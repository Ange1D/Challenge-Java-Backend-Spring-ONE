package com.ONE.challenge.controller;

import com.ONE.challenge.dto.topico.*;
import com.ONE.challenge.modelo.Curso;
import com.ONE.challenge.modelo.Topico;
import com.ONE.challenge.repository.CursoRepository;
import com.ONE.challenge.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10)Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopicoId> retornaDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaTopicoId(topico));
    }



    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Curso curso = cursoRepository.getReferenceById(datosActualizarTopico.cursoId());
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico, curso);
        return ResponseEntity.ok( new DatosRespuestaTopico(topico));
    }

}
