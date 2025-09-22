package br.com.housemanager.stockflow.controller;

import br.com.housemanager.stockflow.dto.ItemTransacaoDTO;
import br.com.housemanager.stockflow.model.ItemTransacao;
import br.com.housemanager.stockflow.model.Transacao;
import br.com.housemanager.stockflow.model.TransacaoFiltro;
import br.com.housemanager.stockflow.service.ProdutoService;
import br.com.housemanager.stockflow.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService service;
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<?> listar(
            @ModelAttribute TransacaoFiltro filtro,
            @RequestHeader(value = "X-Page", required = false, defaultValue = "0") int page,
            @RequestHeader(value = "X-Size", required = false, defaultValue = "10") int size
    ) {
        Page<Transacao> transacoes = service.listar(filtro, page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(transacoes.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(transacoes.getTotalPages()))
                .header("X-Page-Size", String.valueOf(size))
                .body(transacoes.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Transacao> criar(@RequestBody Transacao transacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(transacao));
    }

    @PostMapping("/{transacaoId}/item")
    public ResponseEntity<ItemTransacao> adicionarItem(@PathVariable UUID transacaoId, @RequestBody ItemTransacaoDTO itemTransacao) {
        ItemTransacao item = service.adicionarItemTransacao(transacaoId, itemTransacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/{transacaoId}/item/{itemId}")
    public ResponseEntity<Object> removerItem(@PathVariable UUID transacaoId, @PathVariable UUID itemId) {
        Transacao transacao = service.removerItemTransacao(transacaoId, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{transacaoId}/item/{itemId}")
    public ResponseEntity<ItemTransacao> atualizarItemTransacao(@PathVariable UUID transacaoId, @PathVariable UUID itemId, @RequestBody ItemTransacaoDTO itemTransacao) {
        ItemTransacao item = service.atualizarItemTransacao(transacaoId, itemId, itemTransacao);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }


}
