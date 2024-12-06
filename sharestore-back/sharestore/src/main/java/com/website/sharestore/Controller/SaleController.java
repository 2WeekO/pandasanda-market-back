package com.website.sharestore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Entity.ResponseStatus;
import com.website.sharestore.Entity.SaleResponse;
import com.website.sharestore.Service.SaleResponseService;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    private final SaleResponseService saleResponseService;

    @Autowired
    public SaleController(SaleResponseService saleResponseService) {
        this.saleResponseService = saleResponseService;
    }

    @PostMapping("/create")
    public ResponseEntity<SaleResponse> createResponse(
            @RequestParam Long sellerId,
            @RequestParam Long purchaseId,
            @RequestParam ResponseStatus status) {
        SaleResponse response = saleResponseService.createResponse(sellerId, purchaseId, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<SaleResponse>> getResponsesBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(saleResponseService.getResponsesBySeller(sellerId));
    }
}