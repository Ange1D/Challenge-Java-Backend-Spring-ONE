package com.ONE.challenge.controller;

import com.ONE.challenge.dto.topico.DatosRegistroTopico;
import com.ONE.challenge.dto.topico.DatosRespuestaTopico;
import com.ONE.challenge.modelo.Curso;
import com.ONE.challenge.modelo.Topico;
import com.ONE.challenge.repository.CursoRepository;
import com.ONE.challenge.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    public TopicoController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registroTopico(@RequestBody @Valid DatosRegistroTopico datosRegistro, UriComponentsBuilder uri) {
        Curso curso = cursoRepository.getReferenceById(datosRegistro.cursoId());
        Topico topico = topicoRepository.save(new Topico(datosRegistro, curso));
        DatosRespuestaTopico datosRespuesta = new DatosRespuestaTopico(topico);
        URI url = uri.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }


}
