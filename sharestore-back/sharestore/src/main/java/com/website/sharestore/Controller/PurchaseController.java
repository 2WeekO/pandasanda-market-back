package com.website.sharestore.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Dto.Response.AccountResponseDto;
import com.website.sharestore.Dto.Response.PurchaseResponseDto;
import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Service.PurchaseService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/create")
    public ResponseEntity<PurchaseResponseDto> createRequest(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        // 서비스 메서드 호출
        PurchaseRequest savedRequest = purchaseService.createPurchaseRequest(purchaseRequestDto);

        // PurchaseResponseDto로 변환
        PurchaseResponseDto responseDto = new PurchaseResponseDto(savedRequest);

        // ResponseEntity로 반환
        return ResponseEntity.ok(responseDto);
    }

    // 구매 요청 승인
    @PutMapping("/approve/{requestId}")
    public ResponseEntity<PurchaseResponseDto> approveRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.approvePurchaseRequest(requestId));
    }

    // 구매 요청 승인 확인
    @PutMapping("/approve/check/{requestId}")
    public ResponseEntity<PurchaseResponseDto> approveCheckRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.approveCheckPurchaseRequest(requestId));
    }

    /*
    승인 확인 다음 절차인 거래 신청(자신의 계좌 정보를 구매자에게 보여줌)
    권한은 판매자에게 있으므로 판매자가 거래 신청 -> 거래 수락(구매자) -> 제한 시간 15분 거래
    거래 = 판매자의 계좌 정보를 구매자에게 보여주고 바로 복사 붙여넣기 할 수 있음.
    거래가 완료되면 상품 상태를 판매 완료 변경, 판매 관리 목록에서 제외
    */
    
    // 결제 요청하기
    @PutMapping("/payment/request/{requestId}")
    public ResponseEntity<PurchaseResponseDto> paymentRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.paymentRequest(requestId));
    }

    
    // 거래 요청 수락 (서비스에 제한 시간 타이머 ON)
    @PutMapping("/payment/request/check/{requestId}")
    public ResponseEntity<PurchaseResponseDto> paymentCheckRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.paymentCheckRequest(requestId));
    }
    
    // 구매 요청 거절
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<PurchaseResponseDto> rejectRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.rejectPurchaseRequest(requestId));
    }

    // 구매 요청 거절 확인 -> 삭제(구매 요청 목록)
    @DeleteMapping("/reject/check/{requestId}")
    public ResponseEntity<PurchaseResponseDto> rejectCheckRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.rejectCheckPurchaseRequest(requestId));
    }

    // ## 구매 요청 특정 목록 삭제 ##
    

    // 결제 완료 요청
    @PutMapping("/payment/complete/{requestId}")
    public ResponseEntity<PurchaseResponseDto> paymentCompleteRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.paymentComplteRequest(requestId));
    }

    // 결제 완료 확인
    @PutMapping("/payment/complete/check/{requestId}")
    public ResponseEntity<PurchaseResponseDto> paymentCompleteCheckRequest(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(purchaseService.paymentCompleteCheckRequest(requestId));
    }
    //-------------------------------- 조회 목록 메소드 ------------------------------------

    // 모든 구매 요청 조회 (테스트용 또는 관리자용)
    @GetMapping("/all")
    public ResponseEntity<List<PurchaseResponseDto>> getAllRequests() {
        return ResponseEntity.ok(purchaseService.getAllPurchaseRequests());
    }

    // 특정 구매자의 구매 요청 조회
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<PurchaseResponseDto>> getRequestsByBuyer(@PathVariable("buyerId") Long buyerId) {
        List<PurchaseResponseDto> requests = purchaseService.getRequestsByBuyer(buyerId);
        return ResponseEntity.ok(requests);
    }
    

    // 특정 판매자가 받은 구매 요청 조회
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<PurchaseResponseDto>> getRequestsBySeller(@PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(purchaseService.getRequestsBySeller(sellerId));
    }

    // 계좌 확인 (판매자 계좌 조회)
    @GetMapping("/account/check/{sellerId}")
    public ResponseEntity<AccountResponseDto> getAccountBySeller(@PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(purchaseService.getAccountBySeller(sellerId));
    }



}