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
}
