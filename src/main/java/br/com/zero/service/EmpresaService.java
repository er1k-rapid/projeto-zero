package br.com.zero.service;

import br.com.zero.dao.EmpresaDAO;
import br.com.zero.model.Empresa;

import java.sql.SQLException;
import java.util.List;

public class EmpresaService {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    public Empresa cadastrar(Empresa empresa) throws SQLException {
        empresaDAO.inserir(empresa);  
        return empresa;
    }
    public Empresa buscarPorId(int id) throws SQLException {
        return empresaDAO.buscarPorId(id);
    }

    public List<Empresa> listar() throws SQLException {
        return empresaDAO.listar();
    }

    public Empresa atualizar(Empresa empresa) throws SQLException {
        empresaDAO.atualizar(empresa);
        return empresa;
    }

    public void deletar(int id) throws SQLException {
        empresaDAO.deletar(id);
    }
}
