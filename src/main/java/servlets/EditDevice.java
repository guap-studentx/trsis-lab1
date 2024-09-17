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


@WebServlet(name = "servlets.EditDevice", urlPatterns = {"/edit_device"})
public class EditDevice extends HttpServlet {
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
        String deviceName = request.getParameter("device_name");
        String deviceType = request.getParameter("device_type");
        String deviceAddr = request.getParameter("ip_address");
        String deviceLocation = request.getParameter("location");

        if (deviceId == null || deviceName == null || deviceType == null || deviceAddr == null || deviceLocation == null) {
            sendErrorPage(request, response, "ERROR", "Invalid parameters");
            return;
        }

        int id = Integer.parseInt(deviceId);
        if (!dbManager.deviceExistsById(id)){
            sendErrorPage(request, response, "ERROR", "Device does not exist");
        }

        if (deviceName.length() > 255) {
            sendErrorPage(request, response, "ERROR","Device name is too long");
            return;
        }
        if (deviceType.length() > 255) {
            sendErrorPage(request, response, "ERROR", "Device type is too long");
            return;
        }
        if (deviceAddr.length() > 255) {
            sendErrorPage(request, response, "ERROR","Device address is too long");
            return;
        }
        if (deviceLocation.length() > 255) {
            sendErrorPage(request, response, "ERROR","Device location is too long");
            return;
        }

        if (!dbManager.updateDevice(id, deviceName, deviceType, deviceAddr, deviceLocation)){
            sendErrorPage(request, response, "ERROR", "Failed to update device");
        }
        else{
            sendErrorPage(request, response, "SUCCESS", "Device updated");
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
