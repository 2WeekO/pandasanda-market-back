package com.website.sharestore.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Dto.Response.AccountResponseDto;
import com.website.sharestore.Dto.Response.PurchaseResponseDto;
import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.Product;
import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Repository.AccountRepository;
import com.website.sharestore.Repository.ProductRepository;
import com.website.sharestore.Repository.PurchaseRequestRepository;
import com.website.sharestore.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


// 구매 요청 처리 서비스
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final ProductRepository productRepository;
    //private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    // 구매 요청 생성
    @Transactional
    public PurchaseRequest createPurchaseRequest(PurchaseRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!product.getStatus().equals("판매 중")) {
            throw new IllegalStateException("판매 중이 아니거나 거래 중인 상품입니다.");
        }

        PurchaseRequest purchaseRequest = new PurchaseRequest(requestDto.getBuyerId(), product);

        if(product.getSellerId().equals(purchaseRequest.getBuyerId())) {
            throw new IllegalStateException("구매자와 판매자가 같습니다.");
        }
        purchaseRequest.setMessage(requestDto.getMessage());
        purchaseRequest.setSellerId(product.getSellerId());
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
    
        // 관련 상품 가져오기
        
        // 상태 변경
        purchaseRequest.setStatus("승인됨");
    
        // 저장
        purchaseRequestRepository.save(purchaseRequest);
    
        return new PurchaseResponseDto(purchaseRequest);
    }

    // 구매 요청 승인 확인
    @Transactional
    public PurchaseResponseDto approveCheckPurchaseRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    
        // 상태 변경
        purchaseRequest.setStatus("승인 확인 완료");
        
    
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

    //구매 요청 거절 확인 (구매 요청 삭제)
    @Transactional
    public PurchaseResponseDto rejectCheckPurchaseRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        
        Product product = purchaseRequest.getProduct();

        // 삭제 실행
        purchaseRequestRepository.deleteById(requestId);
        product.setStatus("판매 중");
    
        // 삭제된 요청 정보 반환
        return new PurchaseResponseDto(purchaseRequest);
    }

    // 결제 요청
    @Transactional
    public PurchaseResponseDto paymentRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        
        purchaseRequest.setStatus("결제 요청중");
        // 계좌 정보 데이터 옮기기
        
        purchaseRequestRepository.save(purchaseRequest);

        return new PurchaseResponseDto(purchaseRequest);
    }
    // 결제 요청 수락
    @Transactional
    public PurchaseResponseDto paymentCheckRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        purchaseRequest.setStatus("결제 요청 수락");
        purchaseRequestRepository.save(purchaseRequest);

        return new PurchaseResponseDto(purchaseRequest);
    }

    // 결제 완료 요청
    @Transactional
    public PurchaseResponseDto paymentComplteRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        purchaseRequest.setStatus("결제 완료 요청");
        purchaseRequestRepository.save(purchaseRequest);

        return new PurchaseResponseDto(purchaseRequest);
    }

    // 결제 완료 확인
    @Transactional
    public PurchaseResponseDto paymentCompleteCheckRequest(Long requestId) {
        PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        Product product = purchaseRequest.getProduct();

        product.setStatus("판매 완료");
        purchaseRequest.setStatus("판매 완료 상품");

        purchaseRequestRepository.save(purchaseRequest);
        productRepository.save(product);

        return new PurchaseResponseDto(purchaseRequest);
    }


    //------------------------------------------ 조회 메소드 -----------------------------------
    
    // 모든 구매 요청 조회
    public List<PurchaseResponseDto> getAllPurchaseRequests() {
        return purchaseRequestRepository.findAll()
                .stream()
                .map(PurchaseResponseDto::new)
                .collect(Collectors.toList());
    }

    // 구매자 요청 조회 로직
    public List<PurchaseResponseDto> getRequestsByBuyer(Long buyerId) {
        List<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findByBuyerId(buyerId);
        
        return purchaseRequests.stream()

        .map(request -> new PurchaseResponseDto(
        request.getRequestId(),
        request.getBuyerId(),
        request.getSellerId(),
        request.getProduct().getItemKey(),
        request.getStatus(),
        request.getMessage(),
        request.getProduct().getProductName(),
        request.getProduct().getPrice()
        // request.getAccount().getBankName(),
        // request.getAccount().getAccountNumber(),
        // request.getAccount().getHolderName()
        ))
        .toList();
    }

    // 판매자 요청 조회 로직
    public List<PurchaseResponseDto> getRequestsBySeller(Long sellerId) {  // 판매자ID에 따른 거래 내역
        List<PurchaseRequest> requests = purchaseRequestRepository.findByProduct_SellerId(sellerId);

        
        return requests.stream()

        .map(request -> new PurchaseResponseDto(
        request.getRequestId(),
        request.getBuyerId(),
        request.getSellerId(),
        request.getProduct().getItemKey(),
        request.getStatus(),
        request.getMessage(),
        request.getProduct().getProductName(),
        request.getProduct().getPrice()
        // request.getAccount().getBankName(),
        // request.getAccount().getAccountNumber(),
        // request.getAccount().getHolderName()

        ))
        .toList();
    }

    // 계좌 확인 (판매자 계좌 조회)
    public AccountResponseDto getAccountBySeller(Long sellerId) {
        Account account = accountRepository.findByUser_UserKey(sellerId)
            .orElseThrow(() -> new EntityNotFoundException("해당 판매자의 계좌 정보를 찾을 수 없습니다."));

        return new AccountResponseDto(
            account.getAccountKey(),
            account.getBankName(),
            account.getAccountNumber(),
            account.getHolderName()
        );
    }


}