package com.website.sharestore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.sharestore.Service.WishService;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    // 찜 추가
    @PostMapping("/add")
    public ResponseEntity<Void> addWish(@RequestParam("userKey") Long userKey, @RequestParam("itemKey") Long itemKey) {
        wishService.addWish(userKey, itemKey);
        return ResponseEntity.ok().build();
    }

    // 찜 취소
    @PostMapping("/remove")
    public ResponseEntity<Void> removeWish(@RequestParam("userKey") Long userKey, @RequestParam("itemKey") Long itemKey) {
        wishService.removeWish(userKey, itemKey);
        return ResponseEntity.ok().build();
    }

    // 찜 상태 확인
    @GetMapping("/status")
    public ResponseEntity<Boolean> isWished(@RequestParam("userKey") Long userKey, @RequestParam("itemKey") Long itemKey) {
        boolean isWished = wishService.isWished(userKey, itemKey);
        return ResponseEntity.ok(isWished);
    }

    // 특정 상품의 찜 개수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> getWishCount(@RequestParam("itemKey") Long itemKey) {
        long count = wishService.getWishCount(itemKey);
        return ResponseEntity.ok(count);
    }
}
