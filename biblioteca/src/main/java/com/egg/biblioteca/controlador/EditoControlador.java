package com.egg.biblioteca.controlador;

import com.egg.biblioteca.entidad.Editorial;
import com.egg.biblioteca.excepcion.MiException;
import com.egg.biblioteca.service.EditorialService;
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
@RequestMapping("/editorial") //La URL quedaría localhost:8080/editorial
public class EditoControlador {

    @Autowired
    private EditorialService editoSer;

    @GetMapping("/registrar") //La URL quedaría localhost:8080/editorial/registrar
    public String registrar() {
        return "edito_form.html";
    }

    @PostMapping("/registro") //La URL quedaría localhost:8080/editorial/registro
    public String registroEditorial(@RequestParam String nombreEditorial, ModelMap modelo) {
        try {
            editoSer.crearEditorial(nombreEditorial);
            modelo.put("exito", "La editorial fue cargada correctamente!");
        } catch (MiException ex) {
            Logger.getLogger(EditoControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "edito_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Editorial> editoriales = editoSer.traerTodasEditoriales();
        modelo.addAttribute("editoriales", editoriales);

        return "edito_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editoSer.getOne(id));
        return "edito_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombreEditorial, ModelMap modelo){
        
        try {
            editoSer.modificarEditorial(nombreEditorial, id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "edito_modificar.html";
        }
    }
}
