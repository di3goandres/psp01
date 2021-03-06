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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().endsWith("/db")) {
            showDatabase(req, resp);
        } else {
            showHome(req, resp);
        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            ClassLOCCounter counter = new ClassLOCCounter("resource/Program_1/", true, false);
            counter.process();
            resp.getWriter().println("Program 1");
            resp.getWriter().println(counter.getMessages());

            ClassLOCCounter counterProgram2 = new ClassLOCCounter("resource/Program_2/", true, false);
            resp.getWriter().println("Program 2");
            counterProgram2.process();
            resp.getWriter().println(counterProgram2.getMessages());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

            String out = "Hello!\n";
            while (rs.next()) {
                out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
            }

            resp.getWriter().print(out);
        } catch (Exception e) {
            resp.getWriter().print("There was an error: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        int port = dbUri.getPort();

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT"))); //new Server(80);//
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new App()), "/*");
        server.start();
        server.join();
    }
}
