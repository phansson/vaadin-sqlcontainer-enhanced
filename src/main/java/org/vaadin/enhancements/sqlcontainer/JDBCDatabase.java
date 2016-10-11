/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.enhancements.sqlcontainer;

/**
 *  Types of databases supported by the Enhanced SQLContainer.
 */
public enum JDBCDatabase {
    
    // The main purpose of this enum is to be able to determine the
    // type/make of the database based on the JDBC driver name. This
    // seems like a fairly safe way to do this as the driver class name
    // is almost carved in stone over time. If vendor's change their driver
    // class name then this method will fail. An example of this is yet to
    // be seen.
    
    /**
     * MySQL
     */
    MYSQL("MySQL", JDBCDriverClassNames.MYSQL),
    /**
     * Oracle
     */
    ORACLE("Oracle Database", JDBCDriverClassNames.ORACLE),
    /**
     * MariaDB
     */
    MARIADB("MariaDB", JDBCDriverClassNames.MARIADB),
    /**
     * Microsoft SQL Server
     */
    MSSQL("Microsoft SQL Server", JDBCDriverClassNames.MSSQL),
    /**
     * PostgresSQL
     */
    POSTGRESQL("PostgreSQL", JDBCDriverClassNames.POSTGRESQL ),
    /**
     * Apache Derby
     */
    DERBY("Apache Derby", JDBCDriverClassNames.DERBY),
    /**
     * HyperSQL 
     */
    HSQLDB("HyperSQL Database", JDBCDriverClassNames.HSQLDB);

    private final String displayName;
    private final String driverClassName;

    private JDBCDatabase(String displayName, String driverClassName) {
        this.displayName = displayName;
        this.driverClassName = driverClassName;
    }

    /**
     * Gets a sensible display name for the database. This is as close
     * as possible to how the database officially names itself in its
     * own documentation.
     * 
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the driver class name for the JDBC driver for the
     * given database.
     * @return driver class name
     */
    public String getDriverClassName() {
        return driverClassName;
    }


    /**
     * Gets the database type based on the driver class name.
     * Returns {@code null} if the driver class name is not recognized.
     * @param driverClassName JDBC driver class name, e.g. for Oracle
     *    this would be {@code oracle.jdbc.driver.OracleDriver}.
     * @return the database type
     */
    public static JDBCDatabase getFromDriverClassName(String driverClassName) {
        for (JDBCDatabase x : values()) {
            if(x.getDriverClassName().equals(driverClassName)) {
                return x;
            }
        }
        return null;
    }
}
