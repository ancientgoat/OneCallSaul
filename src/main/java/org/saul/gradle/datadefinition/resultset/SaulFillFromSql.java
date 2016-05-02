package org.saul.gradle.datadefinition.resultset;

import org.saul.gradle.datadefinition.helper.SaulDataSourceConnector;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;

/**
 * 	- identity    :
 * 		source      : TD
 * 		name        : MILK_TABLE
 * 		short_name  : MILK_TABLE
 * 		data_source : TD_ORACLE
 * 		cache       :
 * 			duration_time : 10
 * 			duration_type : minutes
 * 		fields :
 * 			- column      : Td_Alert_Id
 * 			  alias       : Alert_Id
 * 			  data_type   : long
 * 		sql : |
 * 			SELECT  Td_Alert_Id
 * 			FROM    TDDATA.TD_ALERT
 */
public class SaulFillFromSql {

	private SaulDataDefinition saulDataDefinition;
	private SaulDataSourceConnector connector;

	public SaulFillFromSql(final SaulDataDefinition inSaulDataDefinition){
		this.saulDataDefinition = inSaulDataDefinition;
		//this.connector = new SaulDataSourceConnector();
	}

	public void fill(){



	}
}
