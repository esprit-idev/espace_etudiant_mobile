package com.mycompany.entities;

import com.codename1.io.File;

public class Document {
int id;
String niveau,matire,nom,date_insert,proprietaire,type;
File fic;
public Document(int id, String niveau, String matire, String nom, String date_insert, String proprietaire, String type,
		File fic) {
	super();
	this.id = id;
	this.niveau = niveau;
	this.matire = matire;
	this.nom = nom;
	this.date_insert = date_insert;
	this.proprietaire = proprietaire;
	this.type = type;
	this.fic = fic;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNiveau() {
	return niveau;
}
public void setNiveau(String niveau) {
	this.niveau = niveau;
}
public String getMatire() {
	return matire;
}
public void setMatire(String matire) {
	this.matire = matire;
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
public String getProprietaire() {
	return proprietaire;
}
public void setProprietaire(String proprietaire) {
	this.proprietaire = proprietaire;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public File getFic() {
	return fic;
}
public void setFic(File fic) {
	this.fic = fic;
}


}
