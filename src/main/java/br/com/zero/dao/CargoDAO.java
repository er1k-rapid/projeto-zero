package br.com.zero.dao;

import br.com.zero.config.DBConnection;
import br.com.zero.model.Cargo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    public void inserir(Cargo cargo) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO Cargos (Empresa_ID, Descricao, Status) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cargo.getEmpresaId());
        stmt.setString(2, cargo.getDescricao());
        stmt.setString(3, cargo.getStatus());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public Cargo buscarPorId(int id, int empresaId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Cargos WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        ResultSet rs = stmt.executeQuery();
        Cargo cargo = null;
        if (rs.next()) cargo = mapearCargo(rs);
        rs.close();
        stmt.close();
        connection.close();
        return cargo;
    }

    public List<Cargo> listar(int empresaId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Cargos WHERE Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, empresaId);
        ResultSet rs = stmt.executeQuery();
        List<Cargo> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(mapearCargo(rs));
        }
        rs.close();
        stmt.close();
        connection.close();
        return lista;
    }

    public void atualizar(Cargo cargo) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE Cargos SET Descricao = ?, Status = ? WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cargo.getDescricao());
        stmt.setString(2, cargo.getStatus());
        stmt.setInt(3, cargo.getId());
        stmt.setInt(4, cargo.getEmpresaId());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void deletar(int id, int empresaId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "DELETE FROM Cargos WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    private Cargo mapearCargo(ResultSet rs) throws SQLException {
        Cargo cargo = new Cargo();
        cargo.setId(rs.getInt("ID"));
        cargo.setEmpresaId(rs.getInt("Empresa_ID"));
        cargo.setDescricao(rs.getString("Descricao"));
        cargo.setStatus(rs.getString("Status"));
        cargo.setDataCadastro(rs.getTimestamp("Data_Cadastro"));
        return cargo;
    }
}
