package br.com.zero.service;

import br.com.zero.dao.ClienteDAO;
import br.com.zero.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    
    private ClienteDAO dao = new ClienteDAO();

    public void salvar(Cliente c) throws SQLException {
        if (c.getId() == null || c.getId() == 0) {
            dao.inserir(c);
        } else {
            dao.atualizar(c);
        }
    }

    public List<Cliente> listar(int empresaId, String termo) throws SQLException {
        return dao.listar(empresaId, termo);
    }

    public Cliente buscarPorId(int id, int empresaId) throws SQLException {
        return dao.buscarPorId(id, empresaId);
    }

    public void deletar(int id, int empresaId) throws SQLException {
        dao.deletar(id, empresaId);
    }
}