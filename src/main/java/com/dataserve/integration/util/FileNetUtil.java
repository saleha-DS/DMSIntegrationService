package com.dataserve.integration.util;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dataserve.integration.exception.ConnectionException;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

@Component
public class FileNetUtil implements AutoCloseable {
	
	@Value("${content-engine-uri}")
	private String uri;
	
	@Value("${content-engine-stanza}")
	private String stanza;
	
	@Value("${content-engine-object-store-name}")
	private String objectStoreName;
	
	private UserContext uc;
	private Connection conn;
	
	public ObjectStore getObjectStroe(String userName,String password) throws ConnectionException {
        try {
        	conn = Factory.Connection.getConnection(uri.trim());
	        Subject sub = UserContext.createSubject(conn, userName, password, stanza);
	        uc = UserContext.get(); 
	        uc.pushSubject(sub);
	        Domain domain = Factory.Domain.fetchInstance(conn, null, null);	        
	        ObjectStore os = Factory.ObjectStore.fetchInstance(domain, objectStoreName, null);
	        return os;
        } catch (Exception e) {
        	throw new ConnectionException(ConnectionException.OBJECT_STORE_ACCESS_ERROR, "Failed to access Object Store", e);
        }
	}

	@Override
	public void close() {
		try {
			UserContext.get().popSubject();
		} catch (Exception e) {
			IntegrationLogger.warn("Conntection to FileNet was not closed successfully", e);
		}
	}
}
