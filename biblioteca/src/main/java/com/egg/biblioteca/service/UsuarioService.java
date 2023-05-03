package com.egg.biblioteca.service;

import com.egg.biblioteca.entidad.Imagen;
import com.egg.biblioteca.entidad.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepcion.MiException;
import com.egg.biblioteca.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioR;

    @Autowired
    private ImagenService imagenS;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Usuario user = new Usuario();

        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRol(Rol.USER);

        Imagen imagen = imagenS.guardar(archivo);
        user.setImagen(imagen);

        usuarioR.save(user);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, 
            String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioR.findById(idUsuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenS.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioR.save(usuario);
        }
    }

    public Usuario getOne(String id) {
        return usuarioR.getOne(id);
    }
    
    @Transactional()
    public List<Usuario> listarUsuarios(){
        
        List <Usuario> usuarios = new ArrayList();
        usuarios = usuarioR.findAll();
        return usuarios;
    }
    
    @Transactional
    public void cambiarRol(String id){
       
        Optional<Usuario> respuesta = usuarioR.findById(id);
        
        if (respuesta.isPresent()) {          
            Usuario usuario = respuesta.get();
            
            if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.ADMIN);
                
            } else if (usuario.getRol().equals(Rol.ADMIN)){
                usuario.setRol(Rol.USER);
            }
        }
    }

    public void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El mail no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioR.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }
    }

}
