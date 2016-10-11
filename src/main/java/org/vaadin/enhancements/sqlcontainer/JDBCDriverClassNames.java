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
 * JDBC Driver class names for the databases supported by SQLContainer.
 */
public class JDBCDriverClassNames {

    private JDBCDriverClassNames() {
    }
    
    public static final String MYSQL = "com.mysql.jdbc.Driver";
    public static final String ORACLE = "oracle.jdbc.driver.OracleDriver";
    public static final String MARIADB = "org.mariadb.jdbc.Driver";
    public static final String MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String POSTGRESQL = "org.postgresql.Driver";
    public static final String DERBY = "org.apache.derby.jdbc.ClientDriver";
    public static final String HSQLDB = "org.hsqldb.jdbc.JDBCDriver";
}
