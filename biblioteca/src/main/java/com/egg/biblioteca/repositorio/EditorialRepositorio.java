
package com.egg.biblioteca.repositorio;

import com.egg.biblioteca.entidad.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,String> {
    
}
