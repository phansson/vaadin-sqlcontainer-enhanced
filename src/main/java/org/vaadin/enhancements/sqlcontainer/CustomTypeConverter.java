/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.enhancements.sqlcontainer;

import java.sql.ResultSet;

/**
 * Custom type (non-standard types) converter. 
 * 
 * <p>Those who wish to convert from a vendor specific value type into
 * something more sensible must implement this interface. For example this
 * may be a conversion from Oracle's proprietary timestamp types or PostgresSQL's
 * Geometric data types. The advantage of this converter as compared
 * to {@link com.vaadin.data.util.converter.Converter Vaadin standard converter}
 * is that this converter acts inside the JDBC ResultSet traversal. Therefore
 * it has access to the ResultSet. For example this is strictly necessary to 
 * convert Oracle's timestamp types.
 * 
 */
public interface CustomTypeConverter {

    /**
     * Method to convert from what the JDBC driver returned
     * (as per {@link ResultSet#getObject(int)}) to a more sensible
     * type.
     * 
     * The ResultSet from which the value was obtained is passed for 
     * information only. DO NOT PERFORM ACTIONS ON THIS, such as closing
     * it or other bad things. You can however "read" from it, as in
     * {@code rs.getStatement().getConnection()}.
     * 
     * @param from the object value to convert from.
     * @param rs resultset from which the value was returned.
     * @return 
     */
    public Object convertObject(Object from, ResultSet rs);
    
    /**
     * The type of class which {@link #convertObject(Object, ResultSet)}
     * returns, e.g. {@code oracle.sql.TIMESTAMPTZ}.
     * 
     * @return 
     */
    public Class<?> getType();
}
