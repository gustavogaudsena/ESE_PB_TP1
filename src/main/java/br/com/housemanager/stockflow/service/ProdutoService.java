package br.com.housemanager.stockflow.service;

import br.com.housemanager.stockflow.model.Produto;
import br.com.housemanager.stockflow.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> obterPorId(@PathVariable Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}
