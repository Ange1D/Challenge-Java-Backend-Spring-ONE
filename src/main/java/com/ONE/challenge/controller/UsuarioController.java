package com.ONE.challenge.controller;

import com.ONE.challenge.dto.usuario.*;
import com.ONE.challenge.modelo.Tipo;
import com.ONE.challenge.modelo.Usuario;
import com.ONE.challenge.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registroUsuario(@RequestBody DatosRegistroUsuario datosRegistro, UriComponentsBuilder uri) {
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistro));
        DatosRespuestaUsuario datosRespuesta = new DatosRespuestaUsuario(usuario);
        URI url = uri.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuarioId> retornarDatosUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaUsuarioId(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@RequestBody DatosActualizarUsuario datosActualizar) {
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizar.id());
        usuario.actualizarUsuario(datosActualizar);
        return ResponseEntity.ok( new DatosRespuestaUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/contraseña/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> cambiarContrasena(@PathVariable Long id,@RequestBody @Valid DatosActualizarContrasena datosActualizarContrasena){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarContrasena(datosActualizarContrasena);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

    @PutMapping("/setAdmin/{id}")
    @Transactional
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DatosRespuestaUsuario> setAdmin(@PathVariable Long id){
        Usuario usuario= usuarioRepository.getReferenceById(id);
        usuario.setTipo(Tipo.ROLE_ADMIN);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

}
