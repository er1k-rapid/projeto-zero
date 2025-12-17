package br.com.zero.model;

import java.util.Date;

public class Empresa {

    private Integer id;
    private String razaoSocial;
    private String tipoPessoa;
    private String nroInscricao;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String observacoes;
    private String logo;
    private String status;
    private Date dataCadastro;

    private String smtpEmail;
    private String smtpServidor;
    private Integer smtpPorta;
    private String smtpSenha;
    private Boolean usaSSL;
    private Boolean enviaAvaliacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNroInscricao() {
        return nroInscricao;
    }

    public void setNroInscricao(String nroInscricao) {
        this.nroInscricao = nroInscricao;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public void setSmtpEmail(String smtpEmail) {
        this.smtpEmail = smtpEmail;
    }

    public String getSmtpServidor() {
        return smtpServidor;
    }

    public void setSmtpServidor(String smtpServidor) {
        this.smtpServidor = smtpServidor;
    }

    public Integer getSmtpPorta() {
        return smtpPorta;
    }

    public void setSmtpPorta(Integer smtpPorta) {
        this.smtpPorta = smtpPorta;
    }

    public String getSmtpSenha() {
        return smtpSenha;
    }

    public void setSmtpSenha(String smtpSenha) {
        this.smtpSenha = smtpSenha;
    }

    public Boolean getUsaSSL() {
        return usaSSL;
    }

    public void setUsaSSL(Boolean usaSSL) {
        this.usaSSL = usaSSL;
    }

    public Boolean getEnviaAvaliacoes() {
        return enviaAvaliacoes;
    }

    public void setEnviaAvaliacoes(Boolean enviaAvaliacoes) {
        this.enviaAvaliacoes = enviaAvaliacoes;
    }
}
