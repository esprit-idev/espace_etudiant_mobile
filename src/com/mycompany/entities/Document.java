package com.mycompany.entities;

import com.codename1.io.File;

public class Document {
int id,signals;
String niveau,matiere,nom,date_insert,proprietaire,type;
File fic;


public Document() {
	
}

public Document(int id, int signals, String niveau, String matiere, String nom, String date_insert, String proprietaire,
		String type, File fic) {
	super();
	this.id = id;
	this.signals = signals;
	this.niveau = niveau;
	this.matiere = matiere;
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

public int getSignals() {
	return signals;
}

public void setSignals(int signals) {
	this.signals = signals;
}

public String getNiveau() {
	return niveau;
}

public void setNiveau(String niveau) {
	this.niveau = niveau;
}

public String getMatiere() {
	return matiere;
}

public void setMatiere(String matiere) {
	this.matiere = matiere;
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
