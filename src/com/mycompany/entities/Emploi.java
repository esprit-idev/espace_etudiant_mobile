/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;


/**
 *
 * @author Eslem Nabitt
 */
public class Emploi {
    private int id;
    private String title,content,categoryName,date;

    public Emploi() {
    }

    public Emploi(int id, String title, String content, String categoryName, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.date = date;
    }

    public Emploi(String title, String content, String categoryName, String date) {
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
