# std-arrow
std-arrow is a simple JavaWeb + ORM Framework , It helps create simple javaee applets easily and no web server dependency.

**This project is still under active development, though largely finished. It is currently being tested in a production environment. The documentation is being incrementally completed.**



## This is a website build by arrow as a demo.
A web site that helps programmer find jobs.````

http://www.stupideer.cn:8080/offer/job/actionJob.index.do



##Basic usage as blow :
###IOC Support:
```java
private BookFacade bookFacade;
  	@Autowired
	private String name;
  	@Autowired
	private String password;
  	@Autowired
	private Integer age;

	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
```

### AOP Support:
```java
@Proxy
public class BookFacadeImpl implements BookFacade {
	private String hahaha = "hahahaha~";
  
    @Advisor(value={BeforeAop.class,AfterAop.class},cutPosition={PointCut.Before,PointCut.After} ,cutMethod={"beforeTest","afterTest"})
    public void addBook(String bookName) {}  
  
    @Advisor(value=AfterAop.class,cutPosition=PointCut.After,cutMethod="afterTest")
    public void addBook(String bookName,String userName) {}
} 
```

### Service and Transaction Support:
```java
@ActionService
@Transactional
@Clear
public void testing() {}
```

### Leveled Interceptor Support:
#### Class scope
```java
@Interceptor( { ClassInterceptor1.class, ClassInterceptor2.class })
public class ActionTest extends CoreAction {}
```
#### Method scope
```java
@Interceptor( { MethodInterceptor1.class, MethodInterceptor2.class })
	public void testing(User user) {}
```

### ORM Support:
```java
@Entity(MappingByClass.class)
public class TestBo {}
```

### ParamFill and Renders Support:
```java
public void testingParam(User user) {
   List<TestBo> testBoList = doTestModelView();
    		render.toJson(testBoList);
		log.info(
			" ActionTest - test => Name : " + user.getName() + 
			", PassWord : " + user.getPassword() + 
			", Age : " + user.getAge());
		render.setReqAttr("user", "admin");
		render.forwardJsp("/Test2");
	}
```

### Schedule Job Support:
```java
@Job(Name = "TestJob", Main = "testJob", Cron = "0-10 0/1 09-17 * * ?")
public class TestJob {
	public void testJob() {
		System.out.println("I am a testing job ~ ");
	}
}
```

### Start up , none web-server dependency:
```java
public class ServerTest {

	public static void main(String args[]){
		
		HttpServer server = HttpServer.bind(8080);
	    	server.accept(new Controller() {
	        @Get("/test/get")
	        public HttpHandler testGet(HttpServletRequest request, HttpServletResponse response) {
	        	System.out.println("Test HTTP GET success!");
	            return ok();
	        }
	        
	        @Post("/test/post")
	        public HttpHandler testPost(HttpServletRequest request, HttpServletResponse response) {
	        	System.out.println("Test HTTP POST success!");
	            return ok();
	        }
	    });
	    server.listen();
	}
}
```

