package br.com.zero.dao;

import br.com.zero.config.DBConnection;
import br.com.zero.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente c) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO Clientes (Empresa_ID, Razao_Social, Nome_Fantasia, Tipo_Pessoa, Numero_Documento, Email, Telefone, Observacao, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, c.getEmpresaId());
        stmt.setString(2, c.getRazaoSocial());
        stmt.setString(3, c.getNomeFantasia());
        stmt.setString(4, c.getTipoPessoa());
        stmt.setString(5, c.getNumeroDocumento());
        stmt.setString(6, c.getEmail());
        stmt.setString(7, c.getTelefone());
        stmt.setString(8, c.getObservacao());
        stmt.setString(9, c.getStatus() != null ? c.getStatus() : "Ativo");
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void atualizar(Cliente c) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE Clientes SET Razao_Social=?, Nome_Fantasia=?, Tipo_Pessoa=?, Numero_Documento=?, Email=?, Telefone=?, Observacao=?, Status=? WHERE ID=? AND Empresa_ID=?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, c.getRazaoSocial());
        stmt.setString(2, c.getNomeFantasia());
        stmt.setString(3, c.getTipoPessoa());
        stmt.setString(4, c.getNumeroDocumento());
        stmt.setString(5, c.getEmail());
        stmt.setString(6, c.getTelefone());
        stmt.setString(7, c.getObservacao());
        stmt.setString(8, c.getStatus());
        stmt.setInt(9, c.getId());
        stmt.setInt(10, c.getEmpresaId());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Cliente> listar(int empresaId, String termo) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Clientes WHERE Empresa_ID = ?";
        if (termo != null && !termo.isEmpty()) {
            sql += " AND (Razao_Social LIKE ? OR Nome_Fantasia LIKE ?)";
        }
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, empresaId);
        if (termo != null && !termo.isEmpty()) {
            stmt.setString(2, "%" + termo + "%");
            stmt.setString(3, "%" + termo + "%");
        }
        
        ResultSet rs = stmt.executeQuery();
        List<Cliente> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapear(rs));
        
        rs.close(); stmt.close(); conn.close();
        return lista;
    }

    public Cliente buscarPorId(int id, int empresaId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Clientes WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        
        ResultSet rs = stmt.executeQuery();
        Cliente c = null;
        if (rs.next()) c = mapear(rs);
        
        rs.close(); stmt.close(); conn.close();
        return c;
    }

    public void deletar(int id, int empresaId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM Clientes WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        stmt.executeUpdate();
        stmt.close(); conn.close();
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("ID"));
        c.setEmpresaId(rs.getInt("Empresa_ID"));
        c.setRazaoSocial(rs.getString("Razao_Social"));
        c.setNomeFantasia(rs.getString("Nome_Fantasia"));
        c.setTipoPessoa(rs.getString("Tipo_Pessoa"));
        c.setNumeroDocumento(rs.getString("Numero_Documento"));
        c.setEmail(rs.getString("Email"));
        c.setTelefone(rs.getString("Telefone"));
        c.setObservacao(rs.getString("Observacao"));
        c.setStatus(rs.getString("Status"));
        c.setDataCadastro(rs.getTimestamp("Data_Cadastro"));
        return c;
    }
}