package com.afzaalk.URLCompressor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afzaalk.URLCompressor.model.request.Url;
import com.afzaalk.URLCompressor.model.request.UrlDTO;
import com.afzaalk.URLCompressor.model.response.UrlErrorResponseDTO;
import com.afzaalk.URLCompressor.model.response.UrlResponseDTO;
import com.afzaalk.URLCompressor.service.UrlServiceImpl;

@RestController
@RequestMapping(value = "/api")
public class UrlController {
	
	@Autowired
	UrlServiceImpl urlServiceImpl;
	
	@PostMapping("/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody UrlDTO urlDTO) {
		
		Url urlToRet = urlServiceImpl.generateShortUrl(urlDTO);
		
		if(urlToRet != null) {
			UrlResponseDTO urlResponseDTO = new UrlResponseDTO();
			urlResponseDTO.setOriginalUrl(urlDTO.getUrl());
			urlResponseDTO.setShortLink(urlToRet.getShortLink());
			urlResponseDTO.setExpirationDate(urlToRet.getExpirationDate());
			
			return new ResponseEntity<UrlResponseDTO>(urlResponseDTO, HttpStatus.OK);
		}
		
		UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
		urlErrorResponseDTO.setStatus("404");
		urlErrorResponseDTO.setError("There was an error in processing your request. Please try again.");
		
		return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
		
	}

}
