package com.forohub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forohub.domain.ValidacionException;
import com.forohub.domain.usuario.DatosAutenticacion;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.infra.security.DatosTokenJWT;
import com.forohub.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenservice;

    @PostMapping
    public ResponseEntity<DatosTokenJWT> autenticarse(@RequestBody @Valid DatosAutenticacion datos) {

        
        var nombre = datos.nombre();
        if(!usuarioRepository.existsByNombre(nombre)) {
            throw new ValidacionException("El usuario no existe");
        }

        if(!passwordEncoder.matches(datos.passwd(), usuarioRepository.findByNombre(nombre).getPasswd())) {
            throw new ValidacionException("Contraseña equivocada \n");
        }

        var usuario = usuarioRepository.findByNombre(nombre);
        String token = tokenservice.generarToken(usuario);

        return ResponseEntity.ok(new DatosTokenJWT(token));
    }
}
