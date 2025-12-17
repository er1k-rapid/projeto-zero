package br.com.zero.controller;

import br.com.zero.model.Empresa;
import br.com.zero.model.Usuario;
import br.com.zero.service.EmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/empresas")
public class EmpresaController extends HttpServlet {

    private final EmpresaService service = new EmpresaService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        String id = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (id != null) {
                mapper.writeValue(
                    resp.getOutputStream(),
                    service.buscarPorId(Integer.parseInt(id))
                );
            } else {
                List<Empresa> empresas = service.listar();
                mapper.writeValue(resp.getOutputStream(), empresas);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class);

            Empresa empresaSalva;

            if (empresa.getId() == null) {
                empresaSalva = service.cadastrar(empresa); 
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                Usuario usuarioLogado = getUsuarioLogado(req, resp);
                if (usuarioLogado == null) return;

                empresaSalva = service.atualizar(empresa);
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            mapper.writeValue(resp.getOutputStream(), empresaSalva);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(
                resp.getOutputStream(),
                java.util.Map.of("erro", e.getMessage())
            );
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            service.deletar(Integer.parseInt(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Usuario getUsuarioLogado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return usuario;
    }
}
