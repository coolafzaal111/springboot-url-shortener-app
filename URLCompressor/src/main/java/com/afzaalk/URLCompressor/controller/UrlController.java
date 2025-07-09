package com.afzaalk.URLCompressor.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afzaalk.URLCompressor.model.request.Url;
import com.afzaalk.URLCompressor.model.request.UrlDTO;
import com.afzaalk.URLCompressor.model.response.UrlErrorResponseDTO;
import com.afzaalk.URLCompressor.model.response.UrlResponseDTO;
import com.afzaalk.URLCompressor.service.UrlServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api")
public class UrlController {
	
	@Autowired
	UrlServiceImpl urlServiceImpl;
	
	@PostMapping("/generateshorturl")
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
	
	
	@GetMapping("/redirecttooriginalurl/{shortLink}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
		
		if(StringUtils.isEmpty(shortLink)) {
			UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
			urlErrorResponseDTO.setStatus("400");
			urlErrorResponseDTO.setError("Invalid Url!!");
			return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
		}
		
		Url urlToRet = urlServiceImpl.getEncodedUrl(shortLink);
		if(urlToRet == null) {
			UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
			urlErrorResponseDTO.setStatus("400");
			urlErrorResponseDTO.setError("Url might expired or does not exist.");
			return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
		}
		
		if(urlToRet.getExpirationDate().isBefore(LocalDateTime.now())) {
			urlServiceImpl.deleteShortUrl(urlToRet);
			UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
			urlErrorResponseDTO.setStatus("200");
			urlErrorResponseDTO.setError("Url expired, Please generate a fresh one.");
			return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
		}
		
		response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
	}
	

}
