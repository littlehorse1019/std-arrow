package com.std.server.routes;

import static com.std.server.http.Status.notFound;

import com.std.server.http.HttpHandler;
import com.std.server.http.HttpReader;
import com.std.server.http.HttpWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Router implements HttpHandler {

    private Map<String, List<Route>> routes = new HashMap<String, List<Route>>();


    /**
     * Constructs a new {@code Router} converting the methods annotated with {@code Get}, {@code Post}, and {@code
     * Delete} to routes.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code
     * Delete}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     *
     * @param controllers the controllers containing annotated route methods
     * @return a new {@code Router}
     */
    public static Router asRouter (Controller... controllers) {
        if (controllers == null) {
            throw new IllegalArgumentException();
        }
        Router router = new Router();
        for (Controller controller : controllers) {
            Router.addRoutes(controller, router);
        }
        return router;
    }

    /**
     * Constructs a new {@code Router} converting the methods annotated with {@code Get}, {@code Post}, and {@code
     * Delete} to routes.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code
     * Delete}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     * <p>
     * Each class is restricted to containing static routes.
     * <p>
     * Use {@code Router.asRouter(Controller...)} for already instantiated controllers.
     *
     * @param classes the classes containing annotated route methods
     * @return a new {@code Router}
     */
    @SafeVarargs
    public static Router asRouter (Class<? extends Controller>... classes) {
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        Router router = new Router();
        for (Class<? extends Controller> c : classes) {
            Router.addRoutes(c, router);
        }
        return router;
    }

    /**
     * Converts the methods annotated with {@code Get}, {@code Post}, and {@code Delete} to routes and adds them to the
     * specified router.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code
     * Delete}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     *
     * @param controller the controller instance (if there are instance methods representing routes)
     * @param router     the target router
     */
    public static void addRoutes (Controller controller, Router router) {
        Router.addRoutes(controller.getClass(), controller, router);
    }

    /**
     * Converts the methods annotated with {@code Get}, {@code Post}, and {@code Delete} to routes and adds them to the
     * specified router.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code
     * Delete}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     * <p>
     * Each class is restricted to containing static routes.
     * <p>
     * Use {@code Router.addRoutes(Controller, Router)} for already instantiated controllers.
     *
     * @param c      the controller class
     * @param router the target router
     */
    public static void addRoutes (Class<? extends Controller> c, Router router) {
        Router.addRoutes(c, null, router);
    }

    /**
     * Converts the methods annotated with {@code Get}, {@code Post}, and {@code Delete} to routes and adds them to the
     * specified router.
     * <p>
     * A method subject to becoming a {@code Route} must be annotated with {@code Get}, {@code Post}, or {@code
     * Delete}.
     * <p>
     * The method return type must be a {@code HttpHandler}.
     *
     * @param controllerClass the controller class
     * @param controller      the controller instance (if there are instance methods representing routes)
     * @param router          the target router
     */
    private static void addRoutes (Class<? extends Controller> controllerClass, Controller controller, Router router) {
        if (controllerClass == null || router == null) {
            throw new IllegalArgumentException();
        }
        Class<?> c = controllerClass;
        do {
            Stream.concat(Arrays.stream(c.getMethods()), Arrays.stream(c.getDeclaredMethods())).forEach(method -> {
                String requestType;
                String path;

                if (method.isAnnotationPresent(Get.class)) {
                    Get get = method.getDeclaredAnnotation(Get.class);
                    requestType = HttpReader.GET;
                    path = get.value();
                } else if (method.isAnnotationPresent(Post.class)) {
                    Post post = method.getDeclaredAnnotation(Post.class);
                    requestType = HttpReader.POST;
                    path = post.value();
                } else if (method.isAnnotationPresent(Delete.class)) {
                    Delete delete = method.getDeclaredAnnotation(Delete.class);
                    requestType = HttpReader.DELETE;
                    path = delete.value();
                } else {
                    return;
                }

                Route   route    = new Route(requestType, path);
                boolean isStatic = Modifier.isStatic(method.getModifiers());
                if (!isStatic && controller == null) {
                    throw new IllegalArgumentException(
                        "Illegal route. Methods must be declared static for non-instantiated controllers.");
                }
                route.use(method, isStatic ? null : controller);
                router.add(route);
            });
        } while ((c = c.getSuperclass()) != null);

    }

    /**
     * Creates a new {@code Route} with a "GET" request type and the specified path.
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route get (String path) {
        Route route = new Route(HttpReader.GET, path);
        add(route);
        return route;
    }


    /**
     * Creates a new {@code Route} with a "POST" request type and the specified path.
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route post (String path) {
        Route route = new Route(HttpReader.POST, path);
        add(route);
        return route;
    }


    /**
     * Creates a new {@code Route} with a "DELETE" request type and the specified path.
     * <p>
     * The method returns the newly created route to allow customizing the routes parameter ordering and patterns.
     *
     * @param path the route path
     * @return the newly created route
     */
    public Route delete (String path) {
        Route route = new Route(HttpReader.DELETE, path);
        add(route);
        return route;
    }

    /**
     * Adds a new route to the router.
     *
     * @param route the route
     * @return {@code this} for method-chaining
     */
    private Router add (Route route) {
        if (route == null) {
            throw new IllegalArgumentException();
        }
        String requestType = route.getRequestType();
        if (routes.containsKey(requestType)) {
            routes.get(requestType).add(route);
        } else {
            List<Route> routeList = new ArrayList<Route>();
            routeList.add(route);
            routes.put(requestType, routeList);
        }
        return this;
    }

    /**
     * Locates the route that matches the incoming request url.
     * <p>
     * A route must be an absolute match to the request url to be considered.
     *
     * @param url the url
     * @return the best route that matched the url
     * @throws RoutingException indicating a route could not be found that matches the request type and url pair
     */
    private Route find (String requestType, String url) {
        if (!routes.containsKey(requestType)) {
            throw new RoutingException("Unable to find route. Request type: " + requestType + ", url: " + url);
        }
        List<Route> routeList = this.routes.get(requestType);
        for (Route route : routeList) {
            if (route.matches(url)) {
                return route;
            }
        }
        throw new RoutingException("Unable to find route. Request type: " + requestType + ", url: " + url);
    }


    /**
     * Accepts an incoming HTTP exchange, represented by a {@code HttpReader} for parsing the incoming request and a
     * {@code HttpWriter} for writing out a response.
     *
     * @param reader the HTTP reader for parsing the incoming HTTP request
     * @param writer the HTTP writer for writing out the HTTP response
     * @throws IOException indicating an error occurred while reading in the request or writing out the response
     */
    public void accept (HttpReader reader, HttpWriter writer) throws IOException {
        try {
            Object o = find(reader.readRequestType(), reader.readUrl())
                .invoke(reader.parseRequest(), writer.parseResponse());
            if (!(o instanceof HttpHandler)) {
                throw new RoutingException(
                    "Route return type mismatch. Expected: HttpHandler, Actual: " + (o == null ? null : o.getClass()));
            }
            ((HttpHandler) o).accept(reader, writer);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke route action.", e);
        } catch (RoutingException routeEx) {
            notFound().accept(reader, writer);
        }
    }
}
