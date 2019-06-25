/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author nbnb
 */
public class InstanceApps {

public static Twitter getInstanceApp(){
ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("MhJP3Sou9WRV2kK3XMevYwO0o")
          .setOAuthConsumerSecret("Vn1TEX2hduJwmKGTSyML2VJDGjJPgVPU4XR17gtQDC6QhzimCO")
          .setOAuthAccessToken("970535278879195136-JnCyUYTEUme2S6vqWduC39ZsQLn0dsQ")
          .setOAuthAccessTokenSecret("TEQiVvbratxGnpIrLYaS78WZ1yKGSS081bN8dzhUDc2tY")
          .setJSONStoreEnabled(true);     
        //TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
        //TwitterStream twitterStream = tf.getInstance();
  //      twitterStream.addListener(listener);
       Twitter twitter = TwitterFactory.getSingleton();
		return twitter;

		}    
}
