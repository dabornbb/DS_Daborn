/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;

/**
 *
 * @author nbnb
 */
public class StreamTweeter {
    
    
    private void write(JsonGenerator jg, TwitterEntry entry) throws IOException
  {
    jg.writeStartObject();
    // can either do "jg.writeFieldName(...) + jg.writeNumber()", or this:
   jg.writeNumberField("id", entry.getId());
   jg.writeStringField("text", entry.getText());
   jg.writeNumberField("fromUserId", entry.getFromUserId());
   jg.writeNumberField("toUserId", entry.getToUserId());
   jg.writeStringField("langugeCode", entry.getLanguageCode());
   jg.writeEndObject();
   jg.close();
  }
}
