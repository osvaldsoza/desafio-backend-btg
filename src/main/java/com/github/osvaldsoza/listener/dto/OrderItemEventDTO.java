package com.github.osvaldsoza.listener.dto;

import java.math.BigDecimal;

public class OrderItemEventDTO {

    private String produto;
    private Integer quantidade;
    private BigDecimal preco;

    public OrderItemEventDTO() {

    }

    public OrderItemEventDTO(String produto, Integer quantidade, BigDecimal preco) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
