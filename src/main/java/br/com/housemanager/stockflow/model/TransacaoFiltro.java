package br.com.housemanager.stockflow.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record TransacaoFiltro(
        BigDecimal valorTotalMin,
        BigDecimal valorTotalMax,
        String status,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate de,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate ate,
        CategoriaDeProduto categoria
) {
    public boolean estaVazio() {
        return !temTexto(status)
                && valorTotalMin == null
                && valorTotalMax == null
                && de == null
                && ate == null
                && categoria != null;
    }

    public boolean parametrosInvalidos() {
        return (valorTotalMin != null && valorTotalMax != null && valorTotalMin.compareTo(valorTotalMax) > 0)
                || (de != null && ate != null && de.isAfter(ate));
    }

    private static boolean temTexto(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
