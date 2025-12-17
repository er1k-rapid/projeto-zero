package br.com.zero.controller;

import br.com.zero.model.Protocolo;
import br.com.zero.model.Usuario;
import br.com.zero.service.ProtocoloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/protocolos")
public class ProtocoloController extends HttpServlet {

    private final ProtocoloService service = new ProtocoloService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuario = getUsuarioLogado(req);
        if (usuario == null) { resp.setStatus(401); return; }

        resp.setContentType("application/json; charset=UTF-8");
        String id = req.getParameter("id");
        String termo = req.getParameter("termo");

        try {
            if (id != null) {
                mapper.writeValue(resp.getOutputStream(), service.buscarPorId(Integer.parseInt(id), usuario.getEmpresaId()));
            } else {
                mapper.writeValue(resp.getOutputStream(), service.listar(usuario.getEmpresaId(), termo));
            }
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuario = getUsuarioLogado(req);
        if (usuario == null) { resp.setStatus(401); return; }

        try {
            Protocolo p = mapper.readValue(req.getInputStream(), Protocolo.class);
            p.setEmpresaId(usuario.getEmpresaId());
            service.salvar(p);
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuario = getUsuarioLogado(req);
        if (usuario == null) { resp.setStatus(401); return; }

        try {
            String id = req.getParameter("id");
            service.deletar(Integer.parseInt(id), usuario.getEmpresaId());
            resp.setStatus(204);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }

    private Usuario getUsuarioLogado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (Usuario) session.getAttribute("usuario") : null;
    }
}