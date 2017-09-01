package com.hsbc.entites;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mkilar on 29.08.2017.
 */
@XmlRootElement(name = "tweet")
public class Tweet implements Serializable {

    private String message;
    private Calendar creationTime;
    private String author;

    public Tweet(String author, String message, Calendar creationTime) {
        this.author = author;
        this.message = message;
        this.creationTime = creationTime;
    }

    public Tweet() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Calendar creationTime) {
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
