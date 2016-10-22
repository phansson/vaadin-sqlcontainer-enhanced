/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.enhancements.sqlcontainer.cconverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

/**
 * Custom Type Converters for Oracle.
 * 
 * <p>
 * This is meant as a credible example of using {@link CustomTypeConverter}
 * feature. It can be used as-is.
 *
 * <p>To use any of the methods in this class requires that an Oracle JDBC
 * driver MUST be on the class path.
 */
public class OraTypeConverters {

    private static final Logger LOGGER = Logger.getLogger(OraTypeConverters.class.getName());
    private OraTypeConverters() {
    }
    
    /**
     * Returns type converters for {@code TIMESTAMPTZ} and {@code TIMESTAMPLTZ}.
     * These will be converted into a {@link java.sql.Timestamp}.
     *
     * @return map of custom type converters
     */
    public static Map<Class<?>,CustomTypeConverter> getOraTypeConverters() {
        Map<Class<?>,CustomTypeConverter> map = new HashMap<>();
        
        
        map.put(oracle.sql.TIMESTAMPTZ.class,
                new CustomTypeConverter<oracle.sql.TIMESTAMPTZ, java.sql.Timestamp>() {
            @Override
            public java.sql.Timestamp convertObject(TIMESTAMPTZ from, ResultSet rs) {
                try {
                    return from.timestampValue(rs.getStatement().getConnection());
                } catch (SQLException ex) {
                    LOGGER.log(Level.WARNING, "Cannot convert " + getType().getName(), ex);
                }
                return null;
            }

            @Override
            public Class<java.sql.Timestamp> getType() {
                return java.sql.Timestamp.class;
            }

        });
        map.put(oracle.sql.TIMESTAMPLTZ.class,
                new CustomTypeConverter<oracle.sql.TIMESTAMPLTZ, java.sql.Timestamp>() {
            @Override
            public java.sql.Timestamp convertObject(TIMESTAMPLTZ from, ResultSet rs) {
                try {
                    return from.timestampValue(rs.getStatement().getConnection());
                } catch (SQLException ex) {
                    LOGGER.log(Level.WARNING, "Cannot convert " + getType().getName(), ex);
                }
                return null;
            }

            @Override
            public Class<java.sql.Timestamp> getType() {
                return java.sql.Timestamp.class;
            }

        });
        return map;
    }
    
}



