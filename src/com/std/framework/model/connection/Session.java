package com.std.framework.model.connection;


import com.std.framework.model.actor.ModelAct;
import com.std.framework.model.actor.OrderAct;

import java.util.List;


public class Session {

    private Session() {
    }

    public <T> T save(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.insert();
    }

    public <T> boolean remove(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.delete();
    }

    public <T> boolean update(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.update();
    }

    public <T> T get(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.get();
    }

    public <T> List<T> list(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.list();
    }

    public <T> List<T> list(T entity, OrderAct order) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.list(order);
    }

    public <T> List<T> listByEntity(T entity) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.listByEntity();
    }

    public <T> List<T> listByEntity(T entity, OrderAct order) {
        ModelAct<T> modelAct = new ModelAct<>(entity);
        return modelAct.listByEntity(order);
    }

}
