
package com.egg.biblioteca.controlador;

import com.egg.biblioteca.entidad.Usuario;
import com.egg.biblioteca.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioService usuarioS;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }
    
    @GetMapping("/usuario")
    public String listar(ModelMap modelo){
        
        List<Usuario> usuarios = usuarioS.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        
        return "usuario_list.html";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        
        usuarioS.cambiarRol(id);
        
        return "redirect:/admin/usuario";
    }   
}
