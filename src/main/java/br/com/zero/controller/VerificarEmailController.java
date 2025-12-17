package br.com.zero.controller;

import br.com.zero.service.UsuarioService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verificar-email")
public class VerificarEmailController extends HttpServlet {

    private final UsuarioService service = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String token = req.getParameter("token");

        if (token == null || token.isBlank()) {
            resp.sendRedirect("/login?erro=token");
            return;
        }

        try {
            boolean verificado = service.verificarEmail(token);

            if (verificado) {
                resp.sendRedirect("/login?verificado=true");
            } else {
                resp.sendRedirect("/login?erro=token");
            }

        } catch (Exception e) {
            resp.sendRedirect("/login?erro=interno");
        }
    }
}
