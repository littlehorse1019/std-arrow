package test.aop;  

import com.std.framework.annotation.Advisor;
import com.std.framework.annotation.PointCut;
import com.std.framework.annotation.Proxy;
  
@Proxy
public class BookFacadeImpl implements BookFacade {
	
	private String hahaha = "hahahaha~";
  
    @Advisor(value={BeforeAop.class,AfterAop.class},cutPosition={PointCut.Before,PointCut.After} ,cutMethod={"beforeTest","afterTest"})
    public void addBook(String bookName) {
        System.out.println(hahaha+"BookFacadeImpl增加一本书 :"+bookName);  
    }  
  
    @Advisor(value=AfterAop.class,cutPosition=PointCut.After,cutMethod="afterTest")
    public void addBook(String bookName,String userName) {  
        System.out.println(hahaha+"BookFacadeImpl删除一本书 :"+bookName);  
    }

	@Override
	public void deleteBook(String bookName) {
		
	}
    
} 