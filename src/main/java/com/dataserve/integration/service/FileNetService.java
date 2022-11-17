package com.dataserve.integration.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataserve.integration.exception.ServiceException;
import com.dataserve.integration.util.FileNetUtil;
import com.filenet.api.core.ObjectStore;

@Service
public class FileNetService {
	
	@Autowired
	FileNetUtil fnUtil;
	
	public JSONArray getAllDocumentClasses() throws ServiceException {
		try {
			ObjectStore os = fnUtil.getObjectStroe("fntadmin", "Passw0rd");
	        return null;
		} catch (Exception e) {
			throw new ServiceException(0, "Failed to get all classifications", e);
		} finally {
			fnUtil.close();
		}
	}
}
