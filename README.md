# Enhanced Vaadin SQLContainer

This an enhanced version of Vaadin's SQLContainer.

The code was forked from Vaadin 7.7 branch. Even if the changes are fairly
minimal in terms of code lines it was necessary - due to private methods
and what not - to clone the *whole* of SQLContainer code. Even if most the 
files aren't touched at all. The only thing that hasn't been cloned is the
package `com.vaadin.data.util.sqlcontainer.connection` because it was 
unnecessary to do so.


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
