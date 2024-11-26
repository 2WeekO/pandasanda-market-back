package com.website.sharestore.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.sharestore.Entity.Product;
import com.website.sharestore.Entity.User;
import com.website.sharestore.Entity.Wish;
import com.website.sharestore.Repository.ProductRepository;
import com.website.sharestore.Repository.UserRepository;
import com.website.sharestore.Repository.WishRepository;

@Service
public class WishService {

  @Autowired
  private WishRepository wishRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;
  
  // ============================================================================================

    // -------------------------------------------------------------------
    @Transactional
    public void addWish(Long userKey, Long itemKey) {
      
        // 사용자와 상품 조회
        
        User user = userRepository.findById(userKey)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        Product product = productRepository.findById(itemKey)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
        
        // 찜 여부 상태 확인
        if (wishRepository.existsByUserAndProduct(user, product)) {
            throw new RuntimeException("이미 찜한 상품입니다.");
        }

        
        Wish wish = new Wish();
        wish.setUser(user);
        wish.setProduct(product);
        wish.setCreatedAt(LocalDateTime.now());
        wish.setWishDate(LocalDateTime.now());

        wishRepository.save(wish);
    }
    // -------------------------------------------------------------------
    // 찜 취소
    @Transactional
    public void removeWish(Long userKey, Long itemKey) {
        // 사용자와 상품을 찾음
        User user = userRepository.findById(userKey)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        Product product = productRepository.findById(itemKey)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
        
        Wish wish = wishRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("찜한 상품이 없습니다."));
        
        wishRepository.delete(wish);
    }
    // -------------------------------------------------------------------
    // 찜 상태 확인
    public boolean isWished(Long userKey, Long itemKey) {
        User user = userRepository.findById(userKey)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        Product product = productRepository.findById(itemKey)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
        
        return wishRepository.existsByUserAndProduct(user, product);
    }
    // -------------------------------------------------------------------
    // 특정 상품의 찜 개수 조회
    public long getWishCount(Long itemKey) {
        Product product = productRepository.findById(itemKey)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
        
        return wishRepository.countByProduct(product);
    }


}
