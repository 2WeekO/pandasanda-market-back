package com.website.sharestore.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.website.sharestore.Dto.Response.ProductResponseDto;
import com.website.sharestore.Service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    

    @PostMapping("/register")
    public ResponseEntity<ProductResponseDto> registerProduct(
            @RequestParam("productName") String productName,
            @RequestParam("category1") String category1,
            @RequestParam("category2") String category2,
            @RequestParam("category3") String category3,
            @RequestParam("productCondition") String productCondition,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("quantity") int quantity,
            @RequestParam("tradeMethod") String tradeMethod,
            @RequestParam("wayComment") String wayComment,
            @RequestParam("shippingMethod") String shippingMethod,
            @RequestParam(value = "images", required = false) MultipartFile[] images, // 이미지 배열을 받을 때
            @AuthenticationPrincipal UserDetails userDetails) {  // UserDetails 사용

        // userDetails.getUsername()을 사용하여 이메일을 가져옴
        String userEmail = userDetails.getUsername();
        
        
        
        // 상품 등록 서비스 호출
        ProductResponseDto productResponseDto = productService.registerProduct(
            productName,
            category1,
            category2,
            category3,
            productCondition,
            description,
            price,
            quantity,
            tradeMethod,
            wayComment,
            shippingMethod,
            images, // 이미지를 포함
            userEmail
        );

        return ResponseEntity.ok(productResponseDto);
    }

    // GetMapping("/all") 정상 작동 확인
    @GetMapping("/all")  // 상품 전체 리스트
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // GetMapping("/{productId}") 정상 작동 확인
    @GetMapping("/{productId}") // 상품 상세
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long itemKey) {
        ProductResponseDto product = productService.getProductById(itemKey);
        return ResponseEntity.ok(product);
    }

    // 상품 수정
    // @PutMapping("/{productId}")
    
    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long ItemKey) {
        productService.deleteProduct(ItemKey);
        return ResponseEntity.noContent().build();
    }
}
