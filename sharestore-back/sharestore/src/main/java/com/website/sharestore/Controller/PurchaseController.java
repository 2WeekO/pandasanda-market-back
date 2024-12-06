package com.website.sharestore.Controller;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Dto.Response.PurchaseResponseDto;
import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



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
    public ResponseEntity<PurchaseResponseDto> approveRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(purchaseService.approvePurchaseRequest(requestId));
    }

    // 구매 요청 거절
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<PurchaseResponseDto> rejectRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(purchaseService.rejectPurchaseRequest(requestId));
    }

    // 모든 구매 요청 조회 (테스트용 또는 관리자용)
    @GetMapping("/all")
    public ResponseEntity<List<PurchaseResponseDto>> getAllRequests() {
        return ResponseEntity.ok(purchaseService.getAllPurchaseRequests());
    }
}
