package com.std.aop;

import com.std.framework.controller.aop.AdvisorBean;
import com.std.framework.core.proxy.ProxyCfgBean;
import com.std.framework.core.proxy.ProxyGenerator;
import org.junit.Test;


public class AopTest {

    @Test
    public void doTest () {
        long flag1 = System.nanoTime();

        ProxyCfgBean proxyCfg = new ProxyCfgBean();
//		proxyCfg.setIocBeanId("BookFacadeImpl");
        proxyCfg.setClassName("com.std.aop.BookFacadeImpl");

        AdvisorBean intercepetor1 = new AdvisorBean();
        intercepetor1.setMethodName("addBook");
        intercepetor1.setMethodArguments(new Class<?>[]{String.class});
        intercepetor1.setBeforeClass(BeforeAop.class);
        intercepetor1.setBeforeMethod("beforeTest");
        intercepetor1.setBeforeArguments(new Class<?>[]{String.class});
        intercepetor1.setAfterClass(AfterAop.class);
        intercepetor1.setAfterMethod("afterTest");
        intercepetor1.setAfterArguments(new Class<?>[]{String.class});

        AdvisorBean intercepetor2 = new AdvisorBean();
        intercepetor2.setMethodName("addBook");
        intercepetor2.setMethodArguments(new Class<?>[]{String.class, String.class});
        intercepetor2.setAfterClass(AfterAop.class);
        intercepetor2.setAfterMethod("afterTest");
        intercepetor2.setAfterArguments(new Class<?>[]{String.class, String.class});

        proxyCfg.addAdvisorBean(intercepetor1);
        proxyCfg.addAdvisorBean(intercepetor2);

        long flag2 = System.nanoTime();
        System.out.println("����׼����ʱ" + (flag2 - flag1) / 1000000000.0 + "��");

        ProxyGenerator pg = new ProxyGenerator();
        pg.bind(proxyCfg);
        Object create = null;
        try {
            create = pg.create();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long flag3 = System.nanoTime();
        System.out.println("���󹹽���ʱ" + (flag3 - flag2) / 1000000000.0 + "��");

        BookFacade bf = (BookFacade) create;
        bf.addBook("��Java���˼�롷");
        bf.addBook("����Сʱ��ͨJ2EE��", "��������");

        long flag4 = System.nanoTime();
        System.out.println("������ú�ʱ" + (flag4 - flag3) / 1000000000.0 + "��");

        long flag5 = System.nanoTime();
        System.out.println("�ܺ�ʱ" + (flag5 - flag1) / 1000000000.0 + "��");
    }
}
