package br.com.zero.service;

import br.com.zero.dao.CargoDAO;
import br.com.zero.model.Cargo;
import java.sql.SQLException;
import java.util.List;

public class CargoService {

    private CargoDAO cargoDAO = new CargoDAO();

    public void cadastrar(Cargo cargo) throws SQLException {
        cargoDAO.inserir(cargo);
    }

    public Cargo buscarPorId(int id, int empresaId) throws SQLException {
        return cargoDAO.buscarPorId(id, empresaId);
    }

    public List<Cargo> listar(int empresaId) throws SQLException {
        return cargoDAO.listar(empresaId);
    }

    public void atualizar(Cargo cargo) throws SQLException {
        cargoDAO.atualizar(cargo);
    }

    public void deletar(int id, int empresaId) throws SQLException {
        cargoDAO.deletar(id, empresaId);
    }
}
