import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.Instant;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.TextFormat.ParseException;

public class StreamingFile {
	public static void main(String a[]) throws IOException, ParseException, org.json.simple.parser.ParseException {
		File theFile=new File("Big_GeoBox.json");
		JsonParser jsonParser = new JsonParser();
		JsonObject array;
	    String place_name;
	    int place_id;
	    int c=0;
	    NER ner=new NER();
	    String id_str = null,id_str2;
//		FileOutputStream  writer =  new FileOutputStream ("big-geo.txt"); 
		   // writer.write("Hello World !!");
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("allin123.txt"), "utf-8"));
		LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
		String city[]= {"London","Paris","New York","Sao Paulo","Toronto","Berlin","San Diego","Tokyo","Barcelona","Punjab","Moscow","Chicago","Singapore","Dubai","San Francisco","Madrid","Amsterdam","Los Angeles","Rome","Boston","San Jose","Toronto","Washington DC","Zurich","Hong Kong","Beijing","Berlin","Sydney","Las Vegas","Frankfurt","Miami","San Diego","Seoul","Prague","Munich","Houston","Milan","Dublin","Seattle","Dallas","Istanbul","Vancouver","Melbourne","Vienna","Abu Dhabi","Calgary","Brussels","Denver","Doha","Oslo","Orlando","Austin","Stockholm","Montreal","Philadelphia","Brisbane","Atlanta","Copenhagen","Saint Petersburg","Perth","Minneapolis","Lisbon","Venice","Portland","Hamburg","Tel Aviv","Lyon","Florence","Stuttgart","Luxembourg","Edmonton","Osaka","Auckland","Ottawa","Budapest","Helsinki","Athens","Cologne","Bangkok","Charlotte","Phoenix","New Orleans","Baltimore","Valencia","Manchester","Nashville","Salt Lake City","Dusseldorf","Sao Paulo","Rio de Janeiro","Raleigh","Warsaw","Marseille","San Antonio","Birmingham","Columbus","Shanghai","Adelaid","Sydney","New South Wales","Melbourne","Brisbane","Perth","Adelaide","Gold Coast–Tweed Heads","Newcastle–Maitland","Canberra–Queanbeyan","Sunshine Coast","Wollongong","Geelong","Hobart","Townsville","Cairns","Darwin","Toowoomba","Ballarat","Bendigo","Albury–Wodonga","Launceston","Mackay","Rockhampton","Bunbury","Coffs Harbour","Bundaberg","Wagga Wagga","Hervey Bay","Mildura–Wentworth","Shepparton–Mooroopna","Port Macquarie","Gladstone–Tannum Sands","Tamworth","Traralgon–Morwell","Orange","Bowral–Mittagong","Busselton","Dubbo","Warragul–Drouin","Geraldton","Nowra–Bomaderry","Bathurst","Warrnambool","Albany","Devonport","Kalgoorlie–Boulder","Mount Gambier","Lismore","Nelson Bay","Maryborough","Burnie–Wynyard","Alice Springs","Victor Harbor–Goolwa","Ballina","Taree","Morisset–Cooranbong","Armidale","Goulburn","Whyalla","Gympie","Echuca–Moama","Forster–Tuncurry","Griffith","Wangaratta","St Georges Basin–Sanctuary Point","Grafton","Yeppoon","Murray Bridge","Mount Isa","Camden Haven","Broken Hill","Moe–Newborough","Karratha","Horsham","Batemans Bay","Singleton","Port Lincoln","Ulladulla","Bairnsdale","Warwick","Kempsey","Sale","Ulverstone","Broome","Port Hedland","Port Pirie","Emerald","Port Augusta","Lithgow","Colac","Mudgee","Muswellbrook","Esperance","Parkes","Swan Hill","Portland","Kingaroy","Detroit","Sacramento","Milwaukee","Kansas City","Tampa","Nuremberg","Bristol"}; 		
		
//		for(int j=0;j<city.length;j++)
//			System.out.print(j++);
		try {
		    while (it.hasNext()) {
		    	System.out.println(c++);
		        String line = it.nextLine();
		        		array = jsonParser.parse(line).getAsJsonObject();
//		        		System.out.println(line.toString());
		        		if((array.get("place").isJsonNull()==false) && (array.getAsJsonObject("place").get("name").isJsonNull()==false) ) {
		        			
		        			place_name=array.getAsJsonObject("place").get("name").toString();
//		        			System.out.println(place_name);
		        			for(int i=0;i<city.length;i++) { 
		        				id_str2=array.getAsJsonObject("_id").get("$oid").getAsString();
//		        				System.out.println(id_str2);
		        				if((!id_str2.equals(id_str)) && (city[i].equals(place_name.replaceAll("^\"|\"$", "")))) {
		        					place_id=i;
		        					id_str=array.getAsJsonObject("_id").get("$oid").getAsString();
//				        			System.out.println(id_str);
//							        System.out.println(array.toString());
							        String txt= array.get("text").toString();
							        //Get Coordination
							        //coor = array.getAsJsonObject("place").getAsJsonObject("bounding_box").getAsJsonArray("coordinates").getAsJsonArray(); 
//							        Get City Name
							        String full_name = null;
							        if(array.get("place").isJsonNull()==false)
							        full_name= array.getAsJsonObject("place").get("full_name").toString();
//									 user lang
//							         JsonElement lang=array.get("lang");					        
//							         time stamp
							        long times= array.get("timestamp_ms").getAsLong();
							        int time = Date.from( Instant.ofEpochSecond(times)).getHours();
//							        int time =datestamp.getTimezoneOffset();
							        // user meta data00
							        // get utc_offset
							        String utc=array.getAsJsonObject("user").get("utc_offset").toString();
							        // description
							        String des=array.getAsJsonObject("user").get("description").toString();
							       // user lang
							        String user_lang =array.getAsJsonObject("user").get("lang").toString();			      
							        // user name
							        String name =array.getAsJsonObject("user").get("name").toString();			      
							        //zone
							        String loc =array.getAsJsonObject("user").get("location").toString();			      
							        //zone
//							        String time_zone=array.getAsJsonObject("user").get("time_zone");
							        String str ='"'+ner.Location(txt)+' '+ner.Location(des)+' '+full_name.replaceAll("^\"|\"$", "")+' '+time+ ' '+user_lang.replaceAll("^\"|\"$", "")+' '+name.replaceAll("^\"|\"$", "")+' '+loc.replaceAll("^\"|\"$", "")+' '+utc+'"'+','+place_id;					       
							        writer.append(str+"\n");
							        System.out.println(str);
//							        System.out.println(c++);
//							        writer.append(array.toString()+"\n");
		        				}
		        	}   
		        		}	
		    }
		} finally {
			 writer.close();
 		    LineIterator.closeQuietly(it);
		}
	}
	

}
