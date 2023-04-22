package com.egg.biblioteca.service;

import com.egg.biblioteca.entidad.Editorial;
import com.egg.biblioteca.excepcion.MyException;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {

    @Autowired
    EditorialRepositorio editoR;

    @Transactional
    public void crearEditorial(String nombre) throws MyException {

        validar(nombre);

        Editorial edito = new Editorial();
        edito.setNombre(nombre);

        editoR.save(edito);
    }

    @Transactional
    public List<Editorial> traerTodasEditoriales() {

        List<Editorial> editoLista = new ArrayList();
        editoLista = editoR.findAll();
        return editoLista;
    }

    @Transactional
    public void modificarEditorial(String nombreEditorial, String id) throws MyException {

        validar(nombreEditorial);

        Optional<Editorial> rta = editoR.findById(id);

        if (rta.isPresent()) {

            Editorial edito = rta.get();

            edito.setNombre(nombreEditorial);
            editoR.save(edito);
        }
    }

    public Editorial getOne(String id) {
        return editoR.getOne(id);
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Editorial edito = editoR.getById(id);
        editoR.delete(edito);
    }

    private void validar(String nombre) throws MyException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("El nombre de la Editorial no puede ser nulo o estar vacío");
        }
    }
}
