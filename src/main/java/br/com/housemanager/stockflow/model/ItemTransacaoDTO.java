package br.com.housemanager.stockflow.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemTransacaoDTO(
        UUID produtoId,
        BigDecimal valor,
        Integer quantidade
) {
}
