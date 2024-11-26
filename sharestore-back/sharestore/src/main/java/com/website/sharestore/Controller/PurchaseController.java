package com.website.sharestore.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Entity.Account;
import com.website.sharestore.Repository.AccountRepository;
import com.website.sharestore.Repository.PurchaseRequestRepository;
import com.website.sharestore.Repository.UserRepository;
import com.website.sharestore.Service.PurchaseRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
  private final PurchaseRequestService purchaseRequestService;
  private final PurchaseRequestRepository purchaseRequestRepository;
  private final UserRepository userRepository;
  private final AccountRepository accountRepository;


  // 구매 요청 생성
    @PostMapping("/request")
    public ResponseEntity<?> requestPurchase(
            @RequestBody PurchaseRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // 인증된 사용자 정보 가져오기
        String buyerUsername = userDetails.getUsername();
        purchaseRequestService.createPurchaseRequest(requestDto, buyerUsername);

        return ResponseEntity.status(HttpStatus.CREATED).body("Purchase request sent successfully!");
    }

    // 구매 요청에 대한 결정 (승인/거부)
    @PutMapping("/{requestId}/decision")
    public ResponseEntity<?> decideRequest(
            @PathVariable Long requestId,
            @RequestParam String decision,
            @AuthenticationPrincipal UserDetails userDetails) {

        String adminUsername = userDetails.getUsername();
        purchaseRequestService.decidePurchaseRequest(requestId, decision, adminUsername);

        return ResponseEntity.ok("Request " + decision.toLowerCase() + "ed.");
    }

    // 판매자의 계좌 정보 가져오기
    @GetMapping("/{requestId}/account")
    public ResponseEntity<Account> getSellerAccount(
            @PathVariable Long requestId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String buyerUsername = userDetails.getUsername();
        Account account = purchaseRequestService.getSellerAccount(requestId, buyerUsername);

        return ResponseEntity.ok(account);
    }



}
