package br.com.zero.controller;

import br.com.zero.model.Cargo;
import br.com.zero.model.Usuario;
import br.com.zero.service.CargoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/cargos")
public class CargoController extends HttpServlet {

    private final CargoService service = new CargoService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        int empresaId = usuarioLogado.getEmpresaId();
        String id = req.getParameter("id");
        String descricao = req.getParameter("descricao");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (id != null) {
                mapper.writeValue(
                    resp.getOutputStream(),
                    service.buscarPorId(Integer.parseInt(id), empresaId)
                );
            } else {
                List<Cargo> cargos = service.listar(empresaId);
                mapper.writeValue(resp.getOutputStream(), cargos);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req, resp);
        if (usuarioLogado == null) return;

        int empresaId = usuarioLogado.getEmpresaId();

        try {
            Cargo cargo = mapper.readValue(req.getInputStream(), Cargo.class);
            cargo.setEmpresaId(empresaId);

            if (cargo.getId() == null) {
                service.cadastrar(cargo);
            } else {
                service.atualizar(cargo);
            }

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    private Usuario getUsuarioLogado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return usuario;
    }
}
