package com.hsbc;

import com.hsbc.entites.Tweet;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mkilar on 01.09.2017.
 */
public class TweetBuilder {

    private String message;
    private String author;
    private Calendar creationTime;

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

    public TweetBuilder fromMap(Map map) {
        this.message = (String) map.get("message");
        this.author = (String) map.get("author");
        this.creationTime = null;
        Long timestamp = (Long) map.get("creationTime");

        if (timestamp != null) {
            creationTime = Calendar.getInstance();
            creationTime.setTimeInMillis(timestamp);
        }
        return this;
    }

    public Tweet build(){
        return new Tweet(author, message, creationTime);
    }


    public static List<Tweet> buildTweetsOfUser(String user) {
        return Stream
                .generate(() -> TweetBuilder.getInstance().randomTweet().withAuthor(user).build())
                .limit(10).collect(Collectors.toList());
    }
}
