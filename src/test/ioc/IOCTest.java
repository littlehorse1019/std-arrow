package test.ioc;

import com.std.framework.controller.BeanFactory;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


public class IOCTest {

    private static Log log = LogFactory.getLogger();

    public static void testIOC () {
        try {
            TestClass1 tc1 = (TestClass1) BeanFactory.getMVCBean("testClass1");
            log.info(tc1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
