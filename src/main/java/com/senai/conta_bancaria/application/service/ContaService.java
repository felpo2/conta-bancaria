package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ContaRequestDTO;
import com.senai.conta_bancaria.application.dto.ContaResponseDTO;
import com.senai.conta_bancaria.application.dto.UsuarioRequestDTO;
import com.senai.conta_bancaria.application.dto.UsuarioResponseDTO;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.entity.Usuario;
import com.senai.conta_bancaria.domain.exception.ContaNaoEncontradaException;
import com.senai.conta_bancaria.domain.exception.UsuarioNaoEncontradoException;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public ContaResponseDTO cadastrarConta(ContaRequestDTO contaRequestDTO) {

        return ContaResponseDTO.fromEntity(
                contaRepository.save(
                        contaRequestDTO.toEntity()
                )
        );
    }

    public List<ContaResponseDTO> listarContas() {
        return contaRepository.findAll()
                .stream().map(
                        ContaResponseDTO::fromEntity
                ).toList();
    }

    public ContaResponseDTO buscarContaId(Long id) {
        return ContaResponseDTO.fromEntity((Conta) contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id))
        );
    }
    public ContaResponseDTO atualizarConta(Long id, ContaRequestDTO contaRequestDTO) {
        Conta contaAtualizada = (Conta) contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id));

        contaAtualizada.setAgencia(contaRequestDTO.agencia());
        contaAtualizada.setSaldo(contaRequestDTO.saldo());
        contaAtualizada.setNumero(contaRequestDTO.numero());

        return ContaResponseDTO.fromEntity(contaRepository.save(contaAtualizada));
    }
    public void deletarConta(Long id) {
        if(!contaRepository.existsById(id)){
            throw new ContaNaoEncontradaException(id);
        }
        contaRepository.deleteById(id);
    }
}
