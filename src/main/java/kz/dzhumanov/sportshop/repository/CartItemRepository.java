package kz.dzhumanov.sportshop.repository;

import kz.dzhumanov.sportshop.model.CartItem;
import kz.dzhumanov.sportshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

        Optional<CartItem> findByProduct(Product product);

        Optional<CartItem> findByProduct_Id(Long productId);

}
