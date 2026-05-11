package sales_kodnest.salessavvy.controller;

import jakarta.servlet.http.HttpServletRequest;
import sales_kodnest.salessavvy.entity.User;
import sales_kodnest.salessavvy.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // -------------------- GET CART COUNT --------------------
    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int count = cartService.getCartItemCount(user.getUserId());
        return ResponseEntity.ok(count);
    }

    // -------------------- GET CART ITEMS --------------------
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> cartItems = cartService.getCartItems(user.getUserId());
        return ResponseEntity.ok(cartItems);
    }

    // -------------------- ADD TO CART --------------------
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> requestBody,
                                          HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int productId = (int) requestBody.get("productId");
        int quantity = requestBody.get("quantity") != null ? (int) requestBody.get("quantity") : 1;

        cartService.addToCart(user.getUserId(), productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // -------------------- UPDATE CART ITEM --------------------
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItem(@RequestBody Map<String, Object> requestBody,
                                               HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int productId = (int) requestBody.get("productId");
        int quantity = (int) requestBody.get("quantity");

        cartService.updateCartItemQuantity(user.getUserId(), productId, quantity);
        return ResponseEntity.ok().build();
    }

    // -------------------- DELETE CART ITEM --------------------
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(@RequestBody Map<String, Object> requestBody,
                                              HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int productId = (int) requestBody.get("productId");

        cartService.deleteCartItem(user.getUserId(), productId);
        return ResponseEntity.noContent().build();
    }
}