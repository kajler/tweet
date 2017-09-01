package com.hsbc.entites;

import java.time.LocalDateTime;

/**
 * Created by mkilar on 29.08.2017.
 */
public class Tweet {

    private String message;
    private LocalDateTime creationTime;
    private String author;

    public Tweet(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public Tweet() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "message='" + message + '\'' +
                ", creationTime=" + creationTime +
                ", author='" + author + '\'' +
                '}';
    }
}
