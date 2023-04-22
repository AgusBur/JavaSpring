package com.egg.biblioteca.service;

import com.egg.biblioteca.entidad.Autor;
import com.egg.biblioteca.excepcion.MyException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    AutorRepositorio autorR;

    @Transactional
    public void crearAutor(String nombre) throws MyException{
        
        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autorR.save(autor);
    }

    @Transactional
    public List<Autor> traerTodosAutores() {

        List<Autor> autorLista = new ArrayList();
        autorLista = autorR.findAll();
        return autorLista;
    }
    
    @Transactional
    public void modificarAutor(String nombre,String id) throws MyException {
        
        validar(nombre);
        
        Optional<Autor> rta = autorR.findById(id);
        
        if (rta.isPresent()) {
            
            Autor autor = rta.get();
            
            autor.setNombre(nombre);
            autorR.save(autor);
        }
    }
    
    public Autor getOne(String id){
        return autorR.getOne(id);
    }
    
    @Transactional
    public void eliminar(String id) throws MyException{
        
        Autor autor = autorR.getById(id);
        autorR.delete(autor);
    }
    
    private void validar(String nombre) throws MyException{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre del Autor no puede ser nulo o estar vacío");
        }
    }
}
