package io.isiflix.isispring.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

public class IsiDispatcherServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -5846713968837882010L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ignoreFaviconRequest(request);
        PrintWriter out = new PrintWriter(response.getWriter());
        out.println("Hello World from IsiSpring!");
        out.close();
    }

    private void ignoreFaviconRequest(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path != null && path.endsWith("/favicon.ico")) {
            request.setAttribute("jakarta.servlet.error.status_code", 404);
        }
    }
}
