package com.std.framework.model.connection;


import com.std.framework.model.actor.ModelAct;
import com.std.framework.model.actor.OrderAct;

import java.util.List;


public class Session {

    private Session() {
    }

    public <T> T save(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.save();
    }

    public <T> boolean delete(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.delete();
    }

    public <T> boolean update(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.update();
    }

    public <T> T findByPK(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.findByPK();
    }

    public <T> List<T> findAll(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.findAll();
    }

    public <T> List<T> findAll(T entity, OrderAct order) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.findAll(order);
    }

    public <T> List<T> findByEntity(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.findByEntity();
    }

    public <T> List<T> findByEntity(T entity, OrderAct order) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.findByEntity(order);
    }

}
