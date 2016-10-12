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
print "results:" +str(len(businesses))+'\n'

for business in businesses:
    #print business.name
    name = business.name
    eventId = business.id
    urlImg = business.image_url
    rating = business.rating
    urlBooking = business.reservation_url
    url = business.url
    print name +"->"+str(rating)+":"+url+"\n"
