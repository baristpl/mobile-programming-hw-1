package com.homework.library;

public class Book {
    private long id;
    private String authorName;
    private String name;
    private String coverImageUrl;
    private int coverImageResId;
    private String description;

    public Book(long id, String authorName, String name, String coverImageUrl, int coverImageResId, String description) {
        this.id = id;
        this.authorName = authorName;
        this.name = name;
        this.coverImageUrl = coverImageUrl;
        this.coverImageResId = coverImageResId;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getCoverImageResId() {
        return coverImageResId;
    }

    public void setCoverImageResId(int coverImageResId) {
        this.coverImageResId = coverImageResId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}