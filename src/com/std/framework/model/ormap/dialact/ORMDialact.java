package com.std.framework.model.ormap.dialact;

public interface ORMDialact {

	public DBtables getDBtables();
	
	public DBSequences getDBSequences();
	
	public DBColumns getDBColumns(String tableName);
	
	
}
