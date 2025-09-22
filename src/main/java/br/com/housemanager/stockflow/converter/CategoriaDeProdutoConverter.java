package br.com.housemanager.stockflow.converter;

import br.com.housemanager.stockflow.model.CategoriaDeProduto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDeProdutoConverter implements Converter<String, CategoriaDeProduto> {
    @Override
    public CategoriaDeProduto convert(String source) {
        for (CategoriaDeProduto categoria : CategoriaDeProduto.values()) {
            if (categoria.getNomeExibicao().equalsIgnoreCase(source)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + source);
    }
}
