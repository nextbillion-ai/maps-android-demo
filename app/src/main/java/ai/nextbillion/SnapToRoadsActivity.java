package ai.nextbillion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.api.NBMapAPIClient;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;
import com.nbmap.nbmapsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import ai.nextbillion.api.models.NBLocation;

public class SnapToRoadsActivity extends AppCompatActivity {

    private NbmapMap nbmapMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull NbmapMap nbmapMap) {
                SnapToRoadsActivity.this.nbmapMap = nbmapMap;
                showMatchedRoute();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    private void showMatchedRoute() {
        NBMapAPIClient client = new NBMapAPIClient();
        List<NBLocation> locations = new ArrayList<NBLocation>();
        locations.add(new NBLocation(33.85956491,-117.94254080));
        locations.add(new NBLocation(33.85887621,-117.92550724));
        locations.add(new NBLocation(33.87275507,-117.90719777));
        client.showMatchedRoute(locations, "test_snap_to_road", nbmapMap, SnapToRoadsActivity.this);
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
