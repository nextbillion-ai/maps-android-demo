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
        points.add(new LatLng(12.92948165,77.61501446));
        points.add(new LatLng(12.95205978,77.60494206));
        points.add(new LatLng(12.96612918,77.60678866));
        points.add(new LatLng(12.96449325,77.59654839));
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
