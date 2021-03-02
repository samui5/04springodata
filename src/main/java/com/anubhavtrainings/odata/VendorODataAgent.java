package com.anubhavtrainings.odata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.anubhavtrainings.service.IVendorPersistence;

import lombok.extern.slf4j.Slf4j;

import com.anubhavtrainings.entities.*;
import com.anubhavtrainings.processor.core.datasource.ODataInterface;

@Slf4j
public class VendorODataAgent implements ODataInterface{
	
	@Autowired
	IVendorPersistence employeeRepo;
	
	public List<?> getEntitySet(){
		return employeeRepo.findAll();
	}

	@Override
	public Object getEntity(Map<String, ?> keys) {
		log.debug("getEntity called");
		Long id = (Long) keys.get("Id");
		log.debug("getEntity id is " + id.intValue());
		return employeeRepo.findById(id);
	}

	@Override
	public List<?> getRelatedEntity(Object source, String relatedEntityName, Map<String, Object> keys,
			Field sourceField) {
		return new ArrayList<>();
	}

	@Override
	public void createEntity(Object dataToCreate) {
		log.debug("createEntity called");
		Vendor p = (Vendor)dataToCreate;
		if (!employeeRepo.existsById(p.getId())) {
			employeeRepo.save((Vendor)dataToCreate);
		}
	}

	@Override
	public void deleteEntity(Map<String, ?> keys) {
		log.debug("deleteEntity called");
		Long id = (Long)keys.get("Id");
		employeeRepo.deleteById(id);
	}

	@Override
	public void updateEntity(Object dataToUpdate) {
		log.debug("updateEntity called");
		Vendor p = (Vendor)dataToUpdate;
		employeeRepo.save(p);
	}
}
