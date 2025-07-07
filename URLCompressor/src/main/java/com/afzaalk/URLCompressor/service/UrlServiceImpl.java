package com.afzaalk.URLCompressor.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afzaalk.URLCompressor.model.request.Url;
import com.afzaalk.URLCompressor.model.request.UrlDTO;
import com.afzaalk.URLCompressor.repository.UrlRepository;

import com.google.common.hash.Hashing;

@Service
public class UrlServiceImpl implements UrlService {
	
	@Autowired
	UrlRepository urlRepository;

	@Override
	public Url generateShortUrl(UrlDTO urlDTO) {
		if(StringUtils.isNotEmpty(urlDTO.getUrl())) {
			String encodedUrl = encodeUrl(urlDTO.getUrl());
			Url urlToPersist = new Url();
			urlToPersist.setCreationDate(LocalDateTime.now());
			urlToPersist.setOriginalUrl(urlDTO.getUrl());
			urlToPersist.setShortLink(encodedUrl);
			urlToPersist.setExpirationDate(getExpirationDate(urlDTO.getExpirationDate(), urlToPersist.getCreationDate()));
			Url urlToRet = persistShortLink(urlToPersist);
			
			if(urlToRet != null) {
				return urlToRet;
			}
		}
		return null;
	}

	// Method for Encoding into short url
	private String encodeUrl(String url) {
		String encodeUrl = "";
		LocalDateTime time = LocalDateTime.now();
		
		// Using murmur algo to shortening the url, we can use any other alternate like adler too.
		encodeUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		return encodeUrl;
	}
	
	// Calculating Expiration Date
	private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
		if(StringUtils.isBlank(expirationDate)) {
			return creationDate.plusSeconds(120);
		}
		LocalDateTime expirationDateToReturn = LocalDateTime.parse(expirationDate);
		return expirationDateToReturn;
	}

	@Override
	public Url persistShortLink(Url url) {
		Url urlToRet = urlRepository.save(url);
		return urlToRet;
	}

	@Override
	public Url getEncodedUrl(String url) {
		Url urlToRet = urlRepository.findByShortLink(url);
		return urlToRet;
	}

	@Override
	public void deleteShortUrl(Url url) {
		urlRepository.delete(url);
		
	}

}