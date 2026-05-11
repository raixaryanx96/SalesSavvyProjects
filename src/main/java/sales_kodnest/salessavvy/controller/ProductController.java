package sales_kodnest.salessavvy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import sales_kodnest.salessavvy.entity.Product;
import sales_kodnest.salessavvy.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) String category,
            HttpServletRequest request) {

        try {

            // Fetch products
            List<Product> products = productService.getProductsByCategory(category);

            // Response map
            Map<String, Object> response = new HashMap<>();

            // Product list
            List<Map<String, Object>> productList = new ArrayList<>();

            for (Product product : products) {

                Map<String, Object> productDetails = new HashMap<>();

                productDetails.put("product_id", product.getProductId());
                productDetails.put("name", product.getName());
                productDetails.put("description", product.getDescription());
                productDetails.put("price", product.getPrice());
                productDetails.put("stock", product.getStock());

                List<String> images =
                        productService.getProductImages(product.getProductId());

                productDetails.put("images", images);

                productList.add(productDetails);
            }

            response.put("products", productList);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}