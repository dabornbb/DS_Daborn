import org.apache.spark.SparkConf;
import org.apache.spark.sql.SQLContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.spark.api.java.JavaSparkContext;
// google translate
//Imports the Google Cloud client library
//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.Translate.TranslateOption;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
/**
 * Use this class to filter through GNIP tweets and extract only the tweet text
 * Use Spark for this for quicker processing. Best to do it by reading tweets from S3 bucket
 */
public class TweetFilter {

    private static String pathToFile = "bb-au.json";
//    private static String pathToSave = "bb//raw-bb-au.arff";
    private static String city = "bb//raw-city-bb.arff";
//    private static String timeZone = "reduced-timezone-big.txt";
    private static String header_city="bb//header-city.txt";
//    private static String header_country="header-country.txt";
   // Whether or not to include time zone information
    private boolean includeTZ = false;
    public static void main(String[] args) throws IOException, StreamingQueryException {
        TweetFilter tf = new TweetFilter();
        tf.createTweetText();
    }
    /**
     * Create the text file with one tweet per file. This way it's easy to train the word2vec model
     * @throws StreamingQueryException 
     */
	void createTweetText() throws StreamingQueryException {
    	// Instantiates a client
		// Translate translate = TranslateOptions.getDefaultInstance().getService();
        // create spark configuration and spark context
        SparkConf conf = new SparkConf()
                .setAppName("TweetFilter")
                .set("spark.driver.allowMultipleContexts", "true")
                .setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // Initialize Spark Session
        SparkSession ssc = SparkSession
                            .builder().appName("TweetFilter")	
                            .config("spark.sql.warehouse.dir", "file:///d:/tmp/spark-warehouse")
                           .getOrCreate();
        // Using SQLContext read in the json for the tweets
        SQLContext sqlContext = ssc.sqlContext();
        Dataset<Row> tweets =ssc.read().json(pathToFile).cache();
        
        tweets.createOrReplaceTempView("tweetTb");
        tweets.printSchema();
//         StreamingQuery query = tweets.writeStream()
//        		  .queryName("tweetTb")
//        		  .outputMode("complete")
//        		  .format("console")
//        		  .start();
//         			query.awaitTermination(1000000000);
//         			query.name();
//       
           
      // }

        // Get only the relevant features
//        if (includeTZ) {
        String query_msg1;
        query_msg1 = "SELECT text as msg,"
        		+ " user.location as loc,"
        		+ " user.description as dsc,"
        		+ " timestamp_ms as ttp,"
        		+ " user.time_zone as tiz,"
        		+ "place.full_name as city,"
        		+ "place.country as country, "
        		+ "user.lang as lan FROM tweetTb "
        		+ "where (place.name IS NOT NULL OR place.full_name IS NOT NULL OR place.country IS NOT NULL) AND user.lang='en'";
        Dataset<Row> results = sqlContext.sql(query_msg1);
        List<Row> tweetList = results.collectAsList();     
        // Write the each tweet, one per line, to a file that can then be used to train the word2vec model
        try {
//            BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToSave),);
//            BufferedWriter outputWriterbycountry = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(pathToSave), StandardCharsets.UTF_8));
            BufferedWriter outputWriterbycity = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(city), StandardCharsets.UTF_8));
            BufferedWriter outputWriter_city = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(header_city), StandardCharsets.UTF_8));
//            BufferedWriter outputWriter_country = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(header_country), StandardCharsets.UTF_8));
//            
            for (Row row : tweetList) {
                String msg = row.getString(0); // msg
                String user_loc =row.getString(1);// location
                String user_dsc = row.getString(2);// user  description
                String ttp=row.getString(3);// time stamp
                String tiz = row.getString(4);// time zone
                String city = row.getString(5); //location
                String country=row.getString(6); // country
                String  lang = row.getString(7);// language
                
//            	if(!lang.equals("en")) {
//                Translation translation =translate.translate(toWrite,TranslateOption.sourceLanguage("jp"),TranslateOption.targetLanguage("en"));
//                  
//                   toWrite= translation.getTranslatedText();
//            	}
                
                msg=Formater(msg);
                user_dsc=Formater(user_dsc);    
                String str[]= city.split(",");
                city=str[0];
                if(user_loc!=null) {
                String strl[]=user_loc.split(",");
                user_loc=strl[0];   
                }
//                outputWriterbycountry.write('"' + msg +'"'+"," +'"'+ user_loc +'"'+','+'"' + user_dsc +'"'+','+'"' + ttp +'"'+','+'"' + tiz +'"'+','+'"' + Formater(country) +'"'+"\n");
                outputWriterbycity.write('"' + msg +'"'+"," +'"'+ Formater(user_loc) +'"'+','+'"' + user_dsc +'"'+','+'"' + ttp +'"'+','+'"' + tiz +'"'+','+'"' + Formater(city) +'"'+"\n");
                outputWriter_city.write('"'+Formater(city)+'"'+',');
//                outputWriter_country.write('"'+Formater(country)+'"'+','+"\n");
                
//                header[i++]=classes;
//                }
//                }
            }
//            outputWriterbycountry.flush();
//            outputWriterbycountry.close();
            outputWriterbycity.flush();
            outputWriterbycity.close();
            outputWriter_city.flush();
            outputWriter_city.close();
//            outputWriter_country.flush();
//            outputWriter_country.close();
            System.out.println("done");
        } catch (IOException exception) {
            System.out.println("Bad path to save tweets to");
        }

        // This might be the best method of doing things, but unfortunately, it breaks files into multiple parts
//         System.out.println("Saving results to json file");
//         results.write().mode("append").json(pathToSave);
    }
	private String removeUrl(String commentstr)
	{
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&])";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
	public String removeDuplicates(String input){
	    String result = "";
	    for (int i = 0; i < input.length(); i++) {
	        if(!result.contains(String.valueOf(input.charAt(i)))) {
	            result += String.valueOf(input.charAt(i));
	        }
	    }
	    return result;
	}
	public String Formater(String msg) {

		if(msg!=null)
				msg = msg
        		.replace(";"," ")
        		.replaceFirst("\\)"," ")
        		.replaceFirst("\\("," ")
                .replace("-"," ")
                .replaceAll("“"," ")
                .replaceAll("'"," ")
                .replaceAll("!", " ")
                .replaceAll("\n", "")
                .replaceAll("\\r", " ") 
                .replaceAll("\\?"," ")
                .replaceAll("/"," ")
               .replaceAll("\\/"," ")
                .replaceFirst("@"," ")
                .replaceAll("¯"," ")
                .replaceAll("\\["," ")
                .replaceAll("\\]"," ")
                .replaceFirst("#","")
                .replaceAll("\"","")
                .replaceAll("  "," ");
        return msg;
	}
}
