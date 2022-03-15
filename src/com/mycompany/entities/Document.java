/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

/**
 *
 * @author MeriamBI
 */
public class Document {
    private int id;
    private String nom,date_insert,prop,matiere,niveau,url;

    public Document(String nom, String date_insert, String prop, String matiere, String niveau,String url) {
        this.nom = nom;
        this.date_insert = date_insert;
        this.prop = prop;
        this.matiere = matiere;
        this.niveau = niveau;
        this.url=url;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
