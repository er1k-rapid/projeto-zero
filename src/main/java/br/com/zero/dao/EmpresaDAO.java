package br.com.zero.dao;

import br.com.zero.config.DBConnection;
import br.com.zero.model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class EmpresaDAO {

	public void inserir(Empresa empresa) throws SQLException {

	    Connection connection = DBConnection.getConnection();

	    String sql = "INSERT INTO Empresas " +
	            "(Razao_Social, Tipo_Pessoa, Numero_Documento, Nome_Fantasia, Logo, " +
	            "Email, Telefone, CEP, Logradouro, Numero, Bairro, Cidade, UF, " +
	            "SMTP_Email, SMTP_Servidor, SMTP_Porta, SMTP_Senha, Usa_SSL, " +
	            "Envia_Avaliacoes, Observacao, Status) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    PreparedStatement stmt =
	            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    stmt.setString(1, empresa.getRazaoSocial());
	    stmt.setString(2, empresa.getTipoPessoa());
	    stmt.setString(3, empresa.getNroInscricao());
	    stmt.setString(4, empresa.getNomeFantasia());
	    stmt.setString(5, empresa.getLogo());
	    stmt.setString(6, empresa.getEmail());
	    stmt.setString(7, empresa.getTelefone());
	    stmt.setString(8, empresa.getCep());
	    stmt.setString(9, empresa.getLogradouro());
	    stmt.setString(10, empresa.getNumero());
	    stmt.setString(11, empresa.getBairro());
	    stmt.setString(12, empresa.getCidade());
	    stmt.setString(13, empresa.getUf());
	    stmt.setString(14, empresa.getSmtpEmail());
	    stmt.setString(15, empresa.getSmtpServidor());
	    stmt.setObject(16, empresa.getSmtpPorta());
	    stmt.setString(17, empresa.getSmtpSenha());
	    stmt.setBoolean(18, empresa.getUsaSSL() != null ? empresa.getUsaSSL() : false);
	    stmt.setBoolean(19, empresa.getEnviaAvaliacoes() != null ? empresa.getEnviaAvaliacoes() : false);
	    stmt.setString(20, empresa.getObservacoes());
	    stmt.setString(21, "Ativo");

	    stmt.executeUpdate();

	    try (ResultSet rs = stmt.getGeneratedKeys()) {
	        if (rs.next()) {
	            empresa.setId(rs.getInt(1));
	        }
	    }

	    stmt.close();
	    connection.close();
	}


    public Empresa buscarPorId(int id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Empresas WHERE ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Empresa empresa = null;
        if (rs.next()) empresa = mapearEmpresa(rs);
        rs.close();
        stmt.close();
        connection.close();
        return empresa;
    }

    public List<Empresa> listar() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Empresas";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<Empresa> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(mapearEmpresa(rs));
        }
        rs.close();
        stmt.close();
        connection.close();
        return lista;
    }

    public void atualizar(Empresa empresa) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE Empresas SET Razao_Social = ?, Tipo_Pessoa = ?, Numero_Documento = ?, Nome_Fantasia = ?, Logo = ?, Email = ?, Telefone = ?, CEP = ?, Logradouro = ?, Numero = ?, Bairro = ?, Cidade = ?, UF = ?, SMTP_Email = ?, SMTP_Servidor = ?, SMTP_Porta = ?, SMTP_Senha = ?, Usa_SSL = ?, Envia_Avaliacoes = ?, Observacao = ?, Status = ? WHERE ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, empresa.getRazaoSocial());
        stmt.setString(2, empresa.getTipoPessoa());
        stmt.setString(3, empresa.getNroInscricao());
        stmt.setString(4, empresa.getNomeFantasia());
        stmt.setString(5, empresa.getLogo());
        stmt.setString(6, empresa.getEmail());
        stmt.setString(7, empresa.getTelefone());
        stmt.setString(8, empresa.getCep());
        stmt.setString(9, empresa.getLogradouro());
        stmt.setString(10, empresa.getNumero());
        stmt.setString(11, empresa.getBairro());
        stmt.setString(12, empresa.getCidade());
        stmt.setString(13, empresa.getUf());
        stmt.setString(14, empresa.getSmtpEmail());
        stmt.setString(15, empresa.getSmtpServidor());
        stmt.setObject(16, empresa.getSmtpPorta());
        stmt.setString(17, empresa.getSmtpSenha());
        stmt.setBoolean(18, empresa.getUsaSSL() != null ? empresa.getUsaSSL() : false);
        stmt.setBoolean(19, empresa.getEnviaAvaliacoes() != null ? empresa.getEnviaAvaliacoes() : false);
        stmt.setString(20, empresa.getObservacoes());
        stmt.setString(21, empresa.getStatus());
        stmt.setInt(22, empresa.getId());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void deletar(int id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "DELETE FROM Empresas WHERE ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    private Empresa mapearEmpresa(ResultSet rs) throws SQLException {
        Empresa empresa = new Empresa();
        empresa.setId(rs.getInt("ID"));
        empresa.setRazaoSocial(rs.getString("Razao_Social"));
        empresa.setTipoPessoa(rs.getString("Tipo_Pessoa"));
        empresa.setNroInscricao(rs.getString("Numero_Documento"));
        empresa.setNomeFantasia(rs.getString("Nome_Fantasia"));
        empresa.setLogo(rs.getString("Logo"));
        empresa.setEmail(rs.getString("Email"));
        empresa.setTelefone(rs.getString("Telefone"));
        empresa.setCep(rs.getString("CEP"));
        empresa.setLogradouro(rs.getString("Logradouro"));
        empresa.setNumero(rs.getString("Numero"));
        empresa.setBairro(rs.getString("Bairro"));
        empresa.setCidade(rs.getString("Cidade"));
        empresa.setUf(rs.getString("UF"));
        empresa.setSmtpEmail(rs.getString("SMTP_Email"));
        empresa.setSmtpServidor(rs.getString("SMTP_Servidor"));
        empresa.setSmtpPorta(rs.getObject("SMTP_Porta") != null ? rs.getInt("SMTP_Porta") : null);
        empresa.setSmtpSenha(rs.getString("SMTP_Senha"));
        empresa.setUsaSSL(rs.getBoolean("Usa_SSL"));
        empresa.setEnviaAvaliacoes(rs.getBoolean("Envia_Avaliacoes"));
        empresa.setObservacoes(rs.getString("Observacao"));
        empresa.setDataCadastro(rs.getTimestamp("Data_Cadastro"));
        empresa.setStatus(rs.getString("Status"));
        return empresa;
    }
}
