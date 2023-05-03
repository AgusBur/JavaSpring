package com.egg.biblioteca.controlador;

import com.egg.biblioteca.entidad.Autor;
import com.egg.biblioteca.excepcion.MiException;
import com.egg.biblioteca.service.AutorService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") //La URL quedaría localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorService autorSer;

    @GetMapping("/registrar") //La URL quedaría localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro") //La URL quedaría localhost:8080/autor/registro
    public String registroAutor(@RequestParam String nombreautor, ModelMap modelo) {
        try {
            autorSer.crearAutor(nombreautor);
            modelo.put("exito", "El autor fue cargado correctamente!");
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Autor> autores = autorSer.traerTodosAutores();
        modelo.addAttribute("autores", autores);

        return "autor_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorSer.getOne(id));
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombreautor, ModelMap modelo){
        
        try {
            autorSer.modificarAutor(nombreautor, id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
    
}
