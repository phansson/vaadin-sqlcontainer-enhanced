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
package org.vaadin.enhancements.sqlcontainer.cconverter;

import java.sql.ResultSet;

/**
 * Custom type (non-standard types) converter. 
 * 
 * <p>Those who wish to convert from a vendor specific value type into
 * something more sensible must implement this interface. For example this
 * may be a conversion from Oracle's proprietary timestamp types or PostgresSQL's
 * Geometric data types. The advantage of this converter as compared
 * to Vaadin standard property converter, {@code com.vaadin.data.util.converter.Converter},
 * is that this converter acts <i>inside</i> the JDBC ResultSet traversal. Therefore
 * it has access to the ResultSet. For example this is strictly necessary in
 * order to convert Oracle's proprietary timestamp types.
 * Vaadin standard property converter
 * converts from model to presentation (and vice versa), while this converter
 * converts from native database type into model. The Vaadin standard property
 * converter may therefore still be used in addition to this converter.
 * 
 * 
 * <p>Note that unlike Vaadin standard property converter
 * this converter is a one-way converter:  it only converts in the direction 
 * <i>from</i> the value returned by the JDBC driver into something else. 
 * 
 * 
 * <p>The converter is applied to all columns of a query except those that
 * are part of a primary key.
 * 
 * @param <FROM> type to convert from, e.g. {@code oracle.sql.TIMESTAMPTZ}
 * @param <TO> type to convert to, e.g. {@code java.util.Date}
 * @see org.vaadin.enhancements.sqlcontainer.SQLContainer#SQLContainer(org.vaadin.enhancements.sqlcontainer.query.QueryDelegate, java.util.Map) 
 */
public interface CustomTypeConverter<FROM,TO> {

    /**
     * Method to convert from what the JDBC driver returned
     * (as per {@link ResultSet#getObject(int)}) to a more sensible
     * type.
     * 
     * <p>The ResultSet from which the value was obtained is passed for information
     * only. DO NOT PERFORM ACTIONS ON THIS, such as closing it, moving the
     * resultset's cursor or other bad things. You can however "read" from it, as in
     * {@code rs.getStatement().getConnection()}.
     * 
     * @param from the object value to convert from.
     * @param rs resultset from which the value was returned.
     * @return 
     */
    public TO convertObject(FROM from, ResultSet rs);
    
    /**
     * The type of class which {@link #convertObject(Object, ResultSet)}
     * returns, e.g. {@code java.util.Date}.
     * 
     * @return 
     */
    public Class<TO> getType();
}
