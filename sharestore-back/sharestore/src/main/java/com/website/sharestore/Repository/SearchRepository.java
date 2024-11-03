package com.website.sharestore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.sharestore.Entity.Search;

public interface SearchRepository extends JpaRepository<Search, Long> {
}
