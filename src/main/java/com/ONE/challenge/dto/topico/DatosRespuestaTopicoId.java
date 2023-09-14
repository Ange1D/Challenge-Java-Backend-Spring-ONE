package com.ONE.challenge.dto.topico;

import com.ONE.challenge.modelo.Topico;

public record DatosRespuestaTopicoId(Long id, String titulo, String mensaje, String fechaCreacion, String estado, String autor, String curso) {

    public DatosRespuestaTopicoId(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getfechaCreacion().toString(),
                topico.getStatus().toString(), topico.getAutor().toString(), topico.getCurso().toString() );
    }
}
