package br.com.housemanager.stockflow.handler;


import br.com.housemanager.stockflow.dto.AiAnalyzedItem;
import br.com.housemanager.stockflow.service.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@Slf4j
public class TransacaoListener {

    private final TransacaoService service;

    public TransacaoListener(TransacaoService service) {
        this.service = service;
    }

    @Bean
    public Consumer<AiAnalysisCompleted> aiAnalysisInput() {
        return event -> {
            log.info("Recebido no Stockflow: {}, user: {}", event.jobId(), event.userId());
            service.adicionarTransacaoPorNota(event.aiResult(), event.userId());
        };
    }

    public record AiAnalysisCompleted(
            String jobId,
            List<AiAnalyzedItem> aiResult,
            String userId,
            Instant ts

    ) {}

}
