package alexa.ticketmaster;

import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Ticketmaster {
	private static final String hostServer = "https://app.ticketmaster.com";

	private static final String API_KEY = "apikey";
	private static final String apiValue = "7elxdku9GGG5k8j0Xm8KWdANDgecHMV0";
	//rBMQRcmAgtYn65vez0A6iQIpjjXAOAzQ

	private static final String discoveryApi = "/discovery/v2";
	private static final String eventsEndPoint = "/events.json";

	private static final int HOURS_INDEX = 0;
	private static final int MINUTES_INDEX = 1;

	private ArrayList<String> eventNamesList = new ArrayList<String>();

	public ArrayList<EventsEntity> getEventDetails(String location, String date) throws Exception {
		WebResource resource = setupClient();
		resource = resource.path(eventsEndPoint);

		// Creating request parameters
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add(API_KEY, apiValue);
		params.add("keyword", URLEncoder.encode(location));
		params.add("startDateTime", constructDateTimeQueryParam(date));

		resource = resource.queryParams(params);

		ClientResponse clientResponse = resource.get(ClientResponse.class);
		ArrayList<EventsEntity> eventsEntities = null;
		if (clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
			String response = clientResponse.getEntity(String.class);
			JSONObject jsonObject = new JSONObject(response);
			eventsEntities = parseJsonObj(jsonObject);
		}

		return eventsEntities;
	}

	private String constructDateTimeQueryParam(String date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time = dateFormat.format(cal.getTime());

		String timeQuery = "T" + time + "Z";
		return date + timeQuery;
	}

	private WebResource setupClient() {
		Client client = Client.create();
		WebResource webResource = client.resource(hostServer).path(discoveryApi);
		return webResource;
	}

	private String convertTimeToText(String time) {
		String[] timeStrArray = time.split(":");

		String formattedTime = "";
		String timeUnit = "";

		int hours = Integer.parseInt(timeStrArray[HOURS_INDEX]);
		int minutes = Integer.parseInt(timeStrArray[MINUTES_INDEX]);

		if (hours > 12) {
			hours = hours % 12;
			timeUnit = "P M ";
		} else {
			timeUnit = "A M ";
		}

		if (hours == 0) { // convert 00 hours as 12 AM
			hours = 12;
		}

		formattedTime += String.valueOf(hours);
		if (minutes != 0) {
			formattedTime += " " + String.valueOf(minutes);
		}
		formattedTime += " " + timeUnit;

		return formattedTime;
	}

	private String convertStringToSpacedOutInt(int number) {

		String spacedOutAddr = "";
		while (number != 0) {
			int digit = number % 10;
			spacedOutAddr += String.valueOf(digit) + " ";
			number = number / 10;
		}

		return new StringBuilder(spacedOutAddr).reverse().toString() + " ";
	}

	private String SpaceOutAdress(String address) {

		String streetNumber = address.split(" ")[0];
		int stNumberInt = 0;
		try {
			stNumberInt = Integer.parseInt(streetNumber);
		} catch (NumberFormatException e) {
			stNumberInt = 0;
		}

		if (stNumberInt != 0) {
			String spacedOutAddr = convertStringToSpacedOutInt(stNumberInt);
			return spacedOutAddr;
		}
		return null;
	}

	private String getEventAddress(JSONObject addressObj) throws JSONException {
		JSONObject addressObj1 = null;
		String eventAddress = "";
		if(addressObj.has("address")) {
		addressObj1 = addressObj.getJSONObject("address");
		if(addressObj1.has("line1")) {
		eventAddress += addressObj1.getString("line1");
		}
		}
		return eventAddress;
	}

	private String getLongitude(JSONObject addressObj) throws JSONException {
		String longitude = "";
		if(addressObj.has("location")) {
		longitude = addressObj.getJSONObject("location").getString("longitude");
		}
		return longitude;
	}

	private String getLatitude(JSONObject addressObj) throws JSONException {
		String latitude = ""; 
		if(addressObj.has("location")) {
		latitude = addressObj.getJSONObject("location").getString("latitude");
		}
		return latitude;
	}

	private String getEventTime(JSONObject timeObj) throws JSONException {
		JSONObject startTimeObj;
		String startTime = "";

		startTimeObj = timeObj.getJSONObject("start");
		startTime = startTimeObj.getString("localTime");

		String formattedTime = convertTimeToText(startTime);

		return formattedTime;
	}

	private String getEventDescription(String eventName, JSONObject eventJsonObj) throws JSONException {
		String eventDesc = "";

		JSONObject timeObj = eventJsonObj.getJSONObject("dates");
		JSONArray addressObjArray = eventJsonObj.getJSONObject("_embedded").getJSONArray("venues");

		String eventStartTime = "";
		eventStartTime = getEventTime(timeObj);
		String eventAddress = "";
		eventAddress = getEventAddress(addressObjArray.getJSONObject(0)); //Default: Taking only the first venue.

		if (!eventStartTime.equals("")) {
			eventDesc += eventName + " is starting at ";
			eventDesc += eventStartTime;
		}

		if (!eventAddress.equals("")) {
			eventDesc += "at ";
			String spacedOutAddr = SpaceOutAdress(eventAddress);
			if (spacedOutAddr == null) {
				eventDesc += eventAddress;
			} else {
				// remove street number and add spacedoutAddress
				String[] addrElemArray = eventAddress.split(" ");
				String formattedAddr = spacedOutAddr;
				for (int i = 1; i < addrElemArray.length; i++) {
					formattedAddr += addrElemArray[i];
				}
				eventDesc += formattedAddr;
			}
		}

		if (eventDesc.equals("")) {
			eventDesc += "Sorry, Event information is not available for the event " + eventName;
		}

		return eventDesc;
	}

	private Boolean addToEventsList(String eventName) {
		if (eventNamesList.size() == 0 || !eventNamesList.contains(eventName)) {
			eventNamesList.add(eventName);
			return true;
		}
		return false;
	}

	private ArrayList<EventsEntity> parseJsonObj(JSONObject mainJsonObj) throws Exception {
		ArrayList<EventsEntity> eventsArrList = new ArrayList<EventsEntity>();
		
		JSONObject _embeddedObj = mainJsonObj.getJSONObject("_embedded");
		JSONArray jsonArray = _embeddedObj.getJSONArray("events");

		for (int i = 0; i < jsonArray.length(); i++) {
			String latitude = "", longitude = "", genre = "", subGenre = "", genre1 = "", genre2 = "", saleStartTime = "", saleEndTime = "";
			JSONObject jsonEventObject = jsonArray.getJSONObject(i);
			String eventName = jsonEventObject.getString("name");
			String eventTime = (jsonEventObject.getJSONObject("dates")).getJSONObject("start").getString("localTime"); 
			String eventDate = (jsonEventObject.getJSONObject("dates")).getJSONObject("start").getString("localDate");
			String urlBooking = (jsonEventObject.getString("url"));
			String urlImg = (jsonEventObject.getJSONArray("images")).getJSONObject(0).getString("url");
			if((jsonEventObject.has("classifications"))) {
				if((jsonEventObject.getJSONArray("classifications")).getJSONObject(0).has("segment")) {
				genre1 = (jsonEventObject.getJSONArray("classifications")).getJSONObject(0).
						getJSONObject("segment").getString("name");
				} 
				if((jsonEventObject.getJSONArray("classifications")).getJSONObject(0).has("genre")) {
				genre2 = (jsonEventObject.getJSONArray("classifications")).getJSONObject(0).
						getJSONObject("genre").getString("name");
				}
				genre = (genre1 != null && genre1 != "Undefined")?genre1 : genre2;
				if((jsonEventObject.getJSONArray("classifications")).getJSONObject(0).has("subGenre")) {
				subGenre = (jsonEventObject.getJSONArray("classifications")).getJSONObject(0).
						getJSONObject("subGenre").getString("name");	
				}
			}
			if((jsonEventObject.has("sales"))) {
			JSONObject sales = jsonEventObject.getJSONObject("sales"); 
			if(sales.has("public")) {
			JSONObject publicObj = sales.getJSONObject("public");
			if(publicObj.has("startDateTime")) {
			saleStartTime = publicObj.getString("startDateTime");
			}
			if(publicObj.has("endDateTime")) {
			saleEndTime = publicObj.getString("endDateTime");
			} 
			}
			}
			String rating = "0";
			if (addToEventsList(eventName)) {
				// event description
				String eventDesc = getEventDescription(eventName, jsonEventObject);
				// log(eventDesc);

				// latitude
				JSONArray addrArray = jsonEventObject.getJSONObject("_embedded").getJSONArray("venues");
				// get the first venue at 0th index
				latitude = getLatitude(addrArray.getJSONObject(0));
				longitude = getLongitude(addrArray.getJSONObject(0));
				String eventAddress = getEventAddress(addrArray.getJSONObject(0));
				if((latitude == "" || longitude == "") && eventAddress.trim() != "") {
				JSONObject loc = geocoding(eventAddress);
				if(loc != null) {
				latitude = String.valueOf(loc.getDouble("lat"));
				longitude = String.valueOf(loc.getDouble("lng"));
				}
				}
				EventsEntity eventDetails = new EventsEntity(parseString(eventName), eventDesc, eventTime, eventDate, 
						urlBooking, urlImg, genre, subGenre, saleStartTime, saleEndTime, rating, 
						eventAddress, latitude, longitude);

				eventsArrList.add(eventDetails);
			}
		}
		return eventsArrList;
	}
	
	public static JSONObject geocoding(String addr) throws Exception
	{
	    // build a URL
	    String s = "http://maps.google.com/maps/api/geocode/json?" +
	                    "sensor=false&address=";
	    s += URLEncoder.encode(addr, "UTF-8");
	    URL url = new URL(s);
	 
	    // read from the URL
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	 
	    // build a JSON object
	    JSONObject obj = new JSONObject(str);
	    if (! obj.getString("status").equals("OK"))
	        return null;
	 
	    // get the first result
	    JSONObject res = obj.getJSONArray("results").getJSONObject(0);
	    System.out.println(res.getString("formatted_address"));
	    JSONObject loc =
	        res.getJSONObject("geometry").getJSONObject("location");
	    System.out.println("lat: " + loc.getDouble("lat") +
	                        ", lng: " + loc.getDouble("lng"));
	    return loc;
	}
	
	private String parseString(String string) {
		return string.replaceAll("&", "and").replaceAll(" v ", " vs ").toLowerCase();
	}
	
}
