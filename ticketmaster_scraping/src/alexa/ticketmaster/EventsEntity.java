package alexa.ticketmaster;

import java.sql.Timestamp;

public class EventsEntity {
	private String eventName;
	private String description; // eg: this event starts at <startTime> and it is happening at <address>
	private double latitude;
	private double longitude;
	private Timestamp eventTime;
	private Timestamp eventDate; 
	private String urlBooking;
	private String urlImg;
	private String genre;
	private String subGenre;
	private Timestamp saleStartTime;
	private Timestamp saleEndTime; 
	private double rating;
	
	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	private String eventAddress;
	
	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Timestamp getEventTime() {
		return eventTime;
	}


	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}


	public Timestamp getEventDate() {
		return eventDate;
	}


	public void setEventDate(Timestamp eventDate) {
		this.eventDate = eventDate;
	}


	public String getUrlBooking() {
		return urlBooking;
	}


	public void setUrlBooking(String urlBooking) {
		this.urlBooking = urlBooking;
	}


	public String getUrlImg() {
		return urlImg;
	}


	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getSubGenre() {
		return subGenre;
	}


	public void setSubGenre(String subGenre) {
		this.subGenre = subGenre;
	}


	public Timestamp getSaleStartTime() {
		return saleStartTime;
	}


	public void setSaleStartTime(Timestamp saleStartTime) {
		this.saleStartTime = saleStartTime;
	}


	public Timestamp getSaleEndTime() {
		return saleEndTime;
	}


	public void setSaleEndTime(Timestamp saleEndTime) {
		this.saleEndTime = saleEndTime;
	}



	public String getEventAddress() {
		return eventAddress;
	}


	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}


	public EventsEntity(String eventName, String description, Timestamp eventTime, Timestamp eventDate, 
			String urlBooking, String urlImg, String genre, String subGenre, Timestamp saleStartTime, Timestamp saleEndTime, 
			double rating, String eventAddress, double latitude, double longitude) {
		this.eventName = eventName;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.eventTime = eventTime;
		this.eventDate = eventDate;
		this.urlBooking = urlBooking;
		this.urlImg = urlImg;
		this.genre = genre;
		this.subGenre = subGenre;
		this.saleStartTime = saleStartTime;
		this.saleEndTime = saleEndTime;
		this.rating = rating;
		this.eventAddress = eventAddress;
	}
}
