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
import com.forohub.domain.respuesta.DatosActualizarRespuesta;
import com.forohub.domain.respuesta.DatosDetalleRespuesta;
import com.forohub.domain.respuesta.DatosListaRespuestas;
import com.forohub.domain.respuesta.DatosRegistroRespuesta;
import com.forohub.domain.respuesta.Respuesta;
import com.forohub.domain.respuesta.RespuestaRepository;
import com.forohub.domain.topico.TopicoRepository;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.infra.security.TokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {


    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    // publicarRespuesta
    @Transactional
    @PostMapping("/{topicoId}")
    public ResponseEntity<String> publicarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos, 
        @RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable Long topicoId) {
        
        if(!usuarioRepository.existsByNombre(datos.usuario())) {
            throw new ValidacionException("El usuario no existe");
        }

        var usuario = usuarioRepository.findByNombre(datos.usuario());

        // modificar / mejorar
        if(!usuario.getNombre().equals(tokenService.getSubject(header.replace("Bearer ", "")))) {
            throw new ValidacionException("Tu usuario no corresponde con el que se encuentra autenticado");
        }

        if(!topicoRepository.existsById(topicoId)) {
            throw new ValidacionException("El tópico que quieres responder no existe");
        }

        var topico = topicoRepository.findById(topicoId).get();

        if(topico.getEstatus() != true) {
            throw new ValidacionException("El tópico que quieres responder fue eliminado");
        }

        Respuesta respuesta = new Respuesta(null, datos.mensaje(), LocalDateTime.now(), topico, usuario);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok("Respuesta publicada exitosamente");
    }

    // listarRespuestas
    @GetMapping("/{topicoId}")
    public ResponseEntity<Page<DatosListaRespuestas>> listarRespuestas(@PageableDefault(size = 10, sort = "id") Pageable paginacion, @PathVariable Long topicoId) {

        Page<DatosListaRespuestas> pagina = respuestaRepository.findAllByTopico(topicoId, paginacion).map(DatosListaRespuestas::new);


        return ResponseEntity.ok(pagina);
    }
    

    // detallesRespuesta 
    @GetMapping("/{topicoId}/{respuestaId}")
    public ResponseEntity<DatosDetalleRespuesta> detallesRespuesta(@PathVariable Long topicoId, @PathVariable Long respuestaId) {

        var respuesta = respuestaRepository.getReferenceById(respuestaId);

        if(!respuesta.getTopico().getId().equals(topicoId)) {
            throw new ValidationException("El id de la Respuesta no corresponde al Tópico");
        }

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    // editarRespuesta
    @Transactional
    @PutMapping("/{topicoId}/{respuestaId}")
    public ResponseEntity<DatosDetalleRespuesta> editarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datos, @PathVariable Long topicoId, @PathVariable Long respuestaId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        var usuario = usuarioRepository.findByNombre(datos.usuario());

        // modificar / mejorar
        if(!usuario.getNombre().equals(tokenService.getSubject(header.replace("Bearer ", "")))) {
            throw new ValidacionException("Tu usuario no corresponde con el que se encuentra autenticado");
        }

        var respuesta = respuestaRepository.getReferenceById(respuestaId);

        // modificar / mejorar
        if(!respuesta.getUsuario().getNombre().equals(usuario.getNombre())) {
            throw new ValidacionException("No puedes editar un tópico que no abriste");
        }

        if(!respuesta.getTopico().getId().equals(topicoId)) {
            throw new ValidationException("El id de la Respuesta no corresponde al Tópico");
        }

        respuesta.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    // eliminarRespuesta
    @SuppressWarnings("rawtypes")
    @Transactional
    @DeleteMapping("/{topicoId}/{respuestaId}")
    public ResponseEntity eliminarRespuesta(@PathVariable Long topicoId, @PathVariable Long respuestaId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        var respuesta = respuestaRepository.getReferenceById(respuestaId);

        // modificar / mejorar
        if(!respuesta.getUsuario().getNombre().equals
        (tokenService.getSubject(header.replace("Bearer ", "")))) {

            throw new ValidacionException("No puedes eliminar un tópico que no creaste");

        }

        if(!respuesta.getTopico().getId().equals(topicoId)) {
            throw new ValidationException("El id de la Respuesta no corresponde al Tópico");
        }

        respuestaRepository.deleteById(respuestaId);

        return ResponseEntity.noContent().build();
    }
}
