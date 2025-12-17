package br.com.zero.controller.pages;

import br.com.zero.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/usuarios")
public class UsuarioPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = session != null
                ? (Usuario) session.getAttribute("usuario")
                : null;

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("content", "/WEB-INF/view/usuarios/usuario.jsp");
        req.getRequestDispatcher("/WEB-INF/view/layouts/layout.jsp")
           .forward(req, resp);
    }
}
