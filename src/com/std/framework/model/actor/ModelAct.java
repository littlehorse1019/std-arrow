package com.std.framework.model.actor;


import java.util.ArrayList;
import java.util.List;

import com.std.framework.annotation.Entity;
import com.std.framework.container.m.ModelException;

public class ModelAct<T> {

	ExcutionAct<T> excutor = null;
	T entity = null;

	public ModelAct(T entity) {
		if (isEntityAnnotation(entity)) {
			this.entity = entity;
		}
		excutor = new ExcutionAct<T>(entity.getClass());
	}

	private boolean isEntityAnnotation(Object entity) {
		if (entity.getClass().isAnnotationPresent(Entity.class))
			return true;
		throw new ModelException("当前类没有标注Entiy注解，无法使用session对其进行管理!");
	}

	/** 保存对象到数据库 */
	public String save() {
		String pk = "";
		try {
			pk = excutor.excuteSave(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk;
	}

	/** 根据对象主键，删除数据库中相应记录 */
	public boolean delete() {
		boolean isDeleteSuccess = false;
		try {
			isDeleteSuccess = excutor.excuteDelete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleteSuccess;
	}

	/** 根据对象主键，修改数据库中相应记录 */
	public boolean update() {
		boolean isUpdateSuccess = false;
		try {
			isUpdateSuccess = excutor.excuteUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdateSuccess;
	}

	/** 根据对象主键，查找并返回数据库中记录对应的类实例 */
	public T findByPK() {
		T obj = null;
		try {
			obj = excutor.excuteFindByPK(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/** 根据对象与数据库表映射，查找并返回数据库对应表中所有记录对应的类实例 */
	public List<T> findAll() {
		List<T> list = new ArrayList<T>();
		try {
			list = excutor.excuteFindAll(entity.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<T> findAll(OrderAct orderAct) {
		List<T> list = new ArrayList<T>();
		return list;
	}

	/** 根据对象与数据库表映射，依照当前对象各属性值，查找并返回数据库对应表中所有记录对应的类实例 */
	public List<T> findByEntity() {
		List<T> list = new ArrayList<T>();
		try {
			list = excutor.excuteFindAll(entity.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<T> findByEntity(OrderAct orderAct) {
		List<T> list = new ArrayList<T>();
		return list;
	}
}
