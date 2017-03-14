package test.others;

import org.junit.Test;
import test.bo.TestBo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestReflection {

    @Test
    public void doTest() throws Exception {

        TestBo testBo = new TestBo();
        Class<? extends TestBo> clazz = testBo.getClass();
        //setter赋值
        long nanoTime1 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            testBo.setName("luoxiao");
        }
        long nanoTime2 = System.nanoTime();
        System.out.println(nanoTime2 - nanoTime1);

        //setter反射赋值
        long nanoTime3 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Method method = clazz.getDeclaredMethod("setName", new Class[]{String.class});
            method.invoke(testBo, "luoxiao!");
        }
        long nanoTime4 = System.nanoTime();
        System.out.println(nanoTime4 - nanoTime3);

        //field反射赋值
        long nanoTime5 = System.nanoTime();
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        for (int i = 0; i < 100000; i++) {
            field.set(testBo, "luoxiao");
        }
        long nanoTime6 = System.nanoTime();
        System.out.println(nanoTime6 - nanoTime5);

    }

}
