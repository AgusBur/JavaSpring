package com.egg.biblioteca.service;

import com.egg.biblioteca.entidad.Imagen;
import com.egg.biblioteca.excepcion.MiException;
import com.egg.biblioteca.repositorio.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenService {

    @Autowired
    ImagenRepositorio imagenR;

    public Imagen guardar(MultipartFile archivo) throws MiException {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenR.save(imagen);

            } catch (Exception e) {

                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenR.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenR.save(imagen);

            } catch (Exception e) {

                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}
