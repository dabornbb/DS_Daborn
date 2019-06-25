/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;
/**
 *
 * @author nbnb
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.FileInputStream;
import java.io.InputStream;

public class GsonRead {
    /**
     * With the object model read the whole JSON file is loaded on memory and the
     * application gets the desired element.
     */
    public static void main(String[] args) throws JsonParseException, IOException {
  //      InputStream is = new FileInputStream("bb-xx.json");
         tryParse();	
	}
    public static void tryParse() throws IOException {
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createJsonParser(new File("dd.json"));
//		JsonToken token = parser.nextToken();	
        	// Try find at least one object or array.
                
		while (parser.nextToken() != JsonToken.END_OBJECT) {
                    if(parser.getCurrentName()!=null && parser.getCurrentName().equals("rows"))
                       while(parser.nextToken()!= JsonToken.END_ARRAY){
                           while(parser.nextToken()!=JsonToken.END_OBJECT){
                               if(parser.getCurrentName()!=null && parser.getCurrentName().equals("user")){
                                    while(parser.nextToken()!= JsonToken.END_OBJECT){
                                        if(parser.getCurrentName()!=null && parser.getCurrentName().equals("id"))
                                            System.out.println(parser.getText());
                                        if(parser.getCurrentName()!=null && parser.getCurrentName().equals("name"))
                                            System.out.println(parser.getText());
                                                }
                               }
                           } 
                            parser.nextToken();
                    }
                  
                    }
		// No content found
//		if (token == null) {
//			return;
//		}
//
//		boolean scanMore = false;
//
//		while (true) {
//			// If the first token is the start of obejct ->
//			// the response contains only one object (no array)
//			// do not try to get the first object from array.
//			try {
//				if (!JsonToken.START_OBJECT.equals(token) || scanMore) {
//					token = parser.nextToken();
//				}
//				if (!JsonToken.START_OBJECT.equals(token)) {
//					break;
//				}
//				if (token == null) {
//					break;
//				}
//
//			//	Object node = mapper.readValue(parser, mappedClass);
//				
//				//YOUR CODE TO HANDLE OBJECT
//				//...
//
//
//				scanMore = true;
//			} catch (JsonParseException e) {
//				System.out.print("error e");
//				break;
//			}
//		}
	}

}