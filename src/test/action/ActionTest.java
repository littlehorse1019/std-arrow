package test.action;

import com.std.framework.annotation.ActionService;
import com.std.framework.annotation.Clear;
import com.std.framework.annotation.Interceptor;
import com.std.framework.annotation.Transactional;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.ModelHelper;
import com.std.framework.model.connection.ConnectionsPool;
import com.std.framework.model.connection.Session;
import com.std.framework.model.connection.SessionFactory;
import com.std.framework.view.handle.BaseAction;
import java.util.List;
import test.aop.BookFacade;
import test.bo.TestBo;
import test.intcept.ClassInterceptor1;
import test.intcept.ClassInterceptor2;
import test.intcept.MethodInterceptor1;
import test.intcept.MethodInterceptor2;
import test.ioc.IOCTest;
import test.job.TestJob;

@Interceptor ({ClassInterceptor1.class, ClassInterceptor2.class})
public class ActionTest extends BaseAction {

    Log log = LogFactory.getLogger();
    private BookFacade bookFacade;
    private String     name;
    private String     password;
    private Integer    age;

    public void setBookFacade (BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setAge (Integer age) {
        this.age = age;
    }

    @ActionService
    @Transactional
    @Clear
    public void testing () {
        doTestIOCAOP();
        doTestJob();
        log.info("�޲�����������: ActionTest - test => Name : " + name + ", PassWord : " + password + ", Age : " + age);
        List<TestBo> testBoList = doTestModelView();
        render.toJson(testBoList);
        // render.setReqAttr("user","admin");
        // render.toJsp("/Test1");
    }

    private void doTestJob () {
        TestJob job = new TestJob();
        job.testJob();
    }

    private void doTestIOCAOP () {
        System.out.println("---------------IOC����---------------");
        IOCTest.testIOC();
        System.out.println("---------------AOP����---------------");
        bookFacade.addBook("����Ա����������~");
        bookFacade.addBook("ʱ�䶼ȥ�Ķ���?", "��������");
    }

    private List<TestBo> doTestModelView () {

        System.out.println("---------------VIEW��AND MODEL����---------------");

        Session session = SessionFactory.getCurrentSession();

        log.info("���ݿ������ʼ(��ɾ�Ĳ�)" + System.currentTimeMillis());
        TestBo       tb1  = new TestBo();
        List<TestBo> list = session.list(tb1);
        tb1.setAge(25);
        tb1.setJavaLevel("����ܹ�ʦ");
        TestBo tb1save = session.save(tb1);
        log.info(list.size() + " " + list.get(1).getJavaLevel());
        tb1.setId(tb1save.getId());
        session.remove(tb1);

        TestBo tb2 = new TestBo();
        tb2.setId(1);
        tb2.setName("luoxiao");
        tb2.setAge(28);
        tb2.setLolLevel("�貰׽�");
        tb2.setWar3Level("VS17");
        tb2.setJavaLevel("�߼��������ʦ");
        session.update(tb2);

        TestBo tb3 = new TestBo();
        tb3.setId(1);
        TestBo tbpk = session.get(tb3);
        log.info(tbpk.getId() + " " + tbpk.getJavaLevel());

        List<TestBo> testBoList = ModelHelper.findORMClassListBySql(new TestBo(), "SELECT * FROM T_TEST_BO ", null);
        for (TestBo tb : testBoList) {
            log.info(tb.getName());
        }

        log.info("FreeConn Count :" + ConnectionsPool.instance().getCurrentFreeConnPool());
        log.info("Conn Count :" + ConnectionsPool.instance().getCurrentPoolSize());
        log.info("���ݿ��������(��ɾ�Ĳ�)" + System.currentTimeMillis());

        return list;
    }

    @ActionService
    @Interceptor ({MethodInterceptor1.class, MethodInterceptor2.class})
    public void testingParam (User user) {
        log.info("�в�����������: ActionTest - test => Name : " + user.getName() + ", PassWord : " + user.getPassword()
                     + ", Age : " + user.getAge());
        render.setReqAttr("user", "admin");
        render.forwardJsp("/Test2");
    }

    // �Ƿ�����ֵ���ر�
    public boolean isNeedFormed () {
        return true;
    }

}
