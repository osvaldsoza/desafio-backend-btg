package com.github.osvaldsoza.desafiobackendbtg.config;

import com.github.osvaldsoza.desafiobackendbtg.entity.OrderEntity;
import com.github.osvaldsoza.desafiobackendbtg.entity.OrderItem;
import com.github.osvaldsoza.desafiobackendbtg.listener.dto.OrderCreatedEventDTO;
import com.github.osvaldsoza.desafiobackendbtg.listener.dto.OrderItemEventDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMaper(){
        return new ModelMapper();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<OrderCreatedEventDTO, OrderEntity>() {
            @Override
            protected void configure() {
                map().setOrderId(source.getCodigoPedido());
                map().setCustomerId(source.getCodigoCliente());
            }
        });

        modelMapper.addMappings(new PropertyMap<OrderItemEventDTO, OrderItem>() {
            @Override
            protected void configure() {
                map().setPrice(source.getPreco());
                map().setProduct(source.getProduto());
                map().setQuantity(source.getQuantidade());
            }
        });

        return modelMapper;
    }
}
