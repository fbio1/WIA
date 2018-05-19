package com.wia.model;

import java.util.Objects;

public class Local {

    private int id;
    private String nome;
    private String endereco;
    private String responsavel;
    private String contato;
    private String ramal;
    private String image;

    public Local(String nome, String endereco, String responsavel, String contato, String ramal, String image) {
        this.nome = nome;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.contato = contato;
        this.ramal = ramal;
        this.image = image;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
