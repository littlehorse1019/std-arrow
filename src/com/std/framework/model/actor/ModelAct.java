package com.std.framework.model.actor;


import com.std.framework.annotation.Entity;
import com.std.framework.container.m.ModelException;

import java.util.ArrayList;
import java.util.List;

public class ModelAct<T> {

    private ExcutorAct<T> excutor = null;
    private T entity = null;

    public ModelAct(T entity) {
        if (isEntityAnnotation(entity)) {
            this.entity = entity;
        }
        excutor = new ExcutorAct<>(entity.getClass());
    }

    private boolean isEntityAnnotation(Object entity) {
        if (entity.getClass().isAnnotationPresent(Entity.class))
            return true;
        throw new ModelException("当前类没有标注Entiy注解，无法使用session对其进行管理!");
    }

    /**
     * 保存对象到数据库
     */
    public T insert() {
        try {
            return excutor.excuteInsert(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据对象主键，删除数据库中相应记录
     */
    public boolean delete() {
        boolean isDeleteSuccess = false;
        try {
            isDeleteSuccess = excutor.excuteDelete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleteSuccess;
    }

    /**
     * 根据对象主键，修改数据库中相应记录
     */
    public boolean update() {
        boolean isUpdateSuccess = false;
        try {
            isUpdateSuccess = excutor.excuteUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdateSuccess;
    }

    /**
     * 根据对象主键，查找并返回数据库中记录对应的类实例
     */
    public T get() {
        T obj = null;
        try {
            obj = excutor.excuteFindByPK(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 根据对象与数据库表映射，查找并返回数据库对应表中所有记录对应的类实例
     */
    public List<T> list() {
        List<T> list = new ArrayList<>();
        try {
            list = excutor.excuteFindAll(entity.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> list(OrderAct orderAct) {
        return new ArrayList<>();
    }

    /**
     * 根据对象与数据库表映射，依照当前对象各属性值，查找并返回数据库对应表中所有记录对应的类实例
     */
    public List<T> listByEntity() {
        List<T> list = new ArrayList<>();
        try {
            list = excutor.excuteFindAll(entity.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> listByEntity(OrderAct orderAct) {
        List<T> list = new ArrayList<>();
        return list;
    }
}
