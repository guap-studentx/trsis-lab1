package servlets;/*
 * this code is available under GNU GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

import db.DBManager;
import db.DeviceObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "servlets.MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    // Класс для работы с базой данных
    private DBManager dbManager = new DBManager();

    // Добавляет в страницу Таблицу с устройствами
    protected void FormDiv_DeviceTable(PrintWriter out) {
        List<DeviceObject> devices = dbManager.getAllDevices();
        out.println("<!-- Таблица с известными устройствами -->");
        out.println("<hr>");
        out.println("<div>");
        out.println("<h2>Известные устройства</h2>");
        out.println("<table border='1'>");
            out.println("<thead><tr><th>ID</th><th>Название устройства</th><th>Тип устройства</th><th>IP-адрес</th><th>Местоположение</th></tr></thead>");
            out.println("<tbody>");
                for (DeviceObject dev : devices) {
                    out.println("<tr>");
                        out.println("<td>" + dev.id + "</td>");
                        out.println("<td>" + dev.name + "</td>");
                        out.println("<td>" + dev.type + "</td>");
                        out.println("<td>" + dev.ip_address + "</td>");
                        out.println("<td>" + dev.location + "</td>");
                    out.println("</tr>");
                }
            out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("<hr>");
    }

    protected void FormDiv_AddDevice(PrintWriter out) {
        out.println("    <!-- Форма для добавления устройства -->\n" +
                "    <div id=\"add-device-container\">\n" +
                "        <h2>Добавить новое устройство</h2>\n" +
                "        <form action=\"/add_device\" method=\"post\">\n" +
                "            <label for=\"device_name\">Название устройства:</label><br>\n" +
                "            <input type=\"text\" id=\"device_name\" name=\"device_name\" required><br><br>\n" +
                "\n" +
                "            <label for=\"device_type\">Тип устройства:</label><br>\n" +
                "            <select id=\"device_type\" name=\"device_type\" required>\n" +
                "                <option value=\"router\">Маршрутизатор</option>\n" +
                "                <option value=\"switch\">Коммутатор</option>\n" +
                "                <option value=\"server\">Сервер</option>\n" +
                "                <option value=\"firewall\">Межсетевой экран</option>\n" +
                "            </select><br><br>\n" +
                "\n" +
                "            <label for=\"ip_address\">IP-адрес:</label><br>\n" +
                "            <input type=\"text\" id=\"ip_address\" name=\"ip_address\" required><br><br>\n" +
                "\n" +
                "            <label for=\"location\">Местоположение:</label><br>\n" +
                "            <input type=\"text\" id=\"location\" name=\"location\"><br><br>\n" +
                "\n" +
                "            <input type=\"submit\" value=\"Добавить устройство\">\n" +
                "        </form>\n" +
                "    </div>");

    }

    protected void FormDiv_DelDevice(PrintWriter out){
        out.println("    <!-- Форма для удаления устройства -->\n" +
                "    <div id=\"delete-device-container\">\n" +
                "        <h2>Удалить устройство</h2>\n" +
                "        <form action=\"/del_device\" method=\"post\">\n" +
                "            <label for=\"device_id\">ID устройства:</label><br>\n" +
                "            <input type=\"text\" id=\"device_id\" name=\"device_id\" required><br><br>\n" +
                "\n" +
                "            <input type=\"submit\" value=\"Удалить устройство\">\n" +
                "        </form>\n" +
                "    </div>");
    }

    protected void FormDiv_EditDevice(PrintWriter out){
        out.println("    <!-- Форма для редактирования устройства -->\n" +
                "    <div id=\"edit-device-container\">\n" +
                "        <h2>Редактировать устройство</h2>\n" +
                "        <form action=\"/edit_device\" method=\"post\">\n" +
                "            <label for=\"device_id\">ID устройства:</label><br>\n" +
                "            <input type=\"text\" id=\"device_id\" name=\"device_id\" required><br><br>\n" +
                "\n" +
                "            <label for=\"device_name\">Название устройства:</label><br>\n" +
                "            <input type=\"text\" id=\"device_name\" name=\"device_name\" required><br><br>\n" +
                "\n" +
                "            <label for=\"device_type\">Тип устройства:</label><br>\n" +
                "            <select id=\"device_type\" name=\"device_type\" required>\n" +
                "                <option value=\"router\">Маршрутизатор</option>\n" +
                "                <option value=\"switch\">Коммутатор</option>\n" +
                "                <option value=\"server\">Сервер</option>\n" +
                "                <option value=\"firewall\">Межсетевой экран</option>\n" +
                "            </select><br><br>\n" +
                "\n" +
                "            <label for=\"ip_address\">IP-адрес:</label><br>\n" +
                "            <input type=\"text\" id=\"ip_address\" name=\"ip_address\" required><br><br>\n" +
                "\n" +
                "            <label for=\"location\">Местоположение:</label><br>\n" +
                "            <input type=\"text\" id=\"location\" name=\"location\"><br><br>\n" +
                "\n" +
                "            <input type=\"submit\" value=\"Сохранить изменения\">\n" +
                "        </form>\n" +
                "    </div>");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"ru\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Учет сетевого оборудования</title>\n" +
                    "</head>\n" +
                    "<body>");

            FormDiv_DeviceTable(out);
            out.println("<hr>");
            FormDiv_AddDevice(out);
            out.println("<hr>");
            FormDiv_DelDevice(out);
            out.println("<hr>");
            FormDiv_EditDevice(out);
            out.println("</body>\n" +
                    "</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}