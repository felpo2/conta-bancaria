package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }
}
