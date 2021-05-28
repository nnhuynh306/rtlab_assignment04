# RTLAB-ASSIGNMENT04

[rtbox](https://rtbox.rta.vn/s/E3ij2DmaNX9LmQj#pdfviewer)


- Simple Android Application

## Features

- Examine API data and display in recycler view (collapsible items)
- Data is loading by pages
- Convert coordinates in loaded data to markers on Google Maps

## Libraries and APIs

- org.JSON
- Google Maps API 

## Explanation
** all loading processes is done in AsyncTasks
1. Load data:
- Data will be downloading from url to string
- Then convert that string to JSONArray and return
2. Add map markers after data is loaded:
- Find coordinates in JSONObjects of JSONArray
- Use hashmap with key is JSONObject's source_article to prevent duplicates when adding those coordinates
- Send new coordinates MapFragment using MutableLiveData and add new markers
