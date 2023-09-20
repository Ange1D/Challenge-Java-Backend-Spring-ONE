package com.ONE.challenge.dto.curso;

import com.ONE.challenge.modelo.Curso;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        String categoria
){
    public DatosRespuestaCurso(Curso curso){
        this(curso.getId(), curso.getNombre(),curso.getCategoria());
    }
}
