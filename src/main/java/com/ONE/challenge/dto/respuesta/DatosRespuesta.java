package com.ONE.challenge.dto.respuesta;

import com.ONE.challenge.modelo.Respuesta;


public record DatosRespuesta (String mensaje, String topico, String  autor){
    public DatosRespuesta(Respuesta respuesta) {
        this(respuesta.getMensaje(), respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre());
    }
}
