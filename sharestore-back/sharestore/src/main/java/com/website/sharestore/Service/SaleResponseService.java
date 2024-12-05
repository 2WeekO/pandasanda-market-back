package com.website.sharestore.Service;



import com.website.sharestore.Entity.SaleResponse;
import com.website.sharestore.Repository.SaleResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleResponseService {
    private final SaleResponseRepository saleResponseRepository;

    public SaleResponse createResponse(UUID sellerId, UUID purchaseId, ResponseStatus status) {
        SaleResponse response = new SaleResponse();
        response.setSellerId(sellerId);
        response.setPurchaseId(purchaseId);
        response.setStatus(status);
        return saleResponseRepository.save(response);
    }

    public List<SaleResponse> getResponsesBySeller(UUID sellerId) {
        return saleResponseRepository.findBySellerId(sellerId);
    }

    public void updateResponseStatus(UUID responseId, ResponseStatus status) {
        SaleResponse response = saleResponseRepository.findById(responseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Response ID"));
        response.setStatus(status);
        saleResponseRepository.save(response);
    }
}
