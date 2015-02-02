package edu.uniandes.ecos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class App extends HttpServlet {
    /*
     public static void main( String[] args )
     {
     System.out.println( "Hello World!" );
     }
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        if (req.getRequestURI().endsWith("/db")) {
//            showDatabase(req, resp);
//        } else {
        showHome(req, resp);
//        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!");
    }

    public static void main(String[] args) throws Exception {
        Server server = Integer.valueOf(System.getenv("PORT"))); //new Server(80);//
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new App()), "/*");
        server.start();
        server.join();
    }
}
