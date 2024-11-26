package com.website.sharestore.Service;

import org.springframework.stereotype.Service;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Entity.Account;
import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Repository.AccountRepository;
import com.website.sharestore.Repository.PurchaseRequestRepository;
import com.website.sharestore.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseRequestService {
	private final PurchaseRequestRepository purchaseRequestRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    // 구매 요청 생성
    public void createPurchaseRequest(PurchaseRequestDto requestDto, String buyerUsername) {
        User buyer = userRepository.findByEmail(buyerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
    
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setBuyerEmail(buyer.getEmail()); // 구매자 이메일 설정
        purchaseRequest.setBuyerId(buyer.getUserKey()); // 구매자 ID를 직접 설정
        purchaseRequest.setProductId(requestDto.getProductId());
        purchaseRequest.setMessage(requestDto.getMessage());
        purchaseRequest.setStatus("PENDING");
    
        purchaseRequestRepository.save(purchaseRequest);
    }

    // 구매 요청 결정
    public void decidePurchaseRequest(Long requestId, String decision, String adminUsername) {
        PurchaseRequest request = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!"APPROVE".equalsIgnoreCase(decision) && !"REJECT".equalsIgnoreCase(decision)) {
            throw new IllegalArgumentException("Invalid decision");
        }

        request.setStatus(decision.toUpperCase());
        purchaseRequestRepository.save(request);
    }

    // 판매자의 계좌 정보 가져오기
    public Account getSellerAccount(Long requestId, String buyerUsername) {
        PurchaseRequest request = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!"APPROVED".equals(request.getStatus())) {
            throw new IllegalStateException("Request not approved");
        }

        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        return accountRepository.findByUser(seller)
                .orElseThrow(() -> new IllegalArgumentException("Seller account not found"));
    }
    


}
