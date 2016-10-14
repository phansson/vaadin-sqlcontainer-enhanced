# Enhanced Vaadin SQLContainer

This an enhanced version of Vaadin's SQLContainer.

It can be used as a drop-in replacement for the standard SQLContainer.



## Enhancements

* Auto-detect database type from the JDBC Driver's class name. This name
can be obtained programmatically by obtaining an instance to the 
[Driver](https://docs.oracle.com/javase/8/docs/api/java/sql/Driver.html)
you are using and then doing `myDriver.getClass().getName()`.
* The correct `SQLGenerator` is now used so that there's no extra work
to get support for e.g. Oracle and MS SQL Server (as is the case in 
standard Vaadin)
* Support for MariaDB and Apache Derby has been added.
* Support for conversion of vendor-specific data types such as Oracle's
proprietary timestamp type or PostgreSQL's Geographic types. This type of
conversion cannot be done in a standard Vaadin `Converter` because the 
conversion needs access to the JDBC connection.


## Custom type conversion

One of the features of the Enhanced SQLContainer is its ability to do
conversion of non-JDBC data type returned from resultsets. This type of
conversion is different from what can be achieved by Vaadin's standard
[converter](https://vaadin.com/api/com/vaadin/data/util/converter/Converter.html)
in that it happens *inside* the JDBC ResultSet traversal and it has access
to the ResultSet. It's a very advanced feature and most of the time you would
want to use Vaadin standard converter instead.
     
Here's an example that will convert from Oracle's properietary `TIMESTAMPTZ`
value into plain-vanilla `Date`. Incidentially, by doing so, we'll loose the 
nano-second precision on the timestamp but that's a choice we make, as we do not want
to expose Oracle's proprietary type to the rest of our application.

```java
Map<Class<?>,CustomTypeConverter> typeConverters = Collections.singletonMap(
    oracle.sql.TIMESTAMPTZ.class,
    new CustomTypeConverter<oracle.sql.TIMESTAMPTZ, Date>() {
        @Override
        public Date convertObject(oracle.sql.TIMESTAMPTZ from, ResultSet rs) {
            try {
                return from.dateValue(rs.getStatement().getConnection());
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Cannot convert " + getType().getName(), ex);
            }
            return null;
        }

        @Override
        public Class<Date> getType() {
            return Date.class;
        }
    });


SQLContainer myContainer = new SQLContainer(query, typeConverters);
```


## Auto-detect database type

Unless you help [TableQuery](https://vaadin.com/api/com/vaadin/data/util/sqlcontainer/query/TableQuery.html) 
a little it will assume you are using either of HSQLDB, MySQL and PostgreSQL.
Which you probably aren't. And then you get weird errors.

Basically TableQuery needs to know what the underlying database is in order
to build correct SQL statements for it.

Enhanced TableQuery can auto-detect the type of the underlying database
based on the driver's class name. This way you can create applications that
are independent of any specific database.

Here's how:

```java
// Obtain this value from somewhere. Often it can be obtained from a
// JDBC url, from a DataSource or the like. Below it is hard-coded
// but it really shouldn't be in real life.
String driverClass = "oracle.jdbc.driver.OracleDriver";

// Pass it to the TableQuery
TableQuery myQuery = new TableQuery("ORDERS", myPool, driverClass);
```



## Background

The code was forked from Vaadin 7.7 branch. Even if the changes are fairly
minimal in terms of code lines it was necessary - due to private methods
and what not - to clone the *whole* of SQLContainer code. However, most of the 
files aren't touched at all. The only thing that hasn't been cloned is the
package `com.vaadin.data.util.sqlcontainer.connection` because it was 
unnecessary to do so.

I don't particular like to have forked the code in this way. I would have liked
to simply extend but that was unfortunately impossible.