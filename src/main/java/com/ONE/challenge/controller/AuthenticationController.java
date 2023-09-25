package com.ONE.challenge.controller;

import com.ONE.challenge.dto.usuario.DatosAuthenticationUsuario;
import com.ONE.challenge.modelo.Usuario;
import com.ONE.challenge.security.DatosJWTToken;
import com.ONE.challenge.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAuthenticationUsuario datosAuthenticationUsuario){

        Authentication AuthTtoken = new UsernamePasswordAuthenticationToken(datosAuthenticationUsuario.email(), datosAuthenticationUsuario.contrasena());
        Authentication usuarioVerificado = authenticationManager.authenticate(AuthTtoken);
        String JWTToken = tokenService.generarToken((Usuario) usuarioVerificado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }

}
