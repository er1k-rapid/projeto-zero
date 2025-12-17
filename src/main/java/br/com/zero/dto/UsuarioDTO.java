package br.com.zero.dto;

import java.util.Date;

public class UsuarioDTO {

    private int id;
    private int empresaId;
    private String nome;
    private String tipoUsuario;
    private String status;
    private String cargoDescricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCargoDescricao() {
    	return cargoDescricao;
    }
    
    public void setCargoDescricao(String cargoDescricao) {
    	this.cargoDescricao = cargoDescricao;
    }
}
