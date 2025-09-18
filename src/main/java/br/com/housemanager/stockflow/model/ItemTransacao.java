package br.com.housemanager.stockflow.model;

import java.util.UUID;

public class ItemTransacao {
    private UUID id;
    private Produto produto;
    private Transacao transacao;
    private double valor;
    private int quantidade;
}
