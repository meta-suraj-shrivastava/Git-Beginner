package com.writerHub.practice.models;


import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.*;

@Entity
public class Article {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private Date addedDate = new Date();
    private Date modifiedDate = new Date();

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private Author author;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    private Company company;


    public Article() {
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
