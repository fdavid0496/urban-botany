package com.botanica.urbana.domainLayer.service.impl;

import com.botanica.urbana.dataAccessLayer.repository.CartItemRepository;
import com.botanica.urbana.dataAccessLayer.repository.CartRepository;
import com.botanica.urbana.domainLayer.entity.CartEntity;
import com.botanica.urbana.domainLayer.entity.CartItemEntity;
import com.botanica.urbana.domainLayer.entity.ProductEntity;
import com.botanica.urbana.domainLayer.entity.UserEntity;
import com.botanica.urbana.domainLayer.service.CartService;
import com.botanica.urbana.domainLayer.service.ProductService;
import com.botanica.urbana.domainLayer.service.UserService;
import com.botanica.urbana.presentationLayer.dto.request.AddToCartRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.CartResponseDto;
import com.botanica.urbana.supportLayer.exception.BadRequestException;
import com.botanica.urbana.supportLayer.exception.ResourceNotFoundException;
import com.botanica.urbana.supportLayer.exception.UnauthorizedAccessException;
import com.botanica.urbana.supportLayer.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Implementación del servicio CartService.
 * Maneja la lógica de negocio para la gestión del carrito de compras y garantiza
 * la persistencia de productos vinculados a la cuenta de cada usuario en MySQL.
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponseDto getCartByUserEmail(String userEmail) {
        CartEntity cart = getOrCreateCartEntity(userEmail);
        return cartMapper.toCartResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto addProductToCart(String userEmail, AddToCartRequestDto addToCartRequestDto) {
        CartEntity cart = getOrCreateCartEntity(userEmail);
        ProductEntity product = productService.getProductEntityById(addToCartRequestDto.getProductId());

        Optional<CartItemEntity> existingItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItemOpt.isPresent()) {
            CartItemEntity existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + addToCartRequestDto.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItemEntity newItem = CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(addToCartRequestDto.getQuantity())
                    .build();
            cart.getItems().add(newItem);
            cartRepository.save(cart);
        }

        return cartMapper.toCartResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto updateCartItemQuantity(String userEmail, Long cartItemId, Integer newQuantity) {
        if (newQuantity == null || newQuantity <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero.");
        }

        CartEntity cart = getOrCreateCartEntity(userEmail);
        CartItemEntity cartItem = getCartItemEntityById(cartItemId);

        validateItemOwnership(cart, cartItem);

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto removeCartItem(String userEmail, Long cartItemId) {
        CartEntity cart = getOrCreateCartEntity(userEmail);
        CartItemEntity cartItem = getCartItemEntityById(cartItemId);

        validateItemOwnership(cart, cartItem);

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return cartMapper.toCartResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto clearCart(String userEmail) {
        CartEntity cart = getOrCreateCartEntity(userEmail);
        cartItemRepository.deleteByCartId(cart.getId());
        cart.getItems().clear();

        return cartMapper.toCartResponseDto(cart);
    }

    /**
     * Obtiene el carrito del usuario o crea una nueva instancia persistente si aún no existe en MySQL.
     */
    private CartEntity getOrCreateCartEntity(String userEmail) {
        UserEntity user = userService.getUserEntityByEmail(userEmail);
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(
                        CartEntity.builder()
                                .user(user)
                                .items(new ArrayList<>())
                                .build()
                ));
    }

    /**
     * Busca un ítem de carrito por ID o lanza una excepción 404 si no existe.
     */
    private CartItemEntity getCartItemEntityById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem de carrito", "id", cartItemId));
    }

    /**
     * Valida que el ítem pertenezca efectivamente al carrito del usuario autenticado.
     */
    private void validateItemOwnership(CartEntity cart, CartItemEntity cartItem) {
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new UnauthorizedAccessException("No tiene autorización para modificar este ítem del carrito.");
        }
    }
}