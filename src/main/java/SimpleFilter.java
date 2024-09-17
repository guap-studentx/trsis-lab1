import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

// Фильтр будет применяться ко всем запросам
@WebFilter("/*")
public class SimpleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoggingFilter - initializing");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Приведение к HttpServletRequest/HttpServletResponse для работы с HTTP-данными
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Логируем URL запроса
        String requestURI = httpRequest.getRequestURI();
        System.out.println("Request captured by filter:");
        System.out.println(" * Method: " + httpRequest.getMethod());
        System.out.println(" * URI: " + httpRequest.getRequestURI());
        System.out.println(" * Query String: " + httpRequest.getQueryString());


        // Проверка, если страница не /main, отправляем 404
        if (!requestURI.equals("/") && !requestURI.equals("/add_device") &&
                !requestURI.equals("/del_device") && !requestURI.equals("/edit_device"))
        {
            try (PrintWriter out = httpResponse.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Page not Found</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>404. Requested page is not found!</h1>");
                out.println("</tbody>");
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not Found!");
            return; // Прекращаем дальнейшую обработку
        }

        if ((!httpRequest.getMethod().equals("GET")) && (!httpRequest.getMethod().equals("POST"))) {
            try (PrintWriter out = httpResponse.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Not Implemented</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>501. Requested METHOD - Not Implemented!</h1>");
                out.println("</tbody>");
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
            httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Not Implemented!");
            return; // Прекращаем дальнейшую обработку
        }

        // Передаем управление следующему фильтру или сервлету
        chain.doFilter(request, response);

        // Логируем завершение обработки
        System.out.println("Request serviced by filter: " + requestURI);
    }

    @Override
    public void destroy() {
        System.out.println("LoggingFilter - destroyed");
    }
}
