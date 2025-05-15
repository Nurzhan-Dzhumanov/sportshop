package kz.dzhumanov.sportshop.controller.rest;

import jakarta.validation.Valid;
import kz.dzhumanov.sportshop.dto.AddToCartRequest;
import kz.dzhumanov.sportshop.model.CartItem;
import kz.dzhumanov.sportshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public List<CartItem> getCart() {
        return cartItemService.getCartItems();
    }

    @PostMapping
    public ResponseEntity<CartItem> addCart(@Valid @RequestBody AddToCartRequest request) {
        Long productId = request.getProductId();
        int quantity = request.getQuantity();
        return ResponseEntity.ok(cartItemService.addToCart(productId, quantity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        cartItemService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartItemService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
