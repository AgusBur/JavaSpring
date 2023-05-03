
package com.egg.biblioteca.repositorio;

import com.egg.biblioteca.entidad.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,String> {
    
}
