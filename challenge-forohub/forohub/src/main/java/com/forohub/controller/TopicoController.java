package com.forohub.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forohub.domain.ValidacionException;
import com.forohub.domain.topico.DatosActualizarTopico;
import com.forohub.domain.topico.DatosDetalleTopico;
import com.forohub.domain.topico.DatosListaTopico;
import com.forohub.domain.topico.DatosRegistroTopico;
import com.forohub.domain.topico.Topico;
import com.forohub.domain.topico.TopicoRepository;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.infra.security.TokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    @Transactional
    @PostMapping
    public ResponseEntity<String> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, 
        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        if(!usuarioRepository.existsByNombre(datos.usuario())) {
            throw new ValidacionException("El usuario no existe");
        }

        var usuario = usuarioRepository.findByNombre(datos.usuario());

        // modificar / mejorar
        if(!usuario.getNombre().equals(tokenService.getSubject(header.replace("Bearer ", "")))) {
            throw new ValidacionException("Tu usuario no corresponde con el que se encuentra autenticado");
        }

        var topico = new Topico(null, datos.titulo(), datos.mensaje(), true, datos.curso(), LocalDateTime.now(), usuario);

        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico abierto exitosamente");
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10, sort = "id") Pageable paginacion) {
        var pagina = topicoRepository.findAllByEstatusTrue(paginacion).map(DatosListaTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detallesTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DatosDetalleTopico> modificarTopico(@RequestBody @Valid DatosActualizarTopico datos, 
        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        var usuario = usuarioRepository.findByNombre(datos.usuario());

        // modificar / mejorar
        if(!usuario.getNombre().equals(tokenService.getSubject(header.replace("Bearer ", "")))) {
            throw new ValidacionException("Tu usuario no corresponde con el que se encuentra autenticado");
        }

        var topico = topicoRepository.getReferenceById(datos.id());

        // modificar / mejorar
        if(!topico.getUsuario().getNombre().equals(usuario.getNombre())) {
            throw new ValidacionException("No puedes editar un tópico que no abriste");
        }

        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @SuppressWarnings("rawtypes")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id, 
        @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        var topico = topicoRepository.getReferenceById(id);

        // modificar / mejorar
        if(!topico.getUsuario().getNombre().equals
        (tokenService.getSubject(header.replace("Bearer ", "")))) {
            throw new ValidacionException("No puedes eliminar un tópico que no creaste");
        }

        topico.eliminar();

        return ResponseEntity.noContent().build();
    }
}
