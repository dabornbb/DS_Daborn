/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;
public class TwitterStreamingAPI {
    public static void main(String[] args){
        
//bounding box for vancouver (longitute and latitude)
//    double[][] boundingBox= {
//        {
//          11.58277, 113.50516
//        }
//        ,{
//            10.00737, 153.10468
//        }
//      };

        StatusListener listener;
        listener = new StatusListener(){
            long longLastitute=0;
            long lengthStitute=0;
            @Override
            public void onStatus(Status status) {
             //   if(status.getGeoLocation()!= null) {
                    longLastitute =(long) status.getGeoLocation().getLatitude();
                    lengthStitute =(long) status.getGeoLocation().getLongitude(); 
                    try {

	FileWriter fw = new FileWriter("bb-au.json", true);
	BufferedWriter bw = new BufferedWriter(fw);
        
        String rawJSON = DataObjectFactory.getRawJSON(status);
 //       FileOutputStream fos = new FileOutputStream("bb.json");
        OutputStreamWriter osw = null;
        try {        
 //           osw = new OutputStreamWriter(fos, "UTF-8");
      //      bw = new BufferedWriter(osw);
          //  bw.write(rawJSON);
            bw.newLine();
            bw.append(rawJSON.toString());
            bw.flush();
            bw.close();
            
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ignore) {
                }
            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException ignore) {
//                }
//            }
        }
System.out.println(status.getId());
//                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }   
    //            }
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            @Override
            public void onException(Exception ex) {
            }
            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub
                
            }            
        };
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("MhJP3Sou9WRV2kK3XMevYwO0o")
          .setOAuthConsumerSecret("Vn1TEX2hduJwmKGTSyML2VJDGjJPgVPU4XR17gtQDC6QhzimCO")
          .setOAuthAccessToken("970535278879195136-JnCyUYTEUme2S6vqWduC39ZsQLn0dsQ")
          .setOAuthAccessTokenSecret("TEQiVvbratxGnpIrLYaS78WZ1yKGSS081bN8dzhUDc2tY")
          .setJSONStoreEnabled(true);
          double [][]location ={{-9.187026,159.287222},{-54.833766,110.951034}};
        //   filter tweets so that we only get ones within our specified area
        FilterQuery filter = new FilterQuery();
       
        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
        TwitterStream twitterStream = tf.getInstance(); 
        twitterStream.addListener(listener);
         filter.locations(location);
    //    FilterQuery filtre = new FilterQuery();
     //   String[] keywordsArray = { "cricket", "footbal" }; //filter based on your choice of keywords
     //   filtre.track(keywordsArray);
// get all location
        twitterStream.sample();

//        twitterStream.filter(filter);
    }     
//private static void storeJSON(String rawJSON) throws IOException {
//        FileOutputStream fos = new FileOutputStream("bb.json");
//        OutputStreamWriter osw = null;
//        BufferedWriter bw = null;
//        try {        
//            osw = new OutputStreamWriter(fos, "UTF-8");
//            bw = new BufferedWriter(osw);
//          //  bw.write(rawJSON);
//            bw.append(rawJSON);
//            bw.flush();
//            
//        } finally {
//            if (bw != null) {
//                try {
//                    bw.close();
//                } catch (IOException ignore) {
//                }
//            }
//            if (osw != null) {
//                try {
//                    osw.close();
//                } catch (IOException ignore) {
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException ignore) {
//                }
//            }
//        }
//}

}