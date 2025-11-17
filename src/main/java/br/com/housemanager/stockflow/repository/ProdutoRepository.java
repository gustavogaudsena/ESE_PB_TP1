package br.com.housemanager.stockflow.repository;

import br.com.housemanager.stockflow.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    Optional<Produto> findFirstByNome(String nome);
}