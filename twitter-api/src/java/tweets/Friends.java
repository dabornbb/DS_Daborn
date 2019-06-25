/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.util.List;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.User;
import tweets.InstanceApps;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 *
 * @author nbnb
 */
public class Friends {
    public static void main(String a[]) throws TwitterException{
    TwitterFactory factory = new TwitterFactory();
    Twitter twitter = factory.getInstance();
   String OAuthConsumerKey="uCaHM200E04OgS7u11O3nHbV5";
    String OAuthConsumerSecret="4CTDb12QE5ZRMoVKLvGGCq5WVdHfoe0wns2c0gmUIZTV93PNpN";
    String OAuthAccessToken="1106722957403906048-uAkSyICNEB85Vk73AQ3U0U0oRZsiza";
    String OAuthAccessTokenSecret="j0S3qXOqUEQCiyR2oXlvMGQPWjc1SAwZMVOBFXTSYVv0z";
    twitter.setOAuthConsumer(OAuthConsumerKey, OAuthConsumerSecret);
    AccessToken accessToken = new AccessToken(OAuthAccessToken, OAuthAccessTokenSecret);
    twitter.setOAuthAccessToken(accessToken);
    String twitterScreenName = "2828Cassaman";
  //  try {
    //    twitterScreenName = twitter.getScreenName();

    IDs followerIDs = twitter.getFollowersIDs(twitterScreenName, -1);
    long[] ids = followerIDs.getIDs();
    for (long id : ids) {
       twitter4j.User user = twitter.showUser(id);
       //here i am trying to fetch the followers of each id
       String userScreenName = user.getScreenName();
       System.out.println("Name: " + user.getScreenName());
       System.out.println("Location:" + user.getLocation());
       IDs followerIDsOfFollowers = twitter.getFollowersIDs(user.getScreenName(), -1);
       long[]fofIDs = followerIDsOfFollowers.getIDs();
       for(long subId : fofIDs) {
           twitter4j.User user1 = twitter.showUser(subId);
           System.out.println("Follower Master:" + userScreenName +" Follower of Follower Name: " + user1.getScreenName());
           System.out.println("Location:" + user1.getLocation());
       }
       
  //  }
}
    }
}
