package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agencia;
    private String numero;
    private String tipo;
    private Long saldo;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    protected Usuario usuario;

    public void saque(Long valorSaque) {
        if (valorSaque > 0) {
            this.saldo -= valorSaque;
        }
    }

    public void deposito(Long valorDeposito) {
        if (valorDeposito > 0) {
            this.saldo += valorDeposito;
        }
    }
    public void transferencia(Conta contaDestino, Long valorTransferir) {
        if (valorTransferir > 0) {
            this.saldo -= valorTransferir;
        }
    }


}
