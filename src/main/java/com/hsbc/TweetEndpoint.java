package com.hsbc;

import com.hsbc.com.hsbc.entites.Tweet;
import com.hsbc.exception.TweeterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by mkilar on 30.08.2017.
 */
@RestController
public class TweetEndpoint {

    private static Logger logger = Logger.getLogger(TweetEndpoint.class.getName());

    @Autowired
    private TweetController tweetController;

    /**
     * Post a tweet. If user doesn't exists than it is created
     * @param tweet tweet to be persisted
     * @return persisted tweet with updated date
     */
    @RequestMapping(method=POST)
    public Tweet postTweet(Tweet tweet){
        try {
            tweetController.postTweet(tweet);
            return tweet;
        } catch (TweeterException e) {
            logger.log(Level.SEVERE,"Can't post tweet",e);
            return null;
        }
    }

    /**
     * Returns all tweets of particular user
     * @param user of which tweets must be returned
     * @return list of tweets
     */
    @RequestMapping(method=GET)
    public List<Tweet> getTweetsOfUser(String user){
        return tweetController.getTweetsOfUser(user);
    }

    /**
     * Returns tweets of all followed users
     * @param user login of user which is following other users
     * @return list of all tweets of follwed user from the newest to the oldest
     */
    @RequestMapping(method=GET)
    public List<Tweet> getMyWall(String user){
        return tweetController.getMyWall(user);
    }


    /**
     * Singup of tweets
     * @param followingUser user which is going to follow ( can be new user )
     * @param followedUser user which is going to be followed ( user must exits before)
     * @return list of tweets
     */
    @RequestMapping(method=PUT)
    public String signUp(String followingUser, String followedUser){
        try {
            return tweetController.signUp(followingUser,followedUser);
        } catch (TweeterException e) {
            logger.log(Level.SEVERE,"Can't post tweet",e);
            return null;
        }
    }


}
