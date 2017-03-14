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
        throw new ModelException("��ǰ��û�б�עEntiyע�⣬�޷�ʹ��session������й���!");
    }

    /**
     * ����������ݿ�
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
     * ���ݶ���������ɾ�����ݿ�����Ӧ��¼
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
     * ���ݶ����������޸����ݿ�����Ӧ��¼
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
     * ���ݶ������������Ҳ��������ݿ��м�¼��Ӧ����ʵ��
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
     * ���ݶ��������ݿ��ӳ�䣬���Ҳ��������ݿ��Ӧ�������м�¼��Ӧ����ʵ��
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
     * ���ݶ��������ݿ��ӳ�䣬���յ�ǰ���������ֵ�����Ҳ��������ݿ��Ӧ�������м�¼��Ӧ����ʵ��
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
