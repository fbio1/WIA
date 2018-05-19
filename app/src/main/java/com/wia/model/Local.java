package com.wia.model;

import java.util.Objects;

public class Local {

    private int id;
    private String nome;
    private String descricao;
    private String responsavel;
    private String contato;
    private String image;
    private String setor;
    private String email;
    private double latitude;
    private double longitude;

    public Local(String nome, String descricao, String responsavel, String image, String setor, String email, String contato, double latitude, double longitude) {
        this.nome = nome;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.contato = contato;
        this.image = image;
        this.setor = setor;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Local() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return id == local.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
