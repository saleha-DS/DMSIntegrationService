package com.dataserve.integration.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dataserve.integration.dto.ClassDTO;
import com.dataserve.integration.exception.DatabaseException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Component
public class DatabaseUtil {
	
	@Value("${database-server-name}")
	private String serverName;
	
	@Value("${database-port-number}")
	private int port;
	
	@Value("${database-name}")
	private String databaseName;
	
	@Value("${database-username}")
	private String userName;
	
	@Value("${database-password}")
	private String password;
	
	@Value("${database-fetch-batch-size}")
	int batchSize;
	
	private Connection con = null;
	
	public void connect() throws DatabaseException {
        // Create a sql server data source object. 
        SQLServerDataSource ds = new SQLServerDataSource();
        
        /* If you want to use sql server account authentication.*/
        ds.setIntegratedSecurity(false);
        ds.setUser(userName);
        ds.setPassword(password);
                    
        // Set ds server name or ip.
        ds.setServerName(serverName);
        // Set sql server listening port number.
        ds.setPortNumber(port); 
        // Set the database name.
        ds.setDatabaseName(databaseName);
        
        // Get connection
        try {
			con = ds.getConnection();
		} catch (SQLServerException e) {
			throw new DatabaseException("Error getting connection to database", e);
		} catch (Exception e) {
			throw new DatabaseException("Error getting connection to database", e);
		}
	}

	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				IntegrationLogger.warn("Error closing database connection.", e);
			}
		}
	}
	
	private void close(AutoCloseable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				IntegrationLogger.warn("Resource was not closed successfully", e);
			}
		}
	}

	public List<ClassDTO> getSubClassesByParentId(Integer parentId) throws DatabaseException {
		List<ClassDTO> classes = new ArrayList<>();
		PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try {
			String sql = "SELECT CLASSIFICATION_ID, CLASS_AR_NAME, CLASS_EN_NAME, SYMPOLIC_NAME, CLASS_CODE FROM CLASSIFICTIONS WHERE is_fn_added = 1 AND PARENT_ID = ?";
			if (parentId == null) {
				sql = sql.replace("= ?", "IS NULL");
			}
			
			ps = con.prepareStatement(sql);
			if (parentId != null) {
				ps.setInt(1, parentId);
			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				ClassDTO c = new ClassDTO();
				c.setId(rs.getInt("CLASSIFICATION_ID"));
				c.setCode(rs.getString("CLASS_CODE"));
				c.setSymbolicName(rs.getString("SYMPOLIC_NAME"));
				c.setNameAr(rs.getNString("CLASS_AR_NAME"));
				c.setNameEn(rs.getString("CLASS_EN_NAME"));
				c.setParentId(parentId);
				classes.add(c);
			}
			return classes;
		} catch (SQLException e) {
			throw new DatabaseException("", e);
		} finally {
			close(ps);
			close(rs);
		}
	}
	
}
