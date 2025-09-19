package br.com.housemanager.stockflow.controller;

import br.com.housemanager.stockflow.model.Produto;
import br.com.housemanager.stockflow.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(
            @RequestHeader(value = "X-Page", required = false, defaultValue = "0") int page,
            @RequestHeader(value = "X-Size", required = false, defaultValue = "10") int size
    ) {
        Page<Produto> produtos = service.listar(page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(produtos.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(produtos.getTotalPages()))
                .header("X-Page-Size", String.valueOf(size))
                .body(produtos.getContent());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable UUID id, @RequestBody Produto produto) {
        return ResponseEntity.ok(service.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
