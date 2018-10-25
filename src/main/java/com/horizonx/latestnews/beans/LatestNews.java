package com.horizonx.latestnews.beans;

import com.opencsv.bean.CsvBindByName;

public class LatestNews {

	@CsvBindByName(column = "title")
    private String title;
	
	@CsvBindByName(column = "description")
    private String description;
	
	@CsvBindByName(column = "url")
    private String url;
	
	@CsvBindByName(column = "author")
    private String author;
	
	@CsvBindByName(column = "image")
    private String image;
	
	@CsvBindByName(column = "language")
    private String language;
	
	@CsvBindByName(column = "published")
    private String published;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	
	@Override
	public String toString() {
		return "LatestNews [title=" + title + ", description=" + description + ", url=" + url + ", author=" + author
				+ ", image=" + image + ", language=" + language + ", published=" + published + "]";
	}
	
}