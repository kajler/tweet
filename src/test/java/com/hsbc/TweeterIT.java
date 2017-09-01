package com.hsbc;

import com.hsbc.entites.Tweet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.hsbc.TweetBuilder.buildTweetsOfUser;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mkilar on 01.09.2017.
 */


public class TweeterIT {

    RestTemplate restTemplate = new RestTemplate();

    @BeforeClass
    public static void init() {
        SpringApplication.run(Starter.class, new String[0]);
    }


    @Test
    public void integrationPost() {

        Tweet tweet = TweetBuilder.getInstance().withAuthor("mkilar").withMessage("test").build();

        restTemplate.postForObject("http://localhost:8080/tweet", tweet, Tweet.class);

        List<Map> list = restTemplate.getForObject("http://localhost:8080/tweets/mkilar", List.class);

        assertThat(deserialize(list.get(0)).getMessage()).isEqualTo(tweet.getMessage());
        assertThat(deserialize(list.get(0)).getAuthor()).isEqualTo(tweet.getAuthor());


    }


    @Test
    public void shouldBeAbleToFetchFollowingUsersTweets() {


        String user1 = "user1";
        String user2 = "user2";

        //create tweets and post them in parallel
        buildTweetsOfUser(user1).parallelStream()
                .forEach(item -> restTemplate.postForObject("http://localhost:8080/tweet", item, Tweet.class));
        buildTweetsOfUser(user2).parallelStream()
                .forEach(item -> restTemplate.postForObject("http://localhost:8080/tweet", item, Tweet.class));


        restTemplate.put("http://localhost:8080/signup?following=me&followed=user1", null);
        restTemplate.put("http://localhost:8080/signup?following=me&followed=user2", null);

        List<Map> list = restTemplate.getForObject("http://localhost:8080/wall/me", List.class);

        assertThat(list.size()).isEqualTo(20);

        for (int i = 1; i < list.size(); i++) {
            assertThat(deserialize(list.get(i - 1)).getCreationTime())
                    .isLessThanOrEqualTo(deserialize(list.get(i)).getCreationTime());
        }
    }

    private Tweet deserialize(Map map) {
        return TweetBuilder.getInstance().fromMap(map).build();

    }
}
