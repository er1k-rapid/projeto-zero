package br.com.zero.controller;

import br.com.zero.model.Usuario;
import br.com.zero.service.UsuarioService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/usuarios")
public class UsuarioController extends HttpServlet {

    private final UsuarioService service = new UsuarioService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        int empresaId = usuarioLogado.getEmpresaId();
        String id = req.getParameter("id");
        String nome = req.getParameter("nome");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (id != null) {
                mapper.writeValue(
                    resp.getOutputStream(),
                    service.buscarPorId(Integer.parseInt(id), empresaId)
                );
            } else {
                mapper.writeValue(
                    resp.getOutputStream(),
                    service.listar(empresaId, nome)
                );
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            Usuario usuario = mapper.readValue(req.getInputStream(), Usuario.class);

            if (usuario.getId() == null) {
            	Usuario usuarioLogado = getUsuarioLogado(req, resp);
            	
            	if (usuarioLogado != null) {
            		usuario.setEmpresaId(usuarioLogado.getEmpresaId());
            	};
            	
            	service.cadastrarComVerificacao(usuario);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                return;
            }

            Usuario usuarioLogado = getUsuarioLogado(req, resp);
            if (usuarioLogado == null) return;

            usuario.setEmpresaId(usuarioLogado.getEmpresaId());
            service.atualizar(usuario);

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain");
            resp.getWriter().write(e.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        int empresaId = usuarioLogado.getEmpresaId();
        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            service.deletar(Integer.parseInt(id), empresaId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Usuario getUsuarioLogado(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = session != null
                ? (Usuario) session.getAttribute("usuario")
                : null;

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return usuario;
    }
}
