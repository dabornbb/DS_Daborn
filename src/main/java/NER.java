
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;
import java.util.Properties;
public class NER {
	public static void main(String aa[]) {
//		NER ob=new NER();
//		System.out.println(ob.Location("  "));
	}

  public String Location(String str) {
	  if (str==null || str.equals(" ") || str.equals("")) return null;	 
    // set up pipeline properties
    Properties props = new Properties();
    
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
    props.setProperty("annotators", props.getProperty("annotators"));
    
    props.setProperty("ner.applyFineGrained", "false");
    props.setProperty("ssplit.eolonly", "true");
    props.setProperty("coref.algorithm", "neural");

    // example customizations (these are commented out but you can uncomment them to see the results
    props.setProperty("enforceRequirements","false");
    // disable fine grained ner
//    props.setProperty("ner.applyFineGrained", "false");

    // customize fine grained ner
 //   props.setProperty("ner.fine.regexner.mapping", "example.rules");
    props.setProperty("ner.fine.regexner.ignorecase", "true");

    // add additional rules
 //   props.setProperty("ner.additional.regexner.mapping", "example.rules");
    props.setProperty("ner.additional.regexner.ignorecase", "true");
//    props.setProperty("edu.stanford.nlp.pipeline.NERCombinerAnnotator");
    
    // add 2 additional rules files ; set the first one to be case-insensitive
 //   props.setProperty("ner.additional.regexner.mapping", "ignorecase=true,example_one.rules;example_two.rules");

    // set up pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // make a n example document
    CoreDocument doc = new CoreDocument(str);
    // annotate the document
    pipeline.annotate(doc);
    // view resultss
    for (CoreEntityMention em : doc.entityMentions()) 
    	if(em.entityType().equals("LOCATION"))
    		return em.text();
    	return null;
//		    System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
//		    System.out.println("---");
//		    System.out.println("tokens and ner tags");
//    }
//    String tokensAndNERTags = doc.tokens().stream().map(token -> "("+token.word()+","+token.ner()+")").collect(
//        Collectors.joining(" "));
//    System.out.println(tokensAndNERTags);
		
  }

}
