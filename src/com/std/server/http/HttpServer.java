package com.std.server.http;

import com.std.server.ExchangeFactory;
import com.std.server.Server;
import com.std.server.routes.Controller;
import com.std.server.routes.Route;
import com.std.server.routes.Router;

public class HttpServer extends Server {

    /**
     * Default HTTP version used when creating HTTP messages.
     */
    public static final String VERSION = "HTTP/1.1";

    private final Router router;

    private HttpServer(ExchangeFactory factory, Router router) {
        super(factory, new HttpExchangeHandler(router));
        this.router = router;
    }

    public static HttpServer bind(int port) {
        return new HttpServer(new SocketExchangeFactory(port), new Router());
    }

    /**
     * Create a new {@code Route} with a "Get" request type and specified path
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route get(String path) {
        return router.get(path);
    }

    /**
     * Create a new {@code Route} with a "Post" request type and specified path
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route post(String path) {
        return router.post(path);
    }

    /**
     * Create a new {@code Route} with a "DELETE" request type and specified path
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route delete(String path) {
        return router.delete(path);
    }


    /**
     * Converts the methods annotated with {@code Get}, {@code Post}, and {@code DELETE} to routes and adds them to the router.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code DELETE}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     *
     * @param controller the controller instance (if there are instance methods representing routes)
     */
    public HttpServer accept(Controller controller) {
        Router.addRoutes(controller, router);
        return this;
    }

    /**
     * Converts the methods annotated with {@code Get}, {@code Post}, and {@code DELETE} to routes and adds them to the router.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code DELETE}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     * <p>
     * Each class is restricted to containing static routes.
     * <p>
     * Use {@code accept(Controller)} for already instantiated controllers.
     *
     * @param c the controller class
     */
    public HttpServer accept(Class<? extends Controller> c) {
        Router.addRoutes(c, router);
        return this;
    }

}
