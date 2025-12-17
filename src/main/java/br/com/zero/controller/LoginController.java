package br.com.zero.controller;

import br.com.zero.model.Usuario;
import br.com.zero.service.UsuarioService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Usuario usuario = usuarioService.autenticar(email, senha);

            if (usuario == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"erro\":\"credenciais\"}");
                return;
            }

            if (usuario.getEmailVerificado() == null || !usuario.getEmailVerificado()) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("{\"erro\":\"email_nao_verificado\"}");
                return;
            }

            if (!"Ativo".equalsIgnoreCase(usuario.getStatus())) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("{\"erro\":\"usuario_inativo\"}");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", usuario);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"status\":\"ok\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"erro\":\"sistema\"}");
        }
    }

}
