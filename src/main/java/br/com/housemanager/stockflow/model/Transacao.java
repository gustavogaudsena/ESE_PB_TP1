package br.com.housemanager.stockflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transacoes",
        indexes = { @Index(name="idx_transacao_criado_em", columnList = "criado_em") })
public class Transacao {

    @Id@GeneratedValue()
    private UUID id;

    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<ItemTransacao> itensTransacao;

    @Column(nullable = false, name = "criado_em") //data_hora
    private OffsetDateTime criadoEm;

    @Column(nullable = true, name = "atualizado_em") //data_hora
    private OffsetDateTime atualizadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTransacao status = StatusTransacao.ABERTO;

    @Column(nullable = true, precision = 9, scale = 6)
    private BigDecimal valorTotal;


    public void adicionarTransacao(ItemTransacao t) { t.setTransacao(this); itensTransacao.add(t); }
    public void removerTransacao(ItemTransacao t) { t.setTransacao(null); itensTransacao.remove(t); }

    public enum StatusTransacao { ABERTO, PAGO, CANCELADO}
}
