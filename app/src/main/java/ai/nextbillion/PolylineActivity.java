package ai.nextbillion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.annotations.Polyline;
import com.nbmap.nbmapsdk.geometry.LatLng;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;

import java.util.ArrayList;
import java.util.List;

public class PolylineActivity extends AppCompatActivity {
    private NbmapMap mMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline);
        mapView = findViewById(R.id.map_view);
        mapView.getMapAsync(this::onMapSync);
    }

    private void onMapSync(NbmapMap map) {
        mMap = map;
        applyPolyline();
    }

    private void applyPolyline() {
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(39.126646, -84.501918));
        points.add(new LatLng(39.128318, -84.525443));
        points.add(new LatLng(39.120644, -84.52626));
        points.add(new LatLng(39.116058, -84.52184));
        points.add(new LatLng(39.106345, -84.519213));
        String cincinnatiRedsColor = "#C6011F";
        Polyline polyline = mMap.addPolyline(points, cincinnatiRedsColor);
        polyline.setWidth(5);
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
