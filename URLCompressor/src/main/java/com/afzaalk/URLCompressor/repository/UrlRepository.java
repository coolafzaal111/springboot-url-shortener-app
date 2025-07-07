package com.afzaalk.URLCompressor.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.afzaalk.URLCompressor.model.request.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
	
    public Url findByShortLink(String shortLink);
}
