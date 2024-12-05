package com.website.sharestore.Controller;

import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseRequestService purchaseRequestService;

    @PostMapping
    public ResponseEntity<PurchaseRequest> createRequest(
            @RequestParam UUID buyerId, @RequestParam UUID productId) {
        PurchaseRequest request = purchaseRequestService.createRequest(buyerId, productId);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<PurchaseRequest>> getRequestsByBuyer(@PathVariable UUID buyerId) {
        return ResponseEntity.ok(purchaseRequestService.getRequestsByBuyer(buyerId));
    }
}
