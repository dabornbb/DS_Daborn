import org.codehaus.jackson.map.*;
import org.codehaus.jackson.*;
import java.io.File;
public class ParseJson {
  public static void main(String[] args) throws Exception {
    JsonFactory f = new MappingJsonFactory();
    JsonParser jp = f.createJsonParser(new File("bb-au.json"));
//    JsonToken current;
//    current = jp.nextToken();
//    if (current != JsonToken.START_OBJECT) {
//      System.out.println("Error: root should be object: quiting.");
//      return;
//    }
    jp.nextToken(); // JsonToken.START_OBJECT;
    while (jp.nextToken() != JsonToken.END_OBJECT) {
//    	System.out.println("bb");
      String fieldName = jp.getCurrentName();
      // move from field name to field value
//      current = jp.nextToken();
      if (fieldName.equals("created_at")) {
    	 jp.nextValue();
    	 jp.nextValue();
    	 jp.nextValue();
    	  System.out.println("txt: "+jp.getText());
    	 jp.nextValue(); //source
    	 jp.nextValue(); //truncated
    	 jp.nextValue(); //in_reply
    	 jp.nextValue(); //in_reply
    	 jp.nextValue(); //in_reply
    	 jp.nextValue(); //in_reply
    	 jp.nextValue(); //in_reply
//    	System.out.println(jp.nextValue());
        if (jp.nextValue() == JsonToken.START_OBJECT) {
        	jp.nextValue(); //skip {
        	jp.nextValue(); //skip id
        	jp.nextValue(); // skip id_str
        	jp.nextValue(); // skip name
        	jp.nextValue(); // skip screen name	
        	System.out.println("location : "+jp.getText());
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	System.out.println("Des : "+jp.getText());
        	jp.nextValue(); //skip 
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	jp.nextValue(); //skip
        	System.out.println("Created Date : "+jp.getText());
        	jp.nextValue();
        	System.out.println("utc_offset : "+jp.getText());
        	jp.nextValue();
        	System.out.println("time_zone : "+jp.getText());
        	jp.nextValue();
        	jp.nextValue();
        	System.out.println("lang : "+jp.getText());
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	System.out.println("city : "+jp.getText());
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
        	jp.nextValue();
      	System.out.println("timestamp: "+jp.getText());
      		jp.nextValue();
      		jp.nextValue();
//      		jp.nextValue();
      		
          }
//        System.out.println("here: "+jp.getText());
//        } else {
//          System.out.println("Error: records should be an array: skipping.");
          jp.nextValue();
        }
      jp.nextValue();
  
    }                
    jp.nextValue();
  }
  
}