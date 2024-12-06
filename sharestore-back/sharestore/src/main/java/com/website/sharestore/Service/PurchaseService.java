package com.website.sharestore.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.website.sharestore.Repository.PurchaseRequestRepository;
import com.website.sharestore.Repository.ProductRepository;


import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Dto.Response.PurchaseResponseDto;
import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Entity.Product;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 구매 요청 처리 서비스
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final ProductRepository productRepository;

    // 구매 요청 생성
    @Transactional
    public PurchaseRequest createPurchaseRequest(PurchaseRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!product.getStatus().equals("판매 중")) {
            throw new IllegalStateException("Product is not available for purchase.");
        }

        PurchaseRequest purchaseRequest = new PurchaseRequest(requestDto.getBuyerId(), product);
        purchaseRequest.setMessage(requestDto.getMessage());
        product.setStatus("거래 중"); // 상품 상태 업데이트

        purchaseRequestRepository.save(purchaseRequest);
        productRepository.save(product);

        return purchaseRequest; // PurchaseRequest 반환
    }

    
    // 구매 요청 승인
    @Transactional
    public PurchaseResponseDto approvePurchaseRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        purchaseRequest.setStatus("승인됨");
        purchaseRequestRepository.save(purchaseRequest);

        return new PurchaseResponseDto(purchaseRequest);
    }

    // 구매 요청 거절
    @Transactional
    public PurchaseResponseDto rejectPurchaseRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        purchaseRequest.setStatus("거절됨");
        purchaseRequestRepository.save(purchaseRequest);

        return new PurchaseResponseDto(purchaseRequest);
    }

    // 모든 구매 요청 조회
    public List<PurchaseResponseDto> getAllPurchaseRequests() {
        return purchaseRequestRepository.findAll()
                .stream()
                .map(PurchaseResponseDto::new)
                .collect(Collectors.toList());
    }
}