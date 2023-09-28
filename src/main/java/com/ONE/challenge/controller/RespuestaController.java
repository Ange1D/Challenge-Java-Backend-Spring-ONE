package com.ONE.challenge.controller;

import com.ONE.challenge.dto.respuesta.*;
import com.ONE.challenge.modelo.Respuesta;
import com.ONE.challenge.modelo.StatusTopico;
import com.ONE.challenge.modelo.Topico;
import com.ONE.challenge.modelo.Usuario;
import com.ONE.challenge.repository.RespuestaRepository;
import com.ONE.challenge.repository.TopicoRepository;
import com.ONE.challenge.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuesta> registroRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder){
        Topico topico= topicoRepository.getReferenceById(datosRegistroRespuesta.topicoId());
        Usuario usuario = usuarioRepository.getReferenceById(datosRegistroRespuesta.autorId());
        if (topico.getStatus() == StatusTopico.CERRADO) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Respuesta respuesta= respuestaRepository.save(new Respuesta(datosRegistroRespuesta,topico, usuario));
        topico.agregarRespuesta(respuesta);
        usuario.agregarRespuesta(respuesta);
        URI url= uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuesta(respuesta));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRetornoRespuestaId> retornarDatosRespuesta(@PathVariable Long id){
        Respuesta respuesta= respuestaRepository.getReferenceById(id);
        return  ResponseEntity.ok(new DatosRetornoRespuestaId((respuesta)));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listarRespuestas(@Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(respuestaRepository.findAll(pageable).map(DatosListadoRespuesta::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuesta> actualizarRespuesta(@PathVariable Long id,@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        Respuesta respuesta= respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id){
        Respuesta respuesta= respuestaRepository.getReferenceById(id);
        respuestaRepository.delete(respuesta);
        return  ResponseEntity.noContent().build();
    }

}
