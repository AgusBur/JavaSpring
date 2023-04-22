package com.egg.biblioteca.service;

import com.egg.biblioteca.entidad.Autor;
import com.egg.biblioteca.entidad.Editorial;
import com.egg.biblioteca.entidad.Libro;
import com.egg.biblioteca.excepcion.MyException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import com.egg.biblioteca.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroR;
    @Autowired
    private AutorRepositorio autorR;
    @Autowired
    private EditorialRepositorio editoR;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer cantidEjemplares, String idAutor, String idEdito)
            throws MyException {

        validar(isbn, titulo, cantidEjemplares, idAutor, idEdito);

        Autor autor = autorR.findById(idAutor).get();
        Editorial edito = editoR.findById(idEdito).get();
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setCantidEjemplares(cantidEjemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEdito(edito);

        libroR.save(libro);
    }

    @Transactional
    public List<Libro> traerTodosLibros() {

        List<Libro> libroLista = new ArrayList();
        libroLista = libroR.findAll();
        return libroLista;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer cantdEjemplares, String idAutor,
            String idEdito) throws MyException {

        validar(isbn, titulo, cantdEjemplares, idAutor, idEdito);

        Optional<Libro> rtaLibro = libroR.findById(isbn);
        Optional<Autor> rtaAutor = autorR.findById(idAutor);
        Optional<Editorial> rtaEdito = editoR.findById(idEdito);

        Autor autor = new Autor();
        Editorial edito = new Editorial();

        if (rtaAutor.isPresent()) {
            autor = rtaAutor.get();
        }

        if (rtaEdito.isPresent()) {
            edito = rtaEdito.get();
        }

        if (rtaLibro.isPresent()) {
            Libro libro = rtaLibro.get();

            libro.setTitulo(titulo);
            libro.setCantidEjemplares(cantdEjemplares);
            libro.setAutor(autor);
            libro.setEdito(edito);
            
            libroR.save(libro);
        }
    }

    public Libro getOne(Long id) {
        return libroR.getOne(id);
    }

    private void validar(Long isbn, String titulo, Integer cantidEjemplares, String idAutor, String idEdito)
            throws MyException {

        if (isbn == null) {
            throw new MyException("El isbn no puede ser nulo");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new MyException("El título no puede ser nulo o estar vacío");
        }
        if (cantidEjemplares == null) {
            throw new MyException("Los ejemplares no pueden ser nulos");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MyException("El id de Autor no puede ser nulo");
        }
        if (idEdito == null || idEdito.isEmpty()) {
            throw new MyException("El id de Editorial no puede ser nulo");
        }

    }
}
