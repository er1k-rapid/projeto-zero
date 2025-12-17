package br.com.zero.dto;

import br.com.zero.model.Protocolo;
import java.util.List;

public class DashboardDTO {
    private int totalProtocolos;
    private int emAberto; // 
    private int concluidos;
    private int altaPrioridade; 
    private List<Protocolo> recentes;

    // Getters e Setters
    public int getTotalProtocolos() { return totalProtocolos; }
    public void setTotalProtocolos(int totalProtocolos) { this.totalProtocolos = totalProtocolos; }

    public int getEmAberto() { return emAberto; }
    public void setEmAberto(int emAberto) { this.emAberto = emAberto; }

    public int getConcluidos() { return concluidos; }
    public void setConcluidos(int concluidos) { this.concluidos = concluidos; }

    public int getAltaPrioridade() { return altaPrioridade; }
    public void setAltaPrioridade(int altaPrioridade) { this.altaPrioridade = altaPrioridade; }

    public List<Protocolo> getRecentes() { return recentes; }
    public void setRecentes(List<Protocolo> recentes) { this.recentes = recentes; }
}