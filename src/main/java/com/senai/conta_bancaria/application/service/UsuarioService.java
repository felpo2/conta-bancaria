package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    //Metodo para cadastrar um usuario
    public Usuario cadastrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }

    //Metodo para listar todos os usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //Metodo para buscar usuario especifico
    public Usuario buscarUsuarioPorId(Long id) {

        return usuarioRepository.findById(id).get();
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioAtualizado = buscarUsuarioPorId(id);

        if (usuarioAtualizado != null) {
            usuarioAtualizado.setNome(usuario.getNome());
            usuarioAtualizado.setEmail(usuario.getEmail());
            usuarioAtualizado.setSenha(usuario.getSenha());
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;

    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
