/**
 * 
 */
package com.stationmillenium.coverart.dialect;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Custom dialect to correct Hibernate bug
 * @see https://hibernate.onjira.com/browse/HHH-6935
 * @see http://stackoverflow.com/questions/8667965/found-bit-expected-boolean-after-hibernate-4-upgrade
 * @author vincent
 *
 */
public class Mysql5BitBooleanDialect extends MySQL5InnoDBDialect {

	/**
	 * Correct the MySQL bit boolean error
	 */
	public Mysql5BitBooleanDialect() {
        super();
        registerColumnType( Types.BOOLEAN, "bit" );     
    } 
	
}
