package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.*;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.exception.ContaNaoEncontradaException;
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

    public ContaResponseDTO saque(Long id, SaqueDTO saqueDTO) {

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id));

        conta.saque(saqueDTO.valor());
        return  ContaResponseDTO.fromEntity(contaRepository.save(conta));

    }


    public ContaResponseDTO deposito(Long id, DepositoDTO depositoDTO) {

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id));

        conta.deposito(depositoDTO.valor());

        return ContaResponseDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResponseDTO transferir(Long idPartida, TransferenciaDTO transferenciaDTO, Long idDestino) {
        Conta contaPartida = contaRepository.findById(idPartida)
                .orElseThrow(() -> new ContaNaoEncontradaException(idPartida));

        Conta contaDestino = contaRepository.findById(idDestino)
                .orElseThrow(() -> new  ContaNaoEncontradaException(idDestino));

        contaPartida.transferencia(contaDestino, transferenciaDTO.valor());
        return  ContaResponseDTO.fromEntity(contaRepository.save(contaPartida));
    }
}