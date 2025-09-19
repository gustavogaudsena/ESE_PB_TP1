package br.com.housemanager.stockflow.repository;

import br.com.housemanager.stockflow.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}