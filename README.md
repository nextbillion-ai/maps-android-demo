# SDK DEMO App
*notes: for troubleshooting and bugs reporting, please reach out to dan@nextbillion.ai*


This Demo has two major parts,
the first part is the basic features of Maps SDK
- mapview
- marker
- camera
- polyline
- polygon


the second part is Nextbillion API
- directions
- distance matrix
- matching(Snap to roads)

# Nextbillion AI Maps SDK

## Installation

### Maven
To download the SDK please make sure you have added mavenCentral() to your project’s top-level build.gradle

```groovy
mavenCentral()
```

module level build.gradle
```groovy
implementation "ai.nextbillion:android-maps-sdk:1.0.5"
```

### Token
```xml
<string name="nbmap_access_token">your-testing-key</string>
```

### Base API Url
```xml
<string name="nbmap_api_base_url">http://api.nextbillion.io</string>
```

### App level base url
*Currently this field is not in use, no configuration is required*
```xml
<string name="nbmap_base_url">base url</string>
```



## MapView
### Add a Mapview
There are two ways to integrate a Mapview to your Activity/View

#### Add MapView to your layout XML
```xml
<com.nbmap.nbmapsdk.maps.MapView
android:id="@+id/map_view"
android:layout_width="match_parent"/>
```

```xml
<com.nbmap.nbmapsdk.maps.MapView
android:id="@+id/map_view"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:nbmap_cameraTargetLat="40.38532445"
app:nbmap_cameraTargetLng="-82.91441935"
app:nbmap_cameraZoom="10" />
```


#### Instantiate a MapView and then add it to a parent view
```java
mapView = new MapView(context);
parentView.addView(mapView);
```


### Lifecycle of MapView
It is important to bind MapView’s lifecycle with Activity’s
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mapView = findViewById(R.id.map_view);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);
}

///////////////////////////////////////////////////////////////////////////
// Lifecycle
/////////////////////////////////////////////////////////////////////////

@Override
protected void onStart() {
    super.onStart();
    mapView.onStart();
}

@Override
protected void onResume() {
    super.onResume();
    mapView.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    mapView.onPause();
}

@Override
protected void onStop() {
    super.onStop();
    mapView.onStop();
}

@Override
protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
}

@Override
public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
}
```


## Camera

### Get current Camera Position
```java
    CameraPosition currentCameraPosition = mMap.getCameraPosition();
    double currentZoom = currentCameraPosition.zoom;
    double currentTilt = currentCameraPosition.tilt;
```

### Update Camera Position
```java
    int millisecondSpeed = 300;
    CameraPosition position = new CameraPosition.Builder()
         .target(new LatLng(12.97551913,77.58917229))
         .zoom(12)
         .tilt(20)
         .build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), millisecondSpeed);
```

## Marker
A marker identifies a location or a single point on a map, our SDK uses a default image icon which can be replaced by any bitmap image you need to by calling APIs that we have exposed.
### Add a Marker
MarkerOptions exposes APIs that allow you to modify the Marker as you wish, but we are also providing a handy API that accepts a LatLng to mark a location without creating a MarkerOptions.
```java
public Marker addMarker(@NonNull LatLng position)

public Marker addMarker(@NonNull MarkerOptions markerOptions)

public Marker addMarker(@NonNull BaseMarkerOptions markerOptions)

public List<Marker> addMarkers(@NonNull List<? extends BaseMarkerOptions> markerOptionsList)

```

Example
```java
mMap.addMarker(new LatLng(12.97551913,77.58917229));
```



### Marker events

```java
/**
* Sets a callback that's invoked when the user clicks on a marker.
*
* @param listener The callback that's invoked when the user clicks on a marker.
*                 To unset the callback, use null.
*/
public void setOnMarkerClickListener(@Nullable OnMarkerClickListener listener) {
    annotationManager.setOnMarkerClickListener(listener);
}
```
Example
```java
mMap.setOnMarkerClickListener(new NbmapMap.OnMarkerClickListener() {
   @Override
   public boolean onMarkerClick(@NonNull Marker marker) {
       if("Title".equals(marker.getSnippet())){
           //your logic
       }
       return false;
   }
});
```



### Update a Marker
Whenever you have added a marker to the NbmapMap, you will get an instance of Marker.
```java
Marker marker = mMap.addMarker(new LatLng(12.97551913,77.58917229));
```

with this instance, you can update it’s properties dynamically:

### Custom Marker’s Icon
```java
Bitmap iconBitmap = your bitmap logic;
marker.setIcon(IconFactory.getInstance(MarkerActivity.this).fromBitmap(iconBitmap));
```

### Remove a Marker
You can remove a marker by calling removeMarker method of NbmapMap
```java
mMap.removeMarker(marker);
```


### Info Window
The Marker has a built-in InfoWindow, you can show InfoWindow by clicking on the Marker if you have assigned a title or snippet to your MarkerOptions.
```java
mMap.addMarker(new MarkerOptions().position(new LatLng(12.97780156,77.59656748)).title("Title"));
mMap.addMarker(new MarkerOptions().position(new LatLng(12.98208919,77.60329262)).snippet("Snippet"));
```

#### Customise InfoWindow
```java
mMap.setInfoWindowAdapter(new NbmapMap.InfoWindowAdapter() {
    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        //Logic of customising infoWindow’s View
        return your view;
    }
});
```


## Polyline
### Available APIs
```java
public Polyline addPolyline(@NonNull LatLng origin, @NonNull LatLng dest, int color)

public Polyline addPolyline(@NonNull LatLng origin, @NonNull LatLng dest, String color)

public Polyline addPolyline(@NonNull List<LatLng> points, int color)

public Polyline addPolyline(@NonNull List<LatLng> points, String color)

public Polyline addPolyline(@NonNull PolylineOptions polylineOptions)

public List<Polyline> addPolylines(@NonNull List<PolylineOptions> polylineOptionsList)

```


### Example
```java
nbMapAPI.addPolyline(origin, dest, 0xff00ffff);
```


### Click Event
```java
/**
* Sets a callback that's invoked when the user clicks on a polyline.
*
* @param listener The callback that's invoked when the user clicks on a polyline.
*                 To unset the callback, use null.
*/
public void setOnPolylineClickListener(@Nullable OnPolylineClickListener listener) {
annotationManager.setOnPolylineClickListener(listener);
}
```
Example
```java
 mMap.setOnPolylineClickListener(new NbmapMap.OnPolylineClickListener() {
     @Override
     public void onPolylineClick(@NonNull Polyline polyline) {
                
     }
 });
```



## Polygon
### Available APIs
```java
public Polygon addPolygon(@NonNull List<LatLng> points, int color)

public Polygon addPolygon(@NonNull List<LatLng> points, String color)

public Polygon addPolygon(@NonNull PolygonOptions polygonOptions)

public List<Polygon> addPolygons(@NonNull List<PolygonOptions> polygonOptionsList)

```

### Example
```java
nbMapAPI.addPolygon(points, 0xffff0000);
```


### Click Event
```java
/**
* Sets a callback that's invoked when the user clicks on a polygon.
*
* @param listener The callback that's invoked when the user clicks on a polygon.
*                 To unset the callback, use null.
*/
public void setOnPolygonClickListener(@Nullable OnPolygonClickListener listener) {
    annotationManager.setOnPolygonClickListener(listener);
}
```
Example
```java
  mMap.setOnPolygonClickListener(new NbmapMap.OnPolygonClickListener() {
      @Override
      public void onPolygonClick(@NonNull Polygon polygon) {
                
      }
  );
```

## Tile server configuration
Currently we only support local styling(no online styling support yet), there is a sample style json under folder ‘asset’ called ‘nb_bright.json’, you should be able to find there is no source configured, and all layers are referring to a source called ‘openmaptiles’, so whenever your style.json is loaded, you can configure your tile source(openmaptiles) programmatically by calling:
```java
public static void setDefaultTileService(@NonNull NbmapMap map, @NonNull String tileServer) {
   map.setStyle(new Style.Builder().fromUri("asset://nb_bright.json"), style -> {
       if (style.getSource("openmaptiles") == null) {
           VectorSource vectorSource = new VectorSource("openmaptiles", tileServer);
           style.addSource(vectorSource);
       }
   });
}
```


# Nextbillion APIs

## Directions
### Available APIs
```java
public Response<NBDirectionsResponse> getDirections(NBLocation origin, NBLocation dest)

public void enQueueGetDirections(NBLocation origin, NBLocation dest, Callback<NBDirectionsResponse> callback)

public void showDirections(NBLocation origin, NBLocation dest, final NbmapMap nbmapMap, final Context context)

```

### Example
```java
NBMapAPIClient client = new NBMapAPIClient();
client.showDirections(
new NBLocation(12.96206481,77.56687669),
new NBLocation(12.99150562,77.61940507),
nbmapMap, DirectionsActivity.this);

//OR

client.enQueueGetDirections(origin, destination, new Callback<NBDirectionsResponse>() {
    @Override
    public void onResponse(@NonNull Call<NBDirectionsResponse> call, @NonNull Response<NBDirectionsResponse> response) {
    
    }
    
    @Override
    public void onFailure(@NonNull Call<NBDirectionsResponse> call, @NonNull Throwable t) {
    }
});
```




## Distance matrix
### Available APIs
```java
public void enqueueGetDistanceMatrix(List<NBLocation> origins, List<NBLocation> destinations, Callback<NBDistanceMatrixResponse> callback)

public Response<NBDistanceMatrixResponse> getDistanceMatrix(List<NBLocation> origins, List<NBLocation> destinations)

```

### Example
```java
apiClient.enqueueGetDistanceMatrix(origins, destinations, new Callback<NBDistanceMatrixResponse>() {
    @Override
    public void onResponse(@NonNull Call<NBDistanceMatrixResponse> call, @NonNull Response<NBDistanceMatrixResponse> response) {
    
    }
    
    @Override
    public void onFailure(@NonNull Call<NBDistanceMatrixResponse> call, @NonNull Throwable t) {
    
    }
});
```



## Snap to roads(Matching)
The matching(SnapToRoads) will take a series of input locations and return an array of best matched locations that are on the route.
### Available APIs
```java
public Response<NBSnapToRoadResponse> getSnapToRoad(List<NBLocation> path)

public void enqueueSnapToRoad(List<NBLocation> path, Callback<NBSnapToRoadResponse> callback)

public void showMatchedRoute(List<NBLocation> path, final NbmapMap map, final Context context)
```

### Example
```java
private void showMatchedRoute() {
    NBMapAPIClient client = new NBMapAPIClient();
    List<NBLocation> locations = new ArrayList<NBLocation>();
    locations.add(new NBLocation(12.94685395,77.57421511));
    locations.add(new NBLocation(12.96087173,77.57567788));
    locations.add(new NBLocation(12.96628856,77.58859895));
    client.showMatchedRoute(locations, nbmapMap, SnapToRoadsActivity.this);
}
```



