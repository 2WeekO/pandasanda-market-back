package com.website.sharestore.Controller;

import com.website.sharestore.Dto.Request.PurchaseRequestDto;
import com.website.sharestore.Dto.Response.PurchaseResponseDto;
import com.website.sharestore.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 구매 요청 관리 컨트롤러
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    // 구매 요청 생성
    @PostMapping("/api/purchase/create")
    public ResponseEntity<PurchaseResponseDto> createRequest(@RequestBody PurchaseRequestDto requestDto) {
        // 필요한 로직을 수행하고, responseDto를 생성합니다.
        PurchaseResponseDto responseDto = new PurchaseResponseDto();
        // requestDto의 데이터를 기반으로 responseDto를 설정합니다.

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
