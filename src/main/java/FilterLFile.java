
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
public class FilterLFile {

    private static String pathToFile = "Big_GeoBox.json";
    private static String pathToSave = "tweet-big.arff";
    private static String textMsg = "reduced-text-big.txt";
    private static String timeZone = "reduced-timezone-big.txt";
    private static String header_city="header-city-big.txt";
    private static String header_country="header-country-big.txt";
    private static String  path= "reduced-text-big.txt";
    private boolean prettyPrint = true;
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
	private void createTweetText() throws StreamingQueryException {
    	 // Instantiates a client
//        Translate translate = TranslateOptions.getDefaultInstance().getService();
        // create spark configuration and spark context
        SparkConf conf = new SparkConf()
                .setAppName("Tweer")
                .set("spark.driver.allowMultipleContexts", "true")
                .setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Initialize Spark Session
        SparkSession ssc = SparkSession
                            .builder().appName("TweetFilter")	
                            .config("spark.sql.warehouse.dir", "file:///d:/tmp/spark-warehouse")
//                            .sql("set spark.sql.streaming.schemaInference=true")
                            .getOrCreate();
        // Using SQLContext read in the json for the tweets
        SQLContext sqlContext = ssc.sqlContext();
        Dataset<Row> tweets =ssc.readStream()
        		.format("socket")
        		.option("host","localhost")
        		.option("port",9999)
        		.load();
//        sqlContext.readStream().json(pathToFile).cache();
//        tweets.createOrReplaceTempView("tweetTb");
//        tweets.option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ");

////        if (prettyPrint) {
////            System.out.println("------Tweet table Schema------");
        StreamingQuery query = tweets.writeStream()
        		  .queryName("tweetTb")
        		  .outputMode("complete")
        		  .format("console")
        		  .start();
        query.awaitTermination();
       
//            tweets.printSchema();
      // }

        String query_msg,query_class;
        // Get only the relevant features
//        if (includeTZ) {
             query_msg = "SELECT (text,' ', user.location,' ', user.description,' ', timestamp_ms,' ', user.time_zone) as text, (place.full_name) as city,place.country, tweettb.lang FROM tweetTb where (place.name IS NOT NULL OR place.full_name IS NOT NULL OR place.country IS NOT NULL) AND tweettb.lang='en'";
//             query_class = "SELECT (place.name,',', place.full_name,',', place.country FROM tweetTable";
//             place.name, place.full_name, 
//             query = "SELECT 'text filtered ', text,' ',user.description AS text FROM tweetTable";
             
//        }
//        else {
//             query = "SELECT CONCAT('body, ' ', root.contributors)" +
//                    "AS text " +
//                    "FROM tweetTable " +
//                    "WHERE root.contributors is not null";
//        }
             
        Dataset<Row> results = sqlContext.sql(query_msg);

//        if (prettyPrint) {
//            System.out.println("Some text examples");
//            results.show();
//        }

        List<Row> tweetList = results.collectAsList();
       
        // Write the each tweet, one per line, to a file that can then be used to train the word2vec model
        try {
//            BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToSave),);
            BufferedWriter outputWriter = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(pathToSave), StandardCharsets.UTF_8));
            BufferedWriter outputWriter_city = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(header_city), StandardCharsets.UTF_8));
            BufferedWriter outputWriter_country = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(header_country), StandardCharsets.UTF_8));
            
            for (Row row : tweetList) {
                String toWrite = row.get(0).toString();
                String city = row.get(1).toString();
                String country=row.get(2).toString();
                String  lang = row.get(3).toString();	
//            	if(!lang.equals("en")) {
//                Translation translation =translate.translate(toWrite,TranslateOption.sourceLanguage("jp"),TranslateOption.targetLanguage("en"));
//                  
//                   toWrite= translation.getTranslatedText();
//            	}
                toWrite = toWrite.substring(1, toWrite.length() - 1);
                toWrite= toWrite.replace(";"," ")
                		.replaceAll("http.*?\\s", " ")
                		.replaceFirst("\\)"," ")
                		.replaceFirst("\\("," ")
                        .replace('-',' ')
                        .replace(',',' ')
                        .replace('.',' ')
                        .replaceAll("!", " ")
                        .replaceAll("\n", "")
                        .replaceAll("\\r", " ") 
                        .replaceAll("\\?"," ")
                        .replaceAll("/"," ")
                        .replaceAll("\\[","")
                        .replaceAll("\\]","")
                        .replaceAll("\"","")
                        .replaceAll("\\  "," ");
                  
                city= city.replace("["," ").replace("]"," ").replaceAll(","," ");
                country= country.replace("["," ").replace("]"," ");
                outputWriter.write('"' + toWrite +'"'+','+'"' + city +'"'+','+'"' + country +'"'+"\n");
//                for (int j=0;j<=i;j++) {
//                		if(header[j]==null) {
                city=removeDuplicates(city);
                country=removeDuplicates(country);
                city=city.replaceAll("\n", "");
                country=country.replaceAll("\n", "");
                
                outputWriter_city.write('"'+city+'"'+','+"\n");
                outputWriter_country.write('"'+country+'"'+','+"\n");
                
//                header[i++]=classes;
//                }
//                }
            }
            outputWriter.flush();
            outputWriter.close();
            outputWriter_city.flush();
            outputWriter_city.close();
            outputWriter_country.flush();
            outputWriter_country.close();
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
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
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
}
