package test.ioc;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TestClass2 {

    private String value2;
    private List list2;
    private Map map2;
    private TestClass3 tc2;

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public List getList2() {
        return list2;
    }

    public void setList2(List list2) {
        this.list2 = list2;
    }

    public Map getMap2() {
        return map2;
    }

    public void setMap2(Map map2) {
        this.map2 = map2;
    }

    public TestClass3 getTc2() {
        return tc2;
    }

    public void setTc2(TestClass3 tc2) {
        this.tc2 = tc2;
    }

    public String toString() {
        return "\n { value2 = " + value2 + "\n list2 = " + list2 + "\n map2 = " + map2 + "\n tc2 : " + tc2 + " } ";
    }
}
