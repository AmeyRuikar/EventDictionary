package alexa.ticketmaster;

public class EventsEntity {
	private String eventName;
	private String description; // eg: this event starts at <startTime> and it is happening at <address>
	private String latitude;
	private String longitude;
	private String eventTime;
	private String eventDate; 
	private String urlBooking;
	private String urlImg;
	private String genre;
	private String subGenre;
	private String saleStartTime;
	private String saleEndTime; 
	private String rating;
	private String eventAddress;
	
	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
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

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getEventName() {
		return eventName;
	}

	public String getDescription() {
		return description;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	public EventsEntity(String eventName, String description, String eventTime, String eventDate, 
			String urlBooking, String urlImg, String genre, String subGenre, String saleStartTime, String saleEndTime, 
			String rating, String eventAddress, String latitude, String longitude) {
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
