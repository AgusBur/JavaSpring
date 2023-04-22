
package com.egg.biblioteca.entidad;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Libro {
    
    @Id
    private Long isbn;
    private String titulo;
    private Integer cantidEjemplares;
    
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Autor autor;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    private Editorial edito;

    public Libro() {
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantidEjemplares() {
        return cantidEjemplares;
    }

    public void setCantidEjemplares(Integer cantidEjemplares) {
        this.cantidEjemplares = cantidEjemplares;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEdito() {
        return edito;
    }

    public void setEdito(Editorial edito) {
        this.edito = edito;
    }
}
