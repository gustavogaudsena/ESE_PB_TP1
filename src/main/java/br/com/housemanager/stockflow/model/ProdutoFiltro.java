package br.com.housemanager.stockflow.model;


public record ProdutoFiltro(
        String nome,
        CategoriaDeProduto categoria
) {
    public boolean estaVazio() {
        return !hasText(nome) && !hasText(categoria.toString());
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

}
