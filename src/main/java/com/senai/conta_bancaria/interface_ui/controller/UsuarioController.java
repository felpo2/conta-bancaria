package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.application.dto.UsuarioResponseDTO;
import com.senai.conta_bancaria.application.service.UsuarioService;
import com.senai.conta_bancaria.domain.entity.Usuario;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioRequestDTO);

        return ResponseEntity.created(URI.create("/usuario"+usuarioCadastrado.id())).body(usuarioCadastrado);

    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {

        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioRequestDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
