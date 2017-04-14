/**
 *
 */
package test.server;


import static com.std.server.http.Status.ok;

import com.std.server.http.HttpHandler;
import com.std.server.http.HttpServer;
import com.std.server.routes.Controller;
import com.std.server.routes.Get;
import com.std.server.routes.Post;
import com.std.server.servlet.HttpServletRequest;
import com.std.server.servlet.HttpServletResponse;

/**
 * @author
 * @date:2016年10月15日 上午10:27:42
 * @parameter
 * @version 1.0
 * @return
 */

/**
 * @author LUOXIAO
 */
public class ServerTest {

    public static void main (String args[]) {

        HttpServer server = HttpServer.bind(8080);
        server.accept(new Controller() {
            @Get ("/test/get")
            public HttpHandler testGet (HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Test HTTP GET success!");
                return ok();
            }

            @Post ("/test/post")
            public HttpHandler testPost (HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Test HTTP POST success!");
                return ok();
            }
        });
        server.listen();
    }
}
