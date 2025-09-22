package br.com.housemanager.stockflow.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transacoes", indexes = {@Index(name = "idx_transacao_criado_em", columnList = "criado_em")})
public class Transacao {

    @Id
    @GeneratedValue()
    private UUID id;

    @JsonManagedReference
    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<ItemTransacao> itensTransacao = new ArrayList<>();

    @Column(nullable = false, name = "criado_em")
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTransacao status = StatusTransacao.ABERTO;

    @Column(precision = 19, scale = 2, name = "valor_total")
    private BigDecimal valorTotal;

    public void adicionarItemTransacao(ItemTransacao t) {
        t.setTransacao(this);
        itensTransacao.add(t);
        calcularValorTotal();
    }

    public void removerItemTransacao(ItemTransacao t) {
        t.setTransacao(null);
        itensTransacao.remove(t);
        calcularValorTotal();
    }

    public void calcularValorTotal() {
        valorTotal = itensTransacao.stream().reduce(BigDecimal.ZERO, (acc, current) -> acc.add(current.getValorFinal()), BigDecimal::add);
    }

    public void cancelarTransacao() {
        if (podeAlterarStatus())
            throw new IllegalStateException("Só é possível cancelar uma transação com status ABERTO.");
        status = StatusTransacao.CANCELADO;
    }

    public void concluirTransacao() {
        if (podeAlterarStatus())
            throw new IllegalStateException("Só é possível concluir uma transação com status ABERTO.");
        status = StatusTransacao.CONCLUIDO;
    }

    private boolean podeAlterarStatus() {
        return status != StatusTransacao.ABERTO;
    }

    public enum StatusTransacao {ABERTO, CONCLUIDO, CANCELADO}
}
