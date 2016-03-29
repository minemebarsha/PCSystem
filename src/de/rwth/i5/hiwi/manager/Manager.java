package de.rwth.i5.hiwi.manager;

import java.sql.Connection;

import de.rwth.i5.hiwi.DBUtility.ConnectionManager;

public interface Manager {
	static Connection conn = ConnectionManager.getInstance().getConnection();

}
