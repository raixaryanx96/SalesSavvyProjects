package sales_kodnest.salessavvy.repository;

import jakarta.transaction.Transactional;
import sales_kodnest.salessavvy.entity.ProductImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository <ProductImage,Integer> {

List<ProductImage> findByProduct_ProductId(Integer productId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImage pi WHERE pi.product.productId = :productId")
    void deleteByProductId(Integer productId);
	
}
