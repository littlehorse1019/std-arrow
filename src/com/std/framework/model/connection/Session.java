package com.std.framework.model.connection;


import java.util.List;

import com.std.framework.model.actor.ModelAct;
import com.std.framework.model.actor.OrderAct;

public class Session {
	
	private Session(){}

	public String save(Object entity) {
		ModelAct<Object> modelAct = new ModelAct<Object>(entity);
		return modelAct.save();
	}

	public boolean delete(Object entity) {
		ModelAct<Object> modelAct = new ModelAct<Object>(entity);
		return modelAct.delete();
	}

	public boolean update(Object entity) {
		ModelAct<Object> modelAct = new ModelAct<Object>(entity);
		return modelAct.update();
	}

	public <T> T findByPK(T entity) {
		ModelAct<T> modelAct = new ModelAct<T>(entity);
		return modelAct.findByPK();
	}

	public <T> List<T> findAll(T entity) {
		ModelAct<T> modelAct = new ModelAct<T>(entity);
		return modelAct.findAll();
	}

	public <T> List<T> findAll(T entity, OrderAct order) {
		ModelAct<T> modelAct = new ModelAct<T>(entity);
		return modelAct.findAll(order);
	}

	public <T> List<T> findByEntity(T entity) {
		ModelAct<T> modelAct = new ModelAct<T>(entity);
		return modelAct.findByEntity();
	}

	public <T> List<T> findByEntity(T entity, OrderAct order) {
		ModelAct<T> modelAct = new ModelAct<T>(entity);
		return modelAct.findByEntity(order);
	}

}
