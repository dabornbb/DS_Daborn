import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import java.util.Arrays;
import java.util.Iterator;

public class sqlStreamQ {
public static void main(String a[]) {
	SparkSession spark = SparkSession
			  .builder()
			  .appName("JavaStructuredNetworkWordCount")
			  .getOrCreate();
	
}
}
