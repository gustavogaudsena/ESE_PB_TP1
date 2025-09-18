package br.com.housemanager.stockflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.OffsetDateTime;
import java.util.List;

public class Transacao {

    @OneToMany(mappedBy = "")
    List<ItemTransacao> transacoes;
    @Column(nullable = false, name = "criadoEm") //data_hora
    private OffsetDateTime criadoEm;
    private StatusTransacao status;
    private double valorTotal;


    public enum StatusTransacao { ABERTO, PAGO, CANCELADO}
}
