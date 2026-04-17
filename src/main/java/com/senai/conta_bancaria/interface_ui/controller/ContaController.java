package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ContaRequestDTO;
import com.senai.conta_bancaria.application.dto.ContaResponseDTO;
import com.senai.conta_bancaria.application.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@Tag(name = "Conta", description = "Conta de Usuarios")
@RestController
@RequestMapping("/conta")
public class ContaController {
    @Autowired
    ContaService contaService;


    @Operation(
            summary = "Conta Usuario",
            description = "Adiciona uma nova Conta à base",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaRequestDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "agencia": "Santander",
                                          "numero": "78236425934",
                                          "tipo": "CNPJ",
                                          "saldo": "1000"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Conta cadastrada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "saldo inválido", value = "\"saldo mínimo do serviço deve ser R$ 1,00 R$\""),
                                            @ExampleObject(name = "Conta Inexistente", value = "\"Sua Conta não existe, por favor entrar em contato com o suporte \""),
                                            @ExampleObject(name = "Conta Duplicada", value = "\"Sua foi duplicada\""),
                                            @ExampleObject(name = "", value = "\".\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ContaResponseDTO> cadastrarConta(@Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        ContaResponseDTO contaCadastrada = contaService.cadastrarConta(contaRequestDTO);
        return ResponseEntity.created(
                URI.create("/conta/" + contaCadastrada.id())
        ).body(contaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarUsuario() {
        return ResponseEntity.ok(contaService.listarContas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarContaId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody ContaRequestDTO contaRequestDTO
    ) {
        return ResponseEntity.ok(contaService.atualizarConta(id, contaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        contaService.deletarConta(id);
        return ResponseEntity.noContent().build();
    }
}