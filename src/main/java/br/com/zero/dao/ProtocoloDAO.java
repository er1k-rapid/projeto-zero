package br.com.zero.dao;

import br.com.zero.config.DBConnection;
import br.com.zero.model.Protocolo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProtocoloDAO {

    public void inserir(Protocolo p) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO Protocolos (Empresa_ID, Cliente_ID, Prioridade, Status, Descricao, Resolucao, Data_Abertura) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, p.getEmpresaId());
        stmt.setInt(2, p.getClienteId());
        stmt.setString(3, p.getPrioridade());
        stmt.setString(4, p.getStatus());
        stmt.setString(5, p.getDescricao());
        stmt.setString(6, p.getResolucao());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void atualizar(Protocolo p) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE Protocolos SET Cliente_ID=?, Prioridade=?, Status=?, Descricao=?, Resolucao=?, " +
                     "Data_Conclusao = CASE WHEN ? = 'Concluído' THEN NOW() ELSE NULL END " +
                     "WHERE ID=? AND Empresa_ID=?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, p.getClienteId());
        stmt.setString(2, p.getPrioridade());
        stmt.setString(3, p.getStatus());
        stmt.setString(4, p.getDescricao());
        stmt.setString(5, p.getResolucao());
        stmt.setString(6, p.getStatus()); 
        stmt.setInt(7, p.getId());
        stmt.setInt(8, p.getEmpresaId());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Protocolo> listar(int empresaId, String termo) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT p.*, c.Razao_Social AS Cliente_Nome FROM Protocolos p " +
                     "JOIN Clientes c ON p.Cliente_ID = c.ID " +
                     "WHERE p.Empresa_ID = ?";
        
        if (termo != null && !termo.isEmpty()) {
            sql += " AND (p.Descricao LIKE ? OR c.Razao_Social LIKE ?)";
        }
        sql += " ORDER BY p.Data_Abertura DESC"; 

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, empresaId);
        if (termo != null && !termo.isEmpty()) {
            stmt.setString(2, "%" + termo + "%");
            stmt.setString(3, "%" + termo + "%");
        }
        
        ResultSet rs = stmt.executeQuery();
        List<Protocolo> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapear(rs));
        
        rs.close(); stmt.close(); conn.close();
        return lista;
    }

    public Protocolo buscarPorId(int id, int empresaId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT p.*, c.Razao_Social AS Cliente_Nome FROM Protocolos p " +
                     "JOIN Clientes c ON p.Cliente_ID = c.ID " +
                     "WHERE p.ID = ? AND p.Empresa_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        
        ResultSet rs = stmt.executeQuery();
        Protocolo p = null;
        if (rs.next()) p = mapear(rs);
        
        rs.close(); stmt.close(); conn.close();
        return p;
    }

    public void deletar(int id, int empresaId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM Protocolos WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        stmt.executeUpdate();
        stmt.close(); conn.close();
    }

    private Protocolo mapear(ResultSet rs) throws SQLException {
        Protocolo p = new Protocolo();
        p.setId(rs.getInt("ID"));
        p.setEmpresaId(rs.getInt("Empresa_ID"));
        p.setClienteId(rs.getInt("Cliente_ID"));
        p.setClienteNome(rs.getString("Cliente_Nome")); 
        p.setDataAbertura(rs.getTimestamp("Data_Abertura"));
        p.setPrioridade(rs.getString("Prioridade"));
        p.setStatus(rs.getString("Status"));
        p.setDataConclusao(rs.getTimestamp("Data_Conclusao"));
        p.setDescricao(rs.getString("Descricao"));
        p.setResolucao(rs.getString("Resolucao"));
        return p;
    }
}