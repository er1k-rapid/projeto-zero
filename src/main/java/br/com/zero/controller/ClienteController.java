package br.com.zero.controller;

import br.com.zero.model.Cliente;
import br.com.zero.model.Usuario; 
import br.com.zero.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/clientes")
public class ClienteController extends HttpServlet {

    private final ClienteService service = new ClienteService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req);
        if (usuarioLogado == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json; charset=UTF-8");
        String id = req.getParameter("id");
        String termo = req.getParameter("termo"); 

        try {
            if (id != null) {
                mapper.writeValue(resp.getOutputStream(), service.buscarPorId(Integer.parseInt(id), usuarioLogado.getEmpresaId()));
            } else {
                mapper.writeValue(resp.getOutputStream(), service.listar(usuarioLogado.getEmpresaId(), termo));
            }
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req);
        if (usuarioLogado == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Cliente cliente = mapper.readValue(req.getInputStream(), Cliente.class);
            cliente.setEmpresaId(usuarioLogado.getEmpresaId());
            
            service.salvar(cliente);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuarioLogado = getUsuarioLogado(req);
        if (usuarioLogado == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        String id = req.getParameter("id");
        try {
            service.deletar(Integer.parseInt(id), usuarioLogado.getEmpresaId());
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }

    private Usuario getUsuarioLogado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (Usuario) session.getAttribute("usuario") : null;
    }
}