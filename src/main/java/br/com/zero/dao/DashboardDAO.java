package br.com.zero.dao;

import br.com.zero.config.DBConnection;
import br.com.zero.dto.DashboardDTO;
import br.com.zero.model.Protocolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public DashboardDTO carregarDados(int empresaId, int diasFiltro) throws SQLException {
        DashboardDTO dto = new DashboardDTO();
        Connection conn = DBConnection.getConnection();
        
        // Filtro de data (se diasFiltro == 0, considera "Hoje")
        String filtroData = "";
        if (diasFiltro == 0) {
            filtroData = " AND DATE(Data_Abertura) = CURDATE()";
        } else if (diasFiltro > 0) {
            filtroData = " AND Data_Abertura >= DATE_SUB(NOW(), INTERVAL ? DAY)";
        }

        try {

            String sqlCount = "SELECT " +
                    "COUNT(*) as Total, " +
                    "SUM(CASE WHEN Status IN ('Não Iniciado', 'Em Andamento') THEN 1 ELSE 0 END) as EmAberto, " +
                    "SUM(CASE WHEN Status = 'Concluído' THEN 1 ELSE 0 END) as Concluidos, " +
                    "SUM(CASE WHEN Prioridade = 'Alta' AND Status != 'Concluído' AND Status != 'Cancelado' THEN 1 ELSE 0 END) as Criticos " +
                    "FROM Protocolos WHERE Empresa_ID = ?" + filtroData;

            PreparedStatement stmt = conn.prepareStatement(sqlCount);
            stmt.setInt(1, empresaId);
            if (diasFiltro > 0) stmt.setInt(2, diasFiltro);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dto.setTotalProtocolos(rs.getInt("Total"));
                dto.setEmAberto(rs.getInt("EmAberto"));
                dto.setConcluidos(rs.getInt("Concluidos"));
                dto.setAltaPrioridade(rs.getInt("Criticos"));
            }
            rs.close();
            stmt.close();

 
            String sqlRecentes = "SELECT p.*, c.Razao_Social as Cliente_Nome " +
                                 "FROM Protocolos p " +
                                 "JOIN Clientes c ON p.Cliente_ID = c.ID " +
                                 "WHERE p.Empresa_ID = ?" + filtroData + 
                                 " ORDER BY p.Data_Abertura DESC LIMIT 5";
            
            PreparedStatement stmt2 = conn.prepareStatement(sqlRecentes);
            stmt2.setInt(1, empresaId);
            if (diasFiltro > 0) stmt2.setInt(2, diasFiltro);
            
            ResultSet rs2 = stmt2.executeQuery();
            List<Protocolo> lista = new ArrayList<>();
            while (rs2.next()) {
                Protocolo p = new Protocolo();
                p.setId(rs2.getInt("ID"));
                p.setDescricao(rs2.getString("Descricao"));
                p.setPrioridade(rs2.getString("Prioridade"));
                p.setStatus(rs2.getString("Status"));
                p.setClienteNome(rs2.getString("Cliente_Nome"));
                p.setDataAbertura(rs2.getTimestamp("Data_Abertura"));
                lista.add(p);
            }
            dto.setRecentes(lista);
            
            rs2.close();
            stmt2.close();
            
        } finally {
            conn.close();
        }

        return dto;
    }
}