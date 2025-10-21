#python
#python script to test all taxi-api endpoint

import requests

# URL & PORT - CHANGE IF DIFFERENT on docker-compose.yml
taxiURL = "http://localhost:8080"

# PATHS - DO NOT CHANGE
# HEALTH

healthURL = taxiURL + "/taxi-api/_healthcheck"
boroughURL = taxiURL + "/taxi-api/taxi/locationId"
taxiHistoryURL = taxiURL + "/taxi-api/taxi/history"

print("Running test script on Taxi API endpoints...")
print("---")

# Run endpoints
health = requests.get(healthURL)
print("Taxi API Health Check Response: "+str(health.status_code))


# Borough Data Endpoints

# Get Location Ids and Zones by Borough
borough = "Manhattan"
getLocationByBoroughURL = boroughURL + "/?borough=" + borough
#print(getLocationByBoroughURL)
locationByBorough = requests.get(getLocationByBoroughURL)
print("---")
print("Get Zones and Location Ids by Borough")
print("Response code: "+str(locationByBorough.status_code))
print("Response data: " + str(locationByBorough.text))

# Get Location Ids by Borough and Zone
borough = "Manhattan"
zone = "Alphabet City"
getLocationByZoneURL = boroughURL + "/?borough=" + borough + "&zone=" + zone
#print(getLocationByZoneURL)
locationByZone = requests.get(getLocationByZoneURL)
print("--")
print("Get Location Ids by Borough and Zone")
print("Response code: "+str(locationByZone.status_code))
print("Response data: " + str(locationByZone.text))


# Taxi History Data Endpoints
# Get historic cab data - example request 
# can be green, yellow or hire
cabType = "green"
# Location ID returned from above endpoint - can look for location ID by borough and zone
startLocation = "41" 
endLocation = "24"
# Time range of taxi rides
startTime = "1/1/18 0:09"
endTime = "1/1/18 0:27"

# Get cab data by cab type
byTypeQuery = "/?type=" + cabType
byLocationQuery = "/?startLocation=" + startLocation + "&endLocation=" + endLocation
byTimeRangeQuery = "/?startTime=" + startTime + "&endTime=" + endTime

# creating URLs with query parameter
getCabDataByType = taxiHistoryURL + byTypeQuery
getCabDataByLocation = taxiHistoryURL + byLocationQuery
getCabDataByTimeRange =  taxiHistoryURL + byTimeRangeQuery
getCabDataByTypeAndLocation = taxiHistoryURL + byTypeQuery + byLocationQuery
getCabDataByTypeandTime =  taxiHistoryURL + byTypeQuery + byTimeRangeQuery

getCabDataByTypeRequest = requests.get(getCabDataByType)
print("--")
print("Get cab history By cab type")
print("Response code: "+ str(getCabDataByTypeRequest.status_code))
print("Response data: " + str(getCabDataByTypeRequest.text))


getCabDataByLocationRequest = requests.get(getCabDataByLocation)
print("--")
print("Get cab history by start and end Location")
print("Response code: " + str(getCabDataByLocationRequest.status_code))
print("Response data: " + str(getCabDataByLocationRequest.text))

getCabDataByTimeRangeRequest = requests.get(getCabDataByTimeRange)
print("--")
print("Get cab history by time range")
print("Response code: "+ str(getCabDataByTimeRangeRequest.status_code))
print("Response data: " + str(getCabDataByTimeRangeRequest.text))

# TODO (more time) can do even more queries given more time (any combination of query parameters provided above)
# getCabDataByTypeAndLocationRequest = requests.get(getCabDataByTypeAndLocation)
# print("--")
# print("Get cab history by type, start and end location")
# print("Response Code: "+ str(getCabDataByTypeAndLocationRequest.status_code))
# print("Resonse data: " + str(getCabDataByTypeAndLocationRequest.text))

# getCabDataByTypeAndTimeRequest = requests.get(getCabDataByTypeandTime)
# print("--")
# print("Get cab history by type, start and end")
# print("Response Code: "+ str(getCabDataByTypeAndTimeRequest.status_code))
# print("Resonse data: " + str(getCabDataByTypeAndTimeRequest.text))



