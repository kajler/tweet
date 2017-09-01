package com.hsbc;

import com.hsbc.com.hsbc.entites.Tweet;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by mkilar on 01.09.2017.
 */
public class TweetBuilder {

    private String message;
    private String author;

    public static TweetBuilder getInstance(){
        return new TweetBuilder();
    }

    public TweetBuilder randomTweet(){

        message = UUID.randomUUID().toString();
        author = UUID.randomUUID().toString();

        return this;
    }

    public TweetBuilder withAuthor(String author){
        this.author = author;
        return this;
    }

    public TweetBuilder withMessage(String message){
        this.message = message;
        return this;
    }

    public Tweet build(){
        return new Tweet(author, message);
    }
}
