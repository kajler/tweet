package com.hsbc;

import com.hsbc.entites.Tweet;
import com.hsbc.exception.TweeterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by mkilar on 01.09.2017.
 */
public class TweetControllerNoMocksTest {

    private TweeterController tweetController = new TweeterController();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotBeAbleToPostNullTweet() throws Exception {
        exception.expect(TweeterException.class);
        exception.expectMessage("Tweet can't be null");
        tweetController.postTweet(null);
    }

    @Test
    public void shouldNotBeAbleToPostWithoutAuthor() throws Exception {
        exception.expect(TweeterException.class);
        exception.expectMessage("Author can't be empty");
        tweetController.postTweet(TweetBuilder.getInstance().withMessage("msg").build());
    }

    @Test
    public void shouldNotBeAbleToPostWithoutMessage() throws Exception {
        exception.expect(TweeterException.class);
        exception.expectMessage("Message can't be empty");
        tweetController.postTweet(TweetBuilder.getInstance().randomTweet().withMessage("").build());
    }

    @Test
    public void shouldNotBeAbleToPostWithToLongMessage() throws Exception {
        exception.expect(TweeterException.class);
        exception.expectMessage("To long message");
        tweetController.postTweet(TweetBuilder.getInstance().randomTweet().withMessage("msgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmsgmdmdmdmdmdmdmdmdmdmdmdmmdmdmdmdmmmsg").build());
    }

    @Test
    public void shouldPostProperly() throws Exception {
        tweetController.postTweet(TweetBuilder.getInstance().withMessage("msg").withAuthor("me").build());
    }

    @Test
    public void tweetsShouldBeReturnedAndFilledWithCreationDate() throws Exception {

        //given
        Tweet tweet = TweetBuilder.getInstance().randomTweet().withAuthor("me").build();
        //when
        tweetController.postTweet(tweet);
        //then
        List<Tweet> result =tweetController.getTweetsOfUser("me");
        assertThat(result.contains(tweet)).isTrue();
        assertThat(result.get(0).getCreationTime()).isNotNull();
    }

    @Test
    public void getMyWall() throws Exception {

    }


}