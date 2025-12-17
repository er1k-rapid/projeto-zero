package br.com.zero.controller.pages;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("content", "/WEB-INF/view/login.jsp");
        req.getRequestDispatcher("/WEB-INF/view/layouts/auth.jsp")
           .forward(req, resp);
    }
}