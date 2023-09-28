package com.ONE.challenge.dto.topico;

import com.ONE.challenge.dto.curso.DatosRespuestaCurso;
import com.ONE.challenge.dto.usuario.DatosRespuestaUsuario;
import com.ONE.challenge.modelo.Topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopicoId(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String estado, DatosRespuestaUsuario autor, DatosRespuestaCurso curso) {

    public DatosRespuestaTopicoId(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                topico.getStatus().toString(), new DatosRespuestaUsuario(topico.getAutor()),
                new DatosRespuestaCurso(topico.getCurso()));
    }
}
