package kz.dzhumanov.sportshop.service;

import kz.dzhumanov.sportshop.model.CartItem;
import kz.dzhumanov.sportshop.model.Product;
import kz.dzhumanov.sportshop.repository.CartItemRepository;
import kz.dzhumanov.sportshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cartItemRepository.findByProduct(product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }

        CartItem newItem = CartItem.builder()
                .product(product)
                .quantity(quantity)
                .build();
        return cartItemRepository.save(newItem);
    }

    public void removeFromCart(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new RuntimeException("Cart item not found"));

        int newQuantity = item.getQuantity() - 1;
        if (newQuantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        }
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }

}
