package com.egg.biblioteca.controlador;

import com.egg.biblioteca.entidad.Autor;
import com.egg.biblioteca.entidad.Editorial;
import com.egg.biblioteca.entidad.Libro;
import com.egg.biblioteca.excepcion.MyException;
import com.egg.biblioteca.service.AutorService;
import com.egg.biblioteca.service.EditorialService;
import com.egg.biblioteca.service.LibroService;
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
@RequestMapping("/libro") //La URL quedaría localhost:8080/libro
public class LibroControlador {

    @Autowired
    private AutorService autorSer;
    @Autowired
    private EditorialService editoSer;
    @Autowired
    private LibroService libroSer;

    @GetMapping("/registrar") //La URL quedaría localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorSer.traerTodosAutores();
        List<Editorial> editoriales = editoSer.traerTodasEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro") //La URL quedaría localhost:8080/libro/registro
    public String registroLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer cantidEjemplares, @RequestParam String idAutor,
            @RequestParam String idEdito, ModelMap modelo) {
        //IMPORTANTE!! Recordar que el nombre de estos parámetros tienen que ser IGUALES a los "name" del html
        try {

            libroSer.crearLibro(isbn, titulo, cantidEjemplares, idAutor, idEdito); //si sale bien retornamos al index
            modelo.put("exito", "El libro fue cargado correctamente!");

        } catch (MyException ex) {
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);

            List<Autor> autores = autorSer.traerTodosAutores();
            List<Editorial> editoriales = editoSer.traerTodasEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            return "libro_form.html"; //si pasa algo mal,volvemos a cargar la página
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Libro> libros = libroSer.traerTodosLibros();
        modelo.addAttribute("libros", libros);

        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroSer.getOne(isbn));

        List<Autor> autores = autorSer.traerTodosAutores();
        List<Editorial> editoriales = editoSer.traerTodasEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer cantidEjemplares,
            String idAutor, String idEdito, ModelMap modelo) {

        try {

            List<Autor> autores = autorSer.traerTodosAutores();
            List<Editorial> editoriales = editoSer.traerTodasEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroSer.modificarLibro(isbn, titulo, cantidEjemplares, idAutor, idEdito);

            return "redirect:../lista";
        } catch (MyException ex) {

            List<Autor> autores = autorSer.traerTodosAutores();
            List<Editorial> editoriales = editoSer.traerTodasEditoriales();

            modelo.put("error", ex.getMessage());
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_modificar.html";
        }
    }
}
