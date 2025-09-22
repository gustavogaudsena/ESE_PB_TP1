package br.com.housemanager.stockflow.service;


import br.com.housemanager.stockflow.dto.ItemTransacaoDTO;
import br.com.housemanager.stockflow.model.*;
import br.com.housemanager.stockflow.repository.ItemTransacaoRepository;
import br.com.housemanager.stockflow.repository.ProdutoRepository;
import br.com.housemanager.stockflow.repository.TransacaoRepository;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransacaoService {
    private static final ZoneId ZONE_ZP = ZoneId.of("America/Sao_Paulo");
    private final TransacaoRepository transacaoRepository;
    private final ItemTransacaoRepository itemTransacaoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Page<Transacao> listar(TransacaoFiltro filtro, Integer page, Integer size) {
        int p = page == null ? 0 : Math.max(0, page);
        int s = size == null ? 0 : Math.max(1, size);
        PageRequest pageRequest = PageRequest.of(p, s);

        List<Specification<Transacao>> specifications = new ArrayList<>();

        if (filtro.status() != null) {
            specifications.add((root, query, cb) -> cb.equal(root.get("status"), filtro.status()));
        }
        if (filtro.de() != null) {
            specifications.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("criadoEm"), filtro.de()));
        }
        if (filtro.ate() != null) {
            specifications.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("criadoEm"), filtro.ate()));
        }
        if (filtro.categoria() != null) {

            specifications.add((root, query, cb) -> {
                query.distinct(true);
                Join<Transacao, ItemTransacao> joinItens = root.join("itensTransacao");

                return cb.equal(joinItens.get("produto").get("categoria"), filtro.categoria());
            });
        }
        if (filtro.valorTotalMin() != null) {
            specifications.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("valorTotal"), filtro.valorTotalMin()));
        }
        if (filtro.valorTotalMax() != null) {
            specifications.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("valorTotal"), filtro.valorTotalMax()));
        }

        Specification<Transacao> spec = specifications.stream()
                .reduce(Specification::and)
                .orElse(null);

        return transacaoRepository.findAll(spec, pageRequest);
    }

    @Transactional(readOnly = true)
    public Transacao obterPorId(UUID id) {
        return transacaoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transação não encontrada: " + id));
    }

    @Transactional
    public ItemTransacao adicionarItemTransacao(UUID transacaoId, ItemTransacaoDTO dto) {
        Transacao transacao;

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + dto.produtoId()));

        transacao = obterPorId(transacaoId);

        ItemTransacao item = new ItemTransacao();
        item.setValor(dto.valor());
        item.setQuantidade(dto.quantidade());
        item.setProduto(produto);

        transacao.adicionarItemTransacao(item);

        item.setTransacao(transacao);
        ItemTransacao itemSalvo = itemTransacaoRepository.save(item);
        salvar(transacao);

        return itemSalvo;
    }

    @Transactional
    public Transacao removerItemTransacao(UUID transacaoId, @PathVariable UUID itemId) {
        Transacao transacao = obterPorId(transacaoId);
        ItemTransacao item = transacao.getItensTransacao()
                .stream()
                .filter(i -> i.getProduto().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item não encontrado na transação: " + itemId));

        transacao.removerItemTransacao(item);

        return salvar(transacao);
    }

    @Transactional
    public ItemTransacao atualizarItemTransacao(UUID transacaoId, @PathVariable UUID itemId, ItemTransacaoDTO dto) {
        Transacao transacao = obterPorId(transacaoId);

        ItemTransacao item = transacao.getItensTransacao()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item não encontrado na transação: " + itemId));

        if (dto.produtoId() != null && !dto.produtoId().equals(item.getProduto().getId())) {
            Produto novoProduto = produtoRepository.findById(dto.produtoId())
                    .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + dto.produtoId()));
            item.setProduto(novoProduto);
        }

        if (dto.quantidade() != null) item.setQuantidade(dto.quantidade());
        if (dto.valor() != null) item.setValor(dto.valor());

        salvar(transacao);

        return item;
    }

    @Transactional
    public void cancelarTransacao(UUID id) {
        Transacao transacao = obterPorId(id);
        transacao.cancelarTransacao();
        salvar(transacao);
    }

    @Transactional
    public void concluirTransacao(UUID id) {
        Transacao transacao = obterPorId(id);
        transacao.concluirTransacao();
        salvar(transacao);
    }

    @Transactional
    public Transacao salvar(Transacao transacao) {
        if (transacao.getCriadoEm() == null) transacao.setCriadoEm(OffsetDateTime.now(ZONE_ZP));
        transacao.setAtualizadoEm(OffsetDateTime.now(ZONE_ZP));
        transacao.calcularValorTotal();

        return transacaoRepository.save(transacao);
    }
}
