package com.github.osvaldsoza.listener.dto;

import java.util.List;

public class OrderCreatedEventDTO {

    private Long codigoPedido;
    private Long codigoCliente;
    private List<OrderItemEventDTO> itens;

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(Long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<OrderItemEventDTO> getItens() {
        return itens;
    }

    public void setItens(List<OrderItemEventDTO> itens) {
        this.itens = itens;
    }
}
