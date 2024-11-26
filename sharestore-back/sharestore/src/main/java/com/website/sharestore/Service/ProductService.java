package com.website.sharestore.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.website.sharestore.Dto.Request.ProductRequestDto;
import com.website.sharestore.Dto.Response.ProductResponseDto;
import com.website.sharestore.Entity.Product;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.ProductRepository;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageUploadService imageUploadService; // ImageUploadService 주입



    // 상품 등록 메서드 ----------------------------------------------------------
    public ProductResponseDto registerProduct(
        String productName,
        String category1,
        String category2,
        String category3,
        String productCondition,
        String description,
        int price,
        int quantity,
        String tradeMethod,
        String wayComment,
        String shippingMethod,
        MultipartFile[] images,
        String userEmail
        ) {

        // 사용자 조회 ----------------------------------------------------------
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        

        // Product 엔티티 생성 ----------------------------------------------------------
        Product product = Product.builder()
            .productName(productName)
            .category1(category1)
            .category2(category2)
            .category3(category3)
            .price(price)
            .productCondition(productCondition)
            .description(description)
            .quantity(quantity)
            .tradeMethod(tradeMethod)
            .wayComment(wayComment)
            .shippingMethod(shippingMethod)
            .user(user)
            .build();

        // S3에 이미지 업로드하고 URL 리스트 받기
        List<String> imageUrls = uploadImagesToS3(images);
        product.setImageUrls(imageUrls); // 이미지 URL을 상품에 설정
        product.getUser().getNickname();
        product.getUser().getAddress();
        product.getUser().getUserKey();

        productRepository.save(product);

        // ProductResponseDto 생성 ----------------------------------------------------------
        return ProductResponseDto.builder()
            .userKey(user.getUserKey())
            .itemKey(product.getItemKey())
            .productName(product.getProductName())
            .category1(product.getCategory1())
            .category2(product.getCategory2())
            .category3(product.getCategory3())
            .price(product.getPrice())
            .productCondition(product.getProductCondition())
            .description(product.getDescription())
            .quantity(product.getQuantity())
            .tradeMethod(product.getTradeMethod())
            .wayComment(product.getWayComment())
            .shippingMethod(product.getShippingMethod())
            .viewCount(product.getViewCount())
            .productRegisterDate(product.getProductRegisterDate())
        // 유저 정보 얻기
            .userEmail(user.getEmail())
            .userNickname(user.getNickname())
            .userAddress(user.getAddress())
        //-------------------
            .images(product.getImageUrls()) // 이미지 URL 리스트 추가
            .build();
    }

    // S3에 이미지 업로드하는 메서드 ----------------------------------------------------------
    private List<String> uploadImagesToS3(MultipartFile[] images) {
        return List.of(images).stream()
            .map(imageUploadService::uploadImage) // S3에 업로드
            .collect(Collectors.toList());
    }

    // 모든 상품을 가져오는 메서드 ----------------------------------------------------------
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
            .map(product -> ProductResponseDto.builder()
                .itemKey(product.getItemKey())
                .images(product.getImageUrls())                     // 이미지 URL 리스트 추가
                .productName(product.getProductName())              // 상품 이름 추가
                .category1(product.getCategory1())                  // 카테고리 추가
                .category2(product.getCategory2())                  // 카테고리 추가
                .category3(product.getCategory3())                  // 카테고리 추가
                .tradeMethod(product.getTradeMethod())              // 직거래
                .shippingMethod(product.getShippingMethod())        // 배송 방법
                .price(product.getPrice())                          // 상품 가격 추가
                .productCondition(product.getProductCondition())    // 상품 상태 추가
                .viewCount(product.getViewCount())                  // 상품 조회수 추가
                .userEmail(product.getUser().getEmail())            // 유저 이메일 추가
                .userNickname(product.getUser().getNickname())
                .userAddress(product.getUser().getAddress())
                .build()
            ).collect(Collectors.toList());
    }

    // ID로 특정 상품을 가져오는 메서드 (상품 상세)----------------------------------------------------------
    @Transactional
    public ProductResponseDto getProductById(Long itemKey) {

        productRepository.incrementViewCount(itemKey);

        Product product = productRepository.findById(itemKey)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return ProductResponseDto.builder()
            .itemKey(product.getItemKey())
            .productName(product.getProductName())
            .category1(product.getCategory1())
            .category2(product.getCategory2())
            .category3(product.getCategory3())
            .price(product.getPrice())
            .productCondition(product.getProductCondition())
            .description(product.getDescription())
            .quantity(product.getQuantity())
            .tradeMethod(product.getTradeMethod())
            .wayComment(product.getWayComment())
            .shippingMethod(product.getShippingMethod())
            .viewCount(product.getViewCount())
            .productRegisterDate(product.getProductRegisterDate())
            .userEmail(product.getUser().getEmail())
            .images(product.getImageUrls()) // 이미지 URL 리스트 추가
            .userNickname(product.getUser().getNickname())
            .userAddress(product.getUser().getAddress())
            .build();
    }

    
    // 유저의 userKey로 상품 목록을 조회(마이스토어)---------------------------------
    public List<ProductResponseDto> getProductsByUserKey(Long userKey) {
        
        List<Product> products = productRepository.findByUser_UserKey(userKey);
        
        // Product 엔티티를 ProductResponseDto로 변환
        return products.stream()
            .map(product -> ProductResponseDto.builder()
                .userKey(product.getUser().getUserKey())
                .itemKey(product.getItemKey())
                .productName(product.getProductName())
                .category1(product.getCategory1())
                .category2(product.getCategory2())
                .category3(product.getCategory3())
                .price(product.getPrice())
                .productCondition(product.getProductCondition())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .tradeMethod(product.getTradeMethod())
                .wayComment(product.getWayComment())
                .shippingMethod(product.getShippingMethod())
                .viewCount(product.getViewCount())
                .productRegisterDate(product.getProductRegisterDate())
                .userEmail(product.getUser().getEmail())
                .images(product.getImageUrls())
                .userNickname(product.getUser().getNickname())
                .userAddress(product.getUser().getAddress())
                .build())
            .collect(Collectors.toList());
    }

    // 상품 삭제 메소드(마이스토어 상품 삭제 기능)------------------------------------------------
    public void deleteProduct(Long itemKey) {

        
        // 상품 정보 가져오기
        Product product = productRepository.findById(itemKey)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        
        // S3에서 기존 이미지 삭제
        if (product.getImageUrls() != null) {
            for (String imageUrl : product.getImageUrls()) {
                String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1); // URL에서 파일명 추출
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, imageName)); // S3에서 이미지 삭제
            }
        }

        // 상품 삭제
        productRepository.deleteById(itemKey);
    }

    // 상품 수정 메소드 (마이스토어 상품 수정 기능) ---------------------------------------------
    public void updateProduct(Long itemKey, ProductRequestDto productRequestDto, MultipartFile[] images) {
    Product product = productRepository.findById(itemKey)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

    // Product 엔티티의 필드 업데이트
    product.setProductName(productRequestDto.getProductName());
    product.setCategory1(productRequestDto.getCategory1());
    product.setCategory2(productRequestDto.getCategory2());
    product.setCategory3(productRequestDto.getCategory3());
    product.setProductCondition(productRequestDto.getProductCondition());
    product.setDescription(productRequestDto.getDescription());
    product.setPrice(productRequestDto.getPrice());
    product.setQuantity(productRequestDto.getQuantity());
    product.setTradeMethod(productRequestDto.getTradeMethod());
    product.setWayComment(productRequestDto.getWayComment());
    product.setShippingMethod(productRequestDto.getShippingMethod());

    // 기존 이미지를 삭제
    if (productRequestDto.getImages() != null && !productRequestDto.getImages().isEmpty()) {
        if (product.getImageUrls() != null) {
            // 기존 이미지들 삭제
            for (String imageUrl : product.getImageUrls()) {
                String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, imageName)); // 기존 이미지 삭제
            }
        }
        // 새로운 이미지 업로드 및 URL 저장
        List<String> imageUrls = uploadImagesToS3(images);  // 이미지 URL 리스트 받기
        
        product.setImageUrls(imageUrls);  // 새로운 이미지 URL 리스트로 업데이트
    }

    // 상품 업데이트
    productRepository.save(product);
    }
    // ===================================================================================


    
}
