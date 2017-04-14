package com.std.framework.controller.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyBean {

    private String                           propName  = "";
    private Map<String, Map<String, String>> mapProp   = new HashMap<String, Map<String, String>>();
    private List<Map<String, String>>        listProp  = new ArrayList<Map<String, String>>();
    private String                           refProp   = "";
    private String                           valueProp = "";

    public String getPropName () {
        return propName;
    }

    public void setPropName (String propName) {
        this.propName = propName;
    }

    public Map<String, Map<String, String>> getMapProp () {
        return mapProp;
    }

    public void setMapProp (Map<String, Map<String, String>> mapProp) {
        this.mapProp = mapProp;
    }

    public List<Map<String, String>> getListProp () {
        return listProp;
    }

    public void setListProp (List<Map<String, String>> listProp) {
        this.listProp = listProp;
    }

    public String getRefProp () {
        return refProp;
    }

    public void setRefProp (String refProp) {
        this.refProp = refProp;
    }

    public String getValueProp () {
        return valueProp;
    }

    public void setValueProp (String valueProp) {
        this.valueProp = valueProp;
    }

}
