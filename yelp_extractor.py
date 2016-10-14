from yelp.client import Client
from yelp.oauth1_authenticator import Oauth1Authenticator

auth = Oauth1Authenticator(
    consumer_key="1HnfjEqgf_5n5U3J0ZzLTQ",
    consumer_secret="yUrAuuZdV4CzeDPem5M7esmfN5E",
    token="5_Tqp6x8nl71-tRcBWyNEY6m9_C71tWT",
    token_secret="m__7JYceS2X7_t64KP454efXf2Q"
)


client = Client(auth)

response = client.search('Downtown Los Angeles')

businesses = response.businesses
#print "results:" +str(len(businesses))+'\n'

for business in businesses:
    #print business.name
    name = business.name
    eventId = business.id
    urlImg = business.image_url
    rating = business.rating
    urlBooking = business.reservation_url
    url = business.url
    lat = business.location.coordinate.latitude
    longitude = business.location.coordinate.longitude
    dealStart = 0
    dealEnd = 0

    if business.deals:
        print business.deals
        dealStart = business.deals.time_start
        dealEnd = business.deals.time_end

    #print name +"->"+str(rating)+":"+str(lat)+", "+str(longitude)+" deal from "+str(dealStart)+"\n"
    print '''INSERT INTO events("eventID", "eventName", "startTime", "endTime", "urlEvent", "urlBooking", "urlImg", latitude , longitude, "genre", "subgenre", rating, "saleStartTime", "saleEndTime")  VALUES ('%s', '%s', '2016-09-29T14:00:00Z', '2016-09-29T14:00:00Z', '%s', '%s', '%s', %f, %f, '%s', '%s', %f, '2016-09-29T14:00:00Z', '2016-09-29T14:00:00Z');'''%(eventId, name, url, urlBooking, urlImg, lat, longitude, 'food', '', rating)  
