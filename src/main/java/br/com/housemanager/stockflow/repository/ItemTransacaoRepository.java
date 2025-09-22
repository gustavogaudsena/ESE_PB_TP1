package br.com.housemanager.stockflow.repository;

import br.com.housemanager.stockflow.model.ItemTransacao;
import br.com.housemanager.stockflow.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemTransacaoRepository extends JpaRepository<ItemTransacao, UUID> {
}
