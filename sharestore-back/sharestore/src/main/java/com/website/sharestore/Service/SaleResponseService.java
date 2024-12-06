package com.website.sharestore.Service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.sharestore.Entity.ResponseStatus;
import com.website.sharestore.Entity.SaleResponse;
import com.website.sharestore.Repository.SaleResponseRepository;


@Service
public class SaleResponseService {

    private final SaleResponseRepository saleResponseRepository;

    @Autowired
    public SaleResponseService(SaleResponseRepository saleResponseRepository) {
        this.saleResponseRepository = saleResponseRepository;
    }

    public SaleResponse createResponse(Long sellerId, Long purchaseId, ResponseStatus status) {
        SaleResponse response = new SaleResponse();
        response.setSellerId(sellerId);
        response.setPurchaseId(purchaseId);
        response.setStatus(status);
        response.setUpdatedAt(LocalDateTime.now());
        return saleResponseRepository.save(response);
    }

    public List<SaleResponse> getResponsesBySeller(Long sellerId) {
        return saleResponseRepository.findBySellerId(sellerId);
    }
}