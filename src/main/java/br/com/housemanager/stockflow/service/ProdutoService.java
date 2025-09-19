package br.com.housemanager.stockflow.service;

import br.com.housemanager.stockflow.model.Produto;
import br.com.housemanager.stockflow.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    public Page<Produto> listar(Integer page, Integer size) {
        int p = page == null ? 0 : Math.max(0, page);
        int s = size == null ? 0 : Math.max(1, size);
        PageRequest pageRequest = PageRequest.of(p, s);
        return repository.findAll(pageRequest);
    }

    public Produto obterPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Transação não encontrada: " + id));
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(UUID id, Produto produto) {
        produto.setId(id);
        return repository.save(produto);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    public boolean existeProdutoPorId(UUID id) {
        return repository.existsById(id);
    }
}
