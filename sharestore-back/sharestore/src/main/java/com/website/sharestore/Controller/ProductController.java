package com.website.sharestore.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.website.sharestore.Dto.Request.ProductRequestDto;
import com.website.sharestore.Dto.Response.ProductResponseDto;
import com.website.sharestore.Service.ImageUploadService;
import com.website.sharestore.Service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Autowired
    private ImageUploadService imageUploadService;
    

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

    // -------------------------------------------------------------------------------
    @Autowired
    public ProductController(ProductService productService, ImageUploadService imageUploadService) {
        this.productService = productService;
        this.imageUploadService = imageUploadService;
    }

    // 상품 수정 API
    
    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("productId") Long itemKey,
            @RequestPart("product") ProductRequestDto productRequestDto,
            @RequestPart(value = "images", required = false) MultipartFile[] files) {
            try {
                // Handle file upload if files are present
                List<String> imageUrls = new ArrayList<>();
                if (files != null) {
                    for (MultipartFile file : files) {
                        String imageUrl = imageUploadService.uploadImage(file);
                        imageUrls.add(imageUrl);
                    }
                }
            productRequestDto.setImages(imageUrls); // Set the image URLs in the request DTO
            productService.updateProduct(itemKey, productRequestDto,files);
            return ResponseEntity.ok("상품 수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 수정 실패");
        }
    }
    
    // 상품 삭제 API

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long ItemKey) {
        productService.deleteProduct(ItemKey);
        return ResponseEntity.ok().build();
    }

    // 상품 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam("keyword") String keyword) {
        List<ProductResponseDto> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    // 카테고리 API
    @GetMapping("/category")
    public ResponseEntity<List<ProductResponseDto>> categoryProducts(@RequestParam("category") String category) {
        List<ProductResponseDto> products = productService.categoryProducts(category);
        return ResponseEntity.ok(products);
    }

    //=========================================================================================================

    // @PostMapping("/{productId}/like")
    // public ResponseEntity<String> toggleLike(@PathVariable("productId") Long itemKey, @RequestParam Long userKey) {
    //     boolean isLiked = productService.toggleLike(itemKey, userKey);
    //     return ResponseEntity.ok(isLiked ? "Liked" : "Unliked");
    // }

    // @GetMapping("/{productId}/likes")
    // public ResponseEntity<Integer> getLikeCount(@PathVariable("productId") Long itemKey) {
    //     int likeCount = productService.getLikeCount(itemKey);
    //     return ResponseEntity.ok(likeCount);
    // }
}
