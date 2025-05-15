package kz.dzhumanov.sportshop.controller.ui;

import kz.dzhumanov.sportshop.model.Product;
import kz.dzhumanov.sportshop.service.CartItemService;
import kz.dzhumanov.sportshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    private final ProductService productService;
    private final CartItemService cartItemService;

    @Autowired
    public PageController(ProductService productService, CartItemService cartItemService) {
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("productForm", new Product());
        return "products";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartItemService.getCartItems());
        model.addAttribute("cartTotal", cartItemService.getCartTotal());
        return "cart";
    }

    @PostMapping("/products/add")
    public String addToProduct(@ModelAttribute("productForm") Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity,
                            RedirectAttributes redirectAttributes) {
        cartItemService.addToCart(productId, quantity);
        redirectAttributes.addFlashAttribute("success", "✅ Товар добавлен в корзину");
        return "redirect:/products";
    }

    @PostMapping("/cart/increase")
    public String increaseToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartItemService.addToCart(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/products/delete/{id}")
    public String removeProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
        } catch (DataIntegrityViolationException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", "❗ Нельзя удалить: товар есть в корзине");
        }
        return "redirect:/products";
    }

    @PostMapping("/cart/delete/{id}")
    public String removeItem(@PathVariable Long id) {
        cartItemService.removeFromCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearAllItem() {
        cartItemService.clearCart();
        return "redirect:/cart";
    }
}

