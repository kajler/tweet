package com.hsbc;

import com.hsbc.entites.Tweet;
import com.hsbc.exception.TweeterException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

/**
 * Created by mkilar on 29.08.2017.
 */
@Component
public class TweeterController {

    private Map<String, List<Tweet>> tweets = new ConcurrentHashMap<>();
    private Map<String, List<String>> followups = new ConcurrentHashMap<>();
    private static int FIRST_POSITION = 0;

    public void postTweet(Tweet tweet) throws TweeterException {
        validate(tweet);
        persistTweet(tweet);
    }

    private synchronized void persistTweet(Tweet tweet) throws TweeterException {
        String  author = tweet.getAuthor();

        List<Tweet> tweetList= tweets.get(author);
        if (tweetList == null) {
            tweetList = new LinkedList<>();
            tweets.put(author, tweetList);
            followups.put(author, new ArrayList<>());
        }
        tweet.setCreationTime(Calendar.getInstance());
        tweetList.add(FIRST_POSITION, tweet);

    }

    public List<Tweet> getTweetsOfUser(String user) {
        return tweets.get(user);
    }

    public List<Tweet> getMyWall(String user) {
        return tweets.values().stream()
                .flatMap(List::stream)
                .filter(item -> followups.get(user) != null)
                .filter(item -> followups.get(user).contains(item.getAuthor()))
                .sorted(Comparator.comparing(Tweet::getCreationTime))
                .collect(toList());
    }

    public String signUp(String followingUser, String followedUser) throws TweeterException {

        List<String> followers = followups.get(followedUser);
        if (followers == null) {
            throw new TweeterException("User " + followedUser + " doesn't exist");
        }

        List<String> meFollowing = followups.get(followingUser);
        if (meFollowing == null) {
            meFollowing = new ArrayList<>();
            followups.put(followingUser, meFollowing);
        }
        meFollowing.add(followedUser);


        return followedUser;
    }

    private void validate(Tweet tweet) throws TweeterException {

        if (tweet == null){
            throw new TweeterException("Tweet can't be null");
        }

        if (StringUtils.isEmpty(tweet.getMessage())){
            throw new TweeterException("Message can't be empty");
        }

        if (StringUtils.isEmpty(tweet.getAuthor())){
            throw new TweeterException("Author can't be empty");
        }

        if (tweet.getMessage().length() > 140) {
            throw new TweeterException("To long message");
        }

        if (tweet.getAuthor() == null){
            throw new TweeterException("Author can't be empty");
        }
    }

}
