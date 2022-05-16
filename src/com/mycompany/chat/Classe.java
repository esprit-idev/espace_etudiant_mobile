/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aa
 */
public class Classe implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private Niveau niveau;
    private String classe;
   /* private String idn;*/
     private String idn;
     private int nbet;

    public int getNbet() {
        return nbet;
    }

    public void setNbet(int nbet) {
        this.nbet = nbet;
    }

    

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public Classe(int id, Niveau niveau, String classe) {
        this.id = id;
        this.niveau = niveau;
        this.classe = classe;
         idn=niveau.getId();

    }

    
    
    
    public Classe() {
		
	}




	public int getId() {
        return id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public String getClasse() {
        return classe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNiveau(Niveau niveau) {
        idn=niveau.getId();
        this.niveau = niveau;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Classe other = (Classe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.classe, other.classe)) {
            return false;
        }
        if (!Objects.equals(this.idn, other.idn)) {
            return false;
        }
        return Objects.equals(this.niveau, other.niveau);
    }

   




	public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Classe{" + "id=" + id + ", niveau=" + niveau + ", classe=" + classe + ", idn=" + idn + ", nbet=" + nbet + '}';
    }
    
    
    
    
}
