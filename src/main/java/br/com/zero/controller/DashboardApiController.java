package br.com.zero.controller;

import br.com.zero.dao.DashboardDAO;
import br.com.zero.dto.DashboardDTO;
import br.com.zero.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/dashboard")
public class DashboardApiController extends HttpServlet {

    private final DashboardDAO dao = new DashboardDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Pega o filtro da URL (?periodo=7 ou ?periodo=30 ou vazio)
        String periodoStr = req.getParameter("periodo");
        int dias = 0; // Padrão 0 = Hoje
        
        if ("7".equals(periodoStr)) dias = 7;
        else if ("30".equals(periodoStr)) dias = 30;

        try {
            DashboardDTO dados = dao.carregarDados(usuario.getEmpresaId(), dias);
            
            resp.setContentType("application/json; charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), dados);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}