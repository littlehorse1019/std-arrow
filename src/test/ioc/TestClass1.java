package test.ioc;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TestClass1 {

    private String value1;
    private List list1;
    private Map map1;
    private TestClass2 tc1;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public List getList1() {
        return list1;
    }

    public void setList1(List list1) {
        this.list1 = list1;
    }

    public Map getMap1() {
        return map1;
    }

    public void setMap1(Map map1) {
        this.map1 = map1;
    }

    public TestClass2 getTc1() {
        return tc1;
    }

    public void setTc1(TestClass2 tc1) {
        this.tc1 = tc1;
    }

    public String toString() {
        return "\n { value1 = " + value1 + "\n list1 = " + list1 + "\n map1 = " + map1 + "\n tc1 : " + tc1 + " } ";
    }

}
