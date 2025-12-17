package br.com.zero.model;

import java.util.Date;

public class Cargo {

    private Integer id;
    private Integer empresaId;
    private String descricao;
    private String status;
    private Date dataCadastro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
    public String getDescricao() {
    	return descricao;
    }
    
    public void setDescricao(String descricao) {
    	this.descricao = descricao;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}   