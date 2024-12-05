package com.website.sharestore.Service;


import com.website.sharestore.Entity.PurchaseRequest;
import com.website.sharestore.Repository.PurchaseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseRequestService {
    private final PurchaseRequestRepository purchaseRequestRepository;

    public PurchaseRequest createRequest(UUID buyerId, UUID productId) {
        PurchaseRequest request = new PurchaseRequest();
        request.setBuyerId(buyerId);
        request.setProductId(productId);
        return purchaseRequestRepository.save(request);
    }

    public List<PurchaseRequest> getRequestsByBuyer(UUID buyerId) {
        return purchaseRequestRepository.findByBuyerId(buyerId);
    }

    public List<PurchaseRequest> getRequestsByProduct(UUID productId) {
        return purchaseRequestRepository.findByProductId(productId);
    }

    public void updateRequestStatus(UUID requestId, RequestStatus status) {
        PurchaseRequest request = purchaseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Request ID"));
        request.setStatus(status);
        purchaseRequestRepository.save(request);
    }
}
