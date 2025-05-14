package kz.dzhumanov.sportshop.service;

import kz.dzhumanov.sportshop.model.Product;
import kz.dzhumanov.sportshop.repository.CartItemRepository;
import kz.dzhumanov.sportshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (cartItemRepository.findByProduct_Id(id).isPresent()) {
            throw new IllegalStateException("Нельзя удалить товар: он есть в корзине");
        }
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updateProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("Product not found"));
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setCategory(updateProduct.getCategory());
        product.setQuantity(updateProduct.getQuantity());
        return productRepository.save(product);
    }
}
