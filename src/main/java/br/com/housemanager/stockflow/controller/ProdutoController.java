package br.com.housemanager.stockflow.controller;

import br.com.housemanager.stockflow.model.Produto;
import br.com.housemanager.stockflow.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterPorId(@PathVariable UUID id) {
        return produtoService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable UUID id, @RequestBody Produto produto) {
        produto.setId(id);
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable UUID id) {
        produtoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
