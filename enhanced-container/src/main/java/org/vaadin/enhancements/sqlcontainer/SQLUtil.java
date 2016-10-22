/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vaadin.enhancements.sqlcontainer.query.generator.DefaultSQLGenerator;
import org.vaadin.enhancements.sqlcontainer.query.generator.DerbySQLGenerator;
import org.vaadin.enhancements.sqlcontainer.query.generator.MSSQLGenerator;
import org.vaadin.enhancements.sqlcontainer.query.generator.OracleGenerator;
import org.vaadin.enhancements.sqlcontainer.query.generator.SQLGenerator;

public class SQLUtil implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(SQLUtil.class.getName());
    
    /**
     * Escapes different special characters in strings that are passed to SQL.
     * Replaces the following:
     *
     * <ul>
     * <li>' is replaced with ''</li>
     * <li>\x00 is removed</li>
     * <li>\ is replaced with \\</li>
     * <li>" is replaced with \"</li>
     * <li>\x1a is removed</li> </ul>
     *
     * Also note! The escaping done here may or may not be enough to prevent any
     * and all SQL injections so it is recommended to check user input before
     * giving it to the SQLContainer/TableQuery.
     *
     * @param constant
     * @return \\\'\'
     */
    public static String escapeSQL(String constant) {
        if (constant == null) {
            return null;
        }
        String fixedConstant = constant;
        fixedConstant = fixedConstant.replaceAll("\\\\x00", "");
        fixedConstant = fixedConstant.replaceAll("\\\\x1a", "");
        fixedConstant = fixedConstant.replaceAll("'", "''");
        fixedConstant = fixedConstant.replaceAll("\\\\", "\\\\\\\\");
        fixedConstant = fixedConstant.replaceAll("\\\"", "\\\\\"");
        return fixedConstant;
    }
    
    /**
     * Finds the appropriate {@code SQLGenerator} based on a JDBC Driver Class
     * name.
     * 
     * @see JDBCDriverClassNames
     * @param jdbcDriverClassName
     * @return 
     * @throws SQLException if the type of database cannot be determined
     *               from the input
     */
    public static SQLGenerator getSQLGeneratorFromDriverClass(String jdbcDriverClassName) throws SQLException {
        if (jdbcDriverClassName == null) {
            return new DefaultSQLGenerator();
        }
        JDBCDatabase db = JDBCDatabase.getFromDriverClassName(jdbcDriverClassName);
        if (db == null) {
            throw new SQLException("Unknown JDBC Driver Class : " + jdbcDriverClassName);
        }
        if (db == JDBCDatabase.MARIADB
                || db == JDBCDatabase.MYSQL
                || db == JDBCDatabase.POSTGRESQL
                || db == JDBCDatabase.HSQLDB) {
            return new DefaultSQLGenerator();
        }
        if (db == JDBCDatabase.ORACLE) {
            return new OracleGenerator();
        }
        if (db == JDBCDatabase.MSSQL) {
            return new MSSQLGenerator();
        }
        if (db == JDBCDatabase.DERBY) {
            return new DerbySQLGenerator();
        }
        return new DefaultSQLGenerator();
    }


    /**
     * Finds the appropriate {@code SQLGenerator} based on a JDBC Driver Class
     * name.
       * 
     * <p>This method will not throw an exception. If the {@code jdbcDriverClassName}
     * is unknown then an {@link java.util.logging.Level#WARNING WARNING} will be logged
     * and {@link DefaultSQLGenerator} will be returned.
     * 
     * @see JDBCDriverClassNames
     * @param jdbcDriverClassName
     * @return 
     */
    public static SQLGenerator getSQLGeneratorFromDriverClassNoExc(String jdbcDriverClassName) {
        try {
            return getSQLGeneratorFromDriverClass(jdbcDriverClassName);
        } catch (SQLException ex) {
            // Type of database not supported.
            LOGGER.log(Level.WARNING,
                    "Type of database cannot be determined from JDBC Driver class name : {0}"
                    + ", possibly because SQLContainer doesn't have explicit support for this "
                    + "database. A sensible default rule will be used when creating queries. "
                    + "This may or may not work.", jdbcDriverClassName);
            return new DefaultSQLGenerator();
        }
    }
}
