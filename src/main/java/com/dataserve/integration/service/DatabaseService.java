package com.dataserve.integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataserve.integration.dto.ClassDTO;
import com.dataserve.integration.exception.ServiceException;
import com.dataserve.integration.util.DatabaseUtil;

@Service
public class DatabaseService {
	
	@Autowired
	DatabaseUtil dbUtil;

	public List<ClassDTO> getAllDocumentClasses() throws ServiceException {
		try {
			dbUtil.connect();
			List<ClassDTO> classes = dbUtil.getSubClassesByParentId(null);
			for (ClassDTO c : classes) {
				loadSubclasses(dbUtil, c);
			}
	        return classes;
		} catch (Exception e) {
			throw new ServiceException(0, "Failed to get all classifications", e);
		} finally {
			dbUtil.close();
		}
	}

	private void loadSubclasses(DatabaseUtil db, ClassDTO c) throws Exception {
		try {
			List<ClassDTO> classes = dbUtil.getSubClassesByParentId(c.getId());
			c.setSubclasses(classes);
			for (ClassDTO sub : classes) {
				loadSubclasses(db, sub);
			}
		} catch (Exception e) {
			throw new Exception("Failed to load subclasses for class with id " + c.getId().toString());
		}
	}
	
	
}
