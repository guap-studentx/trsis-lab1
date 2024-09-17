package servlets;

import db.DBManager;
import db.DeviceObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "servlets.DelDevice", urlPatterns = {"/del_device"})
public class DelDevice extends HttpServlet {
    // Класс для работы с базой данных
    private final DBManager dbManager = new DBManager();

    protected void sendErrorPage(HttpServletRequest request, HttpServletResponse response, String statusMessage, String errorMessage)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Table example</title>");
            out.println("</head>");

            out.println("<body>");
            out.println("<h1>" + statusMessage + "</h1>");
            out.println("<h1>"+ errorMessage + "</h1>");
            out.println("<form action=\"/\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Вернуться\">");
            out.println("</form>");
            out.println("</tbody>");

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deviceId = request.getParameter("device_id");

        if (deviceId == null) {
            sendErrorPage(request, response, "ERROR", "Invalid parameters");
        } else{
            boolean result;
            int id = Integer.parseInt(deviceId);
            result = dbManager.deviceExistsById(id);
            if (!result){
                sendErrorPage(request, response, "ERROR","Device with specified id does not exist");
                return;
            }
            result = dbManager.deleteDevice(id);
            if (result) {
                sendErrorPage(request, response, "SUCCESS","Device - deleted");
            } else{
                sendErrorPage(request, response, "ERROR","Device - not deleted");
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
