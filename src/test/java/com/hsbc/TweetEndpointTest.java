package com.hsbc;

import com.hsbc.entites.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.hsbc.TweetBuilder.buildTweetsOfUser;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mkilar on 30.08.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TweetEndpoint.class, TweeterController.class})
public class TweetEndpointTest {

    @Autowired
    private TweetEndpoint tweetEndpoint;

    private Tweet tweet;

    @Before
    public void init() {

        tweet = TweetBuilder.getInstance().randomTweet().build();
    }

    @Test
    public void postTweet() throws Exception {

        Tweet response = tweetEndpoint.postTweet(tweet);
        assertThat(response).isEqualTo(tweet);
    }


    @Test
    public void getTweetsOfUser() throws Exception {

        String user = "myself";

        List<Tweet> sourceStream = buildTweetsOfUser(user);

        sourceStream.forEach(item -> tweetEndpoint.postTweet(item));
        List<Tweet> resultList = tweetEndpoint.getTweetsOfUser(user);

        sourceStream.forEach(item -> assertThat(resultList.contains(item)).isTrue());

    }



    @Test
    public void getMyWall() throws Exception {

        String user1 = "user1";
        String user2 = "user2";

        //create tweets and post them in parallel
        buildTweetsOfUser(user1).parallelStream().forEach(item -> tweetEndpoint.postTweet(item));
        buildTweetsOfUser(user2).parallelStream().forEach(item -> tweetEndpoint.postTweet(item));

        tweetEndpoint.signUp("me",user1);
        tweetEndpoint.signUp("me",user2);

        List<Tweet> tweetList = tweetEndpoint.getMyWall("me");

         assertThat(tweetList.size()).isEqualTo(20);

        for(int i=1;i<tweetList.size();i++){
            assertThat(tweetList.get(i - 1).getCreationTime()).isLessThanOrEqualTo(tweetList.get(i).getCreationTime());
        }

    }


}