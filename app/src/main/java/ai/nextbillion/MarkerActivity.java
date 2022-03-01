package ai.nextbillion;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.annotations.Marker;
import com.nbmap.nbmapsdk.annotations.MarkerOptions;
import com.nbmap.nbmapsdk.geometry.LatLng;
import com.nbmap.nbmapsdk.location.engine.LocationEngine;
import com.nbmap.nbmapsdk.location.engine.LocationEngineCallback;
import com.nbmap.nbmapsdk.location.engine.LocationEngineProvider;
import com.nbmap.nbmapsdk.location.engine.LocationEngineRequest;
import com.nbmap.nbmapsdk.location.engine.LocationEngineResult;
import com.nbmap.nbmapsdk.location.permissions.PermissionsListener;
import com.nbmap.nbmapsdk.location.permissions.PermissionsManager;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;
import com.nbmap.nbmapsdk.maps.OnMapReadyCallback;
import com.nbmap.nbmapsdk.maps.Style;
import com.nbmap.nbmapsdk.style.sources.GeoJsonOptions;
import com.nbmap.nbmapsdk.utils.BitmapUtils;

import java.util.List;

import ai.nextbillion.maps.plugins.annotation.Symbol;
import ai.nextbillion.maps.plugins.annotation.SymbolManager;
import ai.nextbillion.maps.plugins.annotation.SymbolOptions;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private NbmapMap mMap;


    static final long DEFAULT_INTERVAL_MILLIS = 1000L;
    static final long DEFAULT_FASTEST_INTERVAL_MILLIS = 1000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        initPermissionManager();
    }

    @Override
    public void onMapReady(@NonNull NbmapMap nbmapMap) {
        mMap = nbmapMap;
        mMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addMarker();
                addSymbol(nbmapMap, mapView, style);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    private void addMarker() {
        Marker marker = mMap.addMarker(new LatLng(12.97551913,77.58917229));
        mMap.removeMarker(marker);
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.97780156,77.59656748)).title("Title"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.98208919,77.60329262)).snippet("Snippet"));
    }

    private void addSymbol(NbmapMap nbmapMap, MapView mapView, Style style){
        style.addImage("SYMBOL_ICON",
                BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.nbmap_marker_icon_default)),
                false);
        GeoJsonOptions options = new GeoJsonOptions().withTolerance(0.4f);
        SymbolManager symbolManager = new SymbolManager(mapView, nbmapMap, style, null, options);
        SymbolOptions symbolOptions = new SymbolOptions()
                .withLatLng(new LatLng(12.97551913,77.58917229))
                .withIconImage("SYMBOL_ICON")
                .withTextField("Symbol");
        Symbol symbol = symbolManager.create(symbolOptions);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    private PermissionsManager permissionsManager;

    private void initPermissionManager(){
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            fetchLocation();
            mapView.getMapAsync(this);
        } else {
            permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                    Toast.makeText(MarkerActivity.this, "You need to accept location permissions.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted) {
                        fetchLocation();
                        mapView.getMapAsync(MarkerActivity.this);
                    } else {
                        finish();
                    }
                }
            });
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void fetchLocation(){
        LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(this);
        LocationEngineRequest locationEngineRequest =
                new LocationEngineRequest.Builder(DEFAULT_INTERVAL_MILLIS)
                        .setFastestInterval(DEFAULT_FASTEST_INTERVAL_MILLIS)
                        .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                        .build();

         LocationEngineCallback<LocationEngineResult> locationEngineListener
                = new LocationEngineCallback<LocationEngineResult>() {
             @Override
             public void onSuccess(LocationEngineResult result) {

             }

             @Override
             public void onFailure(@NonNull Exception e) {

             }
         };
         locationEngine.requestLocationUpdates(locationEngineRequest, locationEngineListener, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

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
}
