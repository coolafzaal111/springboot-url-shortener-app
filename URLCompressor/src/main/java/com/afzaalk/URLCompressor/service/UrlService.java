package com.afzaalk.URLCompressor.service;

import com.afzaalk.URLCompressor.model.request.Url;
import com.afzaalk.URLCompressor.model.request.UrlDTO;

public interface UrlService {
	
	public Url generateShortUrl(UrlDTO urlDTO);
	public Url persistShortLink(Url url);
	public Url getEncodedUrl(String url);
	public void deleteShortUrl(Url url);

}
