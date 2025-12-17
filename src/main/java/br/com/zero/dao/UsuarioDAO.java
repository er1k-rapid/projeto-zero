package br.com.zero.dao;

import br.com.zero.model.Usuario;
import br.com.zero.dto.UsuarioDTO;
import br.com.zero.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO Usuarios (Empresa_ID, Nome, Email, Senha, Cargo_ID, Tipo_Usuario, Observacao, Telefone, Status, Email_Token, Email_Verificado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, usuario.getEmpresaId());
        stmt.setString(2, usuario.getNome());
        stmt.setString(3, usuario.getEmail());
        stmt.setString(4, usuario.getSenha());
        stmt.setObject(5, usuario.getCargoId());
        stmt.setString(6, usuario.getTipoUsuario());
        stmt.setString(7, usuario.getObservacao());
        stmt.setString(8, usuario.getTelefone());
        stmt.setString(9, usuario.getStatus());
        stmt.setString(10, usuario.getEmailToken());
        stmt.setBoolean(11, usuario.getEmailVerificado() != null ? usuario.getEmailVerificado() : false);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public Usuario buscarPorId(int id, int empresaId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Usuarios WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        ResultSet rs = stmt.executeQuery();
        Usuario usuario = null;
        if (rs.next()) usuario = mapearUsuario(rs);
        rs.close();
        stmt.close();
        connection.close();
        return usuario;
    }

    public List<UsuarioDTO> listar(int empresaId, String nome) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT "
        		+ "U.ID, "
        		+ "U.Empresa_ID, "
        		+ "U.Nome, "
        		+ "U.Cargo_ID, "
        		+ "U.Tipo_Usuario, "
        		+ "U.Status, "
        		+ "C.Descricao AS Cargo_Descricao "
        		+ "FROM Usuarios U "
        		+ "LEFT JOIN Cargos C ON U.Cargo_ID = C.ID "
        		+ "WHERE U.Empresa_ID = ? ";
        
        if (nome != null) {
        	sql += "AND Nome LIKE ?";
        };
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, empresaId);
        if (nome != null) {
        	stmt.setString(2, "%" + nome + "%");
        };
        
        ResultSet rs = stmt.executeQuery();
        List<UsuarioDTO> lista = new ArrayList<>();
        while (rs.next()) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(rs.getInt("ID"));
            dto.setNome(rs.getString("Nome"));
            dto.setCargoDescricao(rs.getString("Cargo_Descricao"));
            dto.setTipoUsuario(rs.getString("Tipo_Usuario"));
            dto.setStatus(rs.getString("Status"));
            lista.add(dto);
        }
        rs.close();
        stmt.close();
        connection.close();
        return lista;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE Usuarios SET Nome=?, Email=?, Senha=COALESCE(?, senha), Cargo_ID=?, Tipo_Usuario=?, Observacao=?, Telefone=?, Status=?, Email_Token=?, Email_Verificado=? "
                + "WHERE ID=? AND Empresa_ID=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setObject(4, usuario.getCargoId());
        stmt.setString(5, usuario.getTipoUsuario());
        stmt.setString(6, usuario.getObservacao());
        stmt.setString(7, usuario.getTelefone());
        stmt.setString(8, usuario.getStatus());
        stmt.setString(9, usuario.getEmailToken());
        stmt.setBoolean(10, usuario.getEmailVerificado() != null ? usuario.getEmailVerificado() : false);
        stmt.setInt(11, usuario.getId());
        stmt.setInt(12, usuario.getEmpresaId());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void deletar(int id, int empresaId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "DELETE FROM Usuarios WHERE ID = ? AND Empresa_ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setInt(2, empresaId);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Usuarios WHERE Email = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        Usuario usuario = null;
        if (rs.next()) usuario = mapearUsuario(rs);
        rs.close();
        stmt.close();
        connection.close();
        return usuario;
    }
    
    private boolean hasColumn(ResultSet rs, String columnLabel) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (columnLabel.equalsIgnoreCase(meta.getColumnLabel(i))) {
                return true;
            }
        }
        return false;
    }
    
    public Usuario buscarPorEmailToken(String token) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Usuarios WHERE Email_Token = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, token);

        ResultSet rs = stmt.executeQuery();
        Usuario usuario = null;

        if (rs.next()) {
            usuario = mapearUsuario(rs);
        }

        rs.close();
        stmt.close();
        connection.close();
        return usuario;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("ID"));
        usuario.setEmpresaId(rs.getInt("Empresa_ID"));
        usuario.setNome(rs.getString("Nome"));
        usuario.setEmail(rs.getString("Email"));
    	usuario.setSenha(rs.getString("Senha"));
        usuario.setCargoId((Integer) rs.getObject("Cargo_ID"));
        usuario.setTipoUsuario(rs.getString("Tipo_Usuario"));
        usuario.setObservacao(rs.getString("Observacao"));
        usuario.setTelefone(rs.getString("Telefone"));
        usuario.setStatus(rs.getString("Status"));

        if (hasColumn(rs, "Email_Token")) {
            usuario.setEmailToken(rs.getString("Email_Token"));
        }

        if (hasColumn(rs, "Email_Verificado")) {
            usuario.setEmailVerificado(rs.getBoolean("Email_Verificado"));
        }

        usuario.setDataCadastro(rs.getTimestamp("Data_Cadastro"));
        return usuario;
    }
}
