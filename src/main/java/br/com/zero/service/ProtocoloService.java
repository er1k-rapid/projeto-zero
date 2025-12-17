package br.com.zero.service;

import br.com.zero.dao.ProtocoloDAO;
import br.com.zero.model.Protocolo;
import java.sql.SQLException;
import java.util.List;

public class ProtocoloService {
    private ProtocoloDAO dao = new ProtocoloDAO();

    public void salvar(Protocolo p) throws SQLException {
        if (p.getId() == null || p.getId() == 0) {
            if (p.getStatus() == null) p.setStatus("Não Iniciado");
            dao.inserir(p);
        } else {
            dao.atualizar(p);
        }
    }

    public List<Protocolo> listar(int empresaId, String termo) throws SQLException {
        return dao.listar(empresaId, termo);
    }

    public Protocolo buscarPorId(int id, int empresaId) throws SQLException {
        return dao.buscarPorId(id, empresaId);
    }

    public void deletar(int id, int empresaId) throws SQLException {
        dao.deletar(id, empresaId);
    }
}