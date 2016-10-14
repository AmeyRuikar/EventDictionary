package alexa.ticketmaster;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Tester {

	public static void main(String[] args) throws Exception {
		Ticketmaster ticketmaster = new Ticketmaster();
		String q = "", tableName = "events";
		Path out = Paths.get("queries.sql");
		List<String> queryList = new ArrayList<String>();
		for(int i = 1; i<= 30; i++) {
		System.out.println("November "+ i);
		ArrayList<EventsEntity> eventsEntities = ticketmaster.getEventDetails("los angeles", "2016-11-"+String.valueOf(i));
		if(eventsEntities != null) {
		for (EventsEntity e:eventsEntities) {
			q = "INSERT INTO " + tableName + "(name, startTime, endTime, urlPlace, urlBooking, urlImg, lat , long, eventType, "
					+ "subEventType, rating, saleStartTime, saleEndTime)  VALUES ('" + e.getEventName() + "' , '" + 
					e.getEventTime() + "' , '" + e.getEventTime() + "' , '" + e.getUrlImg() + "' , '" + e.getUrlBooking() + "' , '" 
					+ e.getUrlImg() + "' , '" + e.getLatitude() + "' , '" + e.getLongitude() + "' , '" + e.getGenre() + "' , '" 
					+ e.getSubGenre() + "' , '" + e.getRating() + "' , '" + e.getSaleStartTime() + "' , '" + e.getSaleEndTime() + "')";
			queryList.add(q);
			//System.out.println(e.getEventName());
		}
		System.out.println();
	}
	}
	Files.write(out,queryList,Charset.defaultCharset());
	}
}
