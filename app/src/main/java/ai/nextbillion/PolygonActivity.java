package ai.nextbillion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.geometry.LatLng;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;

import java.util.ArrayList;
import java.util.List;

public class PolygonActivity extends AppCompatActivity {

    private NbmapMap mMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapSync);
    }

    private void onMapSync(NbmapMap map) {
        mMap = map;
        applyPolygon();
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    //center 41.21431566,-81.63779672
    private void applyPolygon() {
        //41.73164917,-81.26090926
        //41.49947295,-81.70412891
        //41.49721472,-82.00865398
        //40.79108785,-81.90915569
        //40.78423933,-81.28804516
        //41.57620605,-81.20362237
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(41.75615968, -81.28450368));
        points.add(new LatLng(41.49947295, -81.70412891));
        points.add(new LatLng(41.49721472, -82.00865398));
        points.add(new LatLng(40.79108785, -81.90915569));
        points.add(new LatLng(40.78423933, -81.28804516));
        points.add(new LatLng(41.57620605, -81.20362237));
        String clevelandCavaliersColorWine = "#336f263d";
        mMap.addPolygon(points, clevelandCavaliersColorWine);
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
