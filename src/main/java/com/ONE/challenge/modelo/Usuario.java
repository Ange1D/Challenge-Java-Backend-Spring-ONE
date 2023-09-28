package com.ONE.challenge.modelo;

import com.ONE.challenge.dto.usuario.DatosActualizarContrasena;
import com.ONE.challenge.dto.usuario.DatosActualizarUsuario;
import com.ONE.challenge.dto.usuario.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    @Enumerated(EnumType.STRING)
    private Tipo tipo = Tipo.ROLE_USER;

    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos = new ArrayList<>();
    @OneToMany(mappedBy = "autor")
    private List<Respuesta> respuestas = new ArrayList<>();

    public Usuario(DatosRegistroUsuario datosRegistroUsuario){
        this.nombre= datosRegistroUsuario.nombre();
        this.email= datosRegistroUsuario.email();
        this.contrasena= datosRegistroUsuario.contrasena();
        if (datosRegistroUsuario.tipo() != this.tipo) {
            this.tipo = datosRegistroUsuario.tipo();
        }
    }



    public void actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario) {
        if (datosActualizarUsuario.nombre()!=null){
            this.nombre= datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.email()!=null){
            this.email= datosActualizarUsuario.email();
        }
        if (datosActualizarUsuario.contrasena() != null) {
            this.contrasena = datosActualizarUsuario.contrasena();
        }
        if (datosActualizarUsuario.tipo() != datosActualizarUsuario.tipo()) {
            this.tipo = datosActualizarUsuario.tipo();
        }
    }



    public void agregarTopico(Topico topico){
        this.topicos.add(topico);
    }

    public void agregarRespuesta(Respuesta respuesta) {
        this.respuestas.add(respuesta);
    }


    public void actualizarContrasena(DatosActualizarContrasena datosActualizarContrasena) {
        if (datosActualizarContrasena.contrasenaActual().equals(this.contrasena)){
            this.contrasena=datosActualizarContrasena.contrasenaNueva();
        }else {
            System.out.println("Las contraseñas no coinciden");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipo.toString()));
    }
    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}