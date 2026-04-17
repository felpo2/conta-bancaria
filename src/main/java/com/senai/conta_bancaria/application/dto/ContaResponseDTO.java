package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Conta;

public record ContaResponseDTO(
        Long id,
        String agencia,
        String numero,
        String tipo,
        Long saldo
) {
    public static ContaResponseDTO fromEntity(Conta conta) {

        return new ContaResponseDTO(
                conta.getId(),
                conta.getAgencia(),
                conta.getNumero(),
                conta.getTipo(),
                conta.getSaldo()

        );
    }
}
