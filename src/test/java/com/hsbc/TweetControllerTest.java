package com.hsbc;

import com.hsbc.com.hsbc.entites.Tweet;
import com.hsbc.exception.TweeterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.doesNotHave;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by mkilar on 01.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {

    @InjectMocks
    private TweetController tweetController = new TweetController();

    @Mock
    private Map<String, List<Tweet>> tweets;

    @Mock
    private Map<String, List<String>> followups;

    @Rule
    public final ExpectedException exception = ExpectedException.none();



    @Test
    public void shouldPostProperly() throws Exception {
        tweetController.postTweet(TweetBuilder.getInstance()
                .withMessage("msg").withAuthor("me").build());


    }



    @Test
    public void getMyWall() throws Exception {

    }

    @Test
    public void shouldNotSingUpForNonExistetUser() throws Exception {

        exception.expect(TweeterException.class);
        exception.expectMessage("User other doesn't exist");

        tweetController.signUp("me","other");

    }

    @Test
    public void properSignIn() throws Exception {

        //given
        when(followups.get(eq("other"))).thenReturn(new ArrayList());
        //when
        String result = tweetController.signUp("me","other");
        //then
        assertThat(result).isEqualTo("other");
        verify(followups).put(eq("me"),anyList());
    }

}