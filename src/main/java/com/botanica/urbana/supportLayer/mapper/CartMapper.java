package com.botanica.urbana.supportLayer.mapper;

import com.botanica.urbana.domainLayer.entity.CartEntity;
import com.botanica.urbana.domainLayer.entity.CartItemEntity;
import com.botanica.urbana.presentationLayer.dto.response.CartItemResponseDto;
import com.botanica.urbana.presentationLayer.dto.response.CartResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Componente Mapeador encargado de transformar las entidades CartEntity y CartItemEntity
 * en sus DTOs de respuesta, calculando de forma dinámica los subtotales por producto y el total general.
 */
@Component
public class CartMapper {

    /**
     * Convierte una entidad CartItemEntity a su DTO de respuesta,
     * calculando automáticamente el subtotal (precio unitario * cantidad).
     *
     * @param itemEntity Entidad del ítem del carrito.
     * @return DTO CartItemResponseDto calculado.
     */
    public CartItemResponseDto toCartItemResponseDto(CartItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        }

        BigDecimal unitPrice = (itemEntity.getProduct() != null && itemEntity.getProduct().getPrice() != null)
                ? itemEntity.getProduct().getPrice()
                : BigDecimal.ZERO;

        int quantity = itemEntity.getQuantity() != null ? itemEntity.getQuantity() : 0;
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

        return CartItemResponseDto.builder()
                .id(itemEntity.getId())
                .productId(itemEntity.getProduct() != null ? itemEntity.getProduct().getId() : null)
                .productName(itemEntity.getProduct() != null ? itemEntity.getProduct().getName() : "")
                .productImageUrl(itemEntity.getProduct() != null ? itemEntity.getProduct().getImageUrl() : "")
                .unitPrice(unitPrice)
                .quantity(quantity)
                .subtotal(subtotal)
                .build();
    }

    /**
     * Convierte una entidad CartEntity completa a su DTO de respuesta,
     * mapeando la información del usuario logueado, la lista de ítems y acumulando el gran total a pagar.
     *
     * @param cartEntity Entidad del carrito de compras.
     * @return DTO CartResponseDto formateado para la vista del carrito.
     */
    public CartResponseDto toCartResponseDto(CartEntity cartEntity) {
        if (cartEntity == null) {
            return CartResponseDto.builder()
                    .items(new ArrayList<>())
                    .totalAmount(BigDecimal.ZERO)
                    .totalItems(0)
                    .build();
        }

        List<CartItemResponseDto> itemDtos = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        if (cartEntity.getItems() != null) {
            for (CartItemEntity item : cartEntity.getItems()) {
                CartItemResponseDto itemDto = toCartItemResponseDto(item);
                itemDtos.add(itemDto);
                totalAmount = totalAmount.add(itemDto.getSubtotal());
                totalItems += itemDto.getQuantity();
            }
        }

        return CartResponseDto.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUser() != null ? cartEntity.getUser().getId() : null)
                .userFullName(cartEntity.getUser() != null ? cartEntity.getUser().getFullName() : "")
                .userEmail(cartEntity.getUser() != null ? cartEntity.getUser().getEmail() : "")
                .items(itemDtos)
                .totalAmount(totalAmount)
                .totalItems(totalItems)
                .build();
    }
}