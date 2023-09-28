package com.ONE.challenge.modelo;

import com.ONE.challenge.dto.curso.DatosActualizarCurso;
import com.ONE.challenge.dto.curso.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;

    @OneToMany(mappedBy = "curso")
    private List<Topico> topicos = new ArrayList<>();

    public Curso(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre=datosRegistroCurso.nombre();
        this.categoria=datosRegistroCurso.categoria();
    }

    public void agregarTopico(Topico topico){
        topicos.add(topico);
    }

    public void actualizarCurso(DatosActualizarCurso datosActualizarCurso) {
        if(datosActualizarCurso.nombre()!=null){
            this.nombre=datosActualizarCurso.nombre();
        }
        if (datosActualizarCurso.categoria()!=null){
            this.categoria=datosActualizarCurso.categoria();
        }
    }

}
