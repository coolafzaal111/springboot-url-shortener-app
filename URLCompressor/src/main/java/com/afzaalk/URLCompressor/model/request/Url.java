package com.afzaalk.URLCompressor.model.request;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Lob;


@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Lob
	private String originalUrl;
	private String shortLink;
	private LocalDateTime creationDate;
	private LocalDateTime expirationDate;
	
	public Url(long id, String originalUrl, String shortLink, LocalDateTime creationDate,
			LocalDateTime expirationDate) {
		this.id = id;
		this.originalUrl = originalUrl;
		this.shortLink = shortLink;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
	}
	
	public Url() {
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "UrlDTO [id=" + id + ", originalUrl=" + originalUrl + ", shortLink=" + shortLink + ", creationDate="
				+ creationDate + ", expirationDate=" + expirationDate + "]";
	}

}
