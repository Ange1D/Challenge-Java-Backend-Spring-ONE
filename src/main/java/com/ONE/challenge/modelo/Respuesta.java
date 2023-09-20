package com.ONE.challenge.modelo;

import com.ONE.challenge.dto.respuesta.DatosActualizarRespuesta;
import com.ONE.challenge.dto.respuesta.DatosRegistroRespuesta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private Boolean solucion = false;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Topico topico, Usuario autor) {
        this.mensaje=datosRegistroRespuesta.mensaje();
        this.topico=topico;
        this.autor = autor;
        if (datosRegistroRespuesta.solucion()) {
            this.topico.setStatus(StatusTopico.SOLUCIONADO);
        } else {
            this.topico.setStatus(StatusTopico.NO_SOLUCIONADO);
        }
    }

    public void actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        if (datosActualizarRespuesta.mensaje()!=null){
            this.mensaje= datosActualizarRespuesta.mensaje();
        }
        if (datosActualizarRespuesta.solucion() != this.solucion) {
            this.solucion = datosActualizarRespuesta.solucion();
        }
    }
}
