package br.com.housemanager.stockflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itens_transacao")
public class ItemTransacao {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_itens_transacao_produto"))
    private Produto produto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id", foreignKey = @ForeignKey(name = "fk_itens_transacao_transacao"))
    private Transacao transacao;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer quantidade;
}
