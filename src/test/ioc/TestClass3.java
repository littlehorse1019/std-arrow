package test.ioc;

import java.util.List;
import java.util.Map;

@SuppressWarnings ("rawtypes")
public class TestClass3 {

    private String value3;
    private List   list3;
    private Map    map3;

    public String getValue3 () {
        return value3;
    }

    public void setValue3 (String value3) {
        this.value3 = value3;
    }

    public List getList3 () {
        return list3;
    }

    public void setList3 (List list3) {
        this.list3 = list3;
    }

    public Map getMap3 () {
        return map3;
    }

    public void setMap3 (Map map3) {
        this.map3 = map3;
    }

    public String toString () {
        return "\n { value3 = " + value3 + "\n list3 = " + list3 + "\n map3 = " + map3 + " } ";
    }
}
