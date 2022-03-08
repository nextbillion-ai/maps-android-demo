package ai.nextbillion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.api.NBMapAPIClient;
import com.nbmap.nbmapsdk.api.NBRoutLineConfig;
import com.nbmap.nbmapsdk.api.NBRouteLineCallback;
import com.nbmap.nbmapsdk.lite.diretions.NBRouteLine;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;
import com.nbmap.nbmapsdk.maps.OnMapReadyCallback;
import com.nbmap.nbmapsdk.maps.Style;

import ai.nextbillion.api.models.NBLocation;

public class DirectionsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private NbmapMap nbmapMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NbmapMap nbmapMap) {
        this.nbmapMap = nbmapMap;
        nbmapMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                showDirections();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    NBRouteLine routeLine;
    private void showDirections() {
        NBMapAPIClient client = new NBMapAPIClient();
        NBRoutLineConfig config = new NBRoutLineConfig.Builder()
                .setDestinationIcon(R.drawable.blue_marker)
                .setOriginIcon(R.mipmap.ic_nb_taxi)
                .setLineColor("#00ff00")
                .setRouteName("test_directions")
                .build();
        NBLocation origin = new NBLocation(12.96206481, 77.56687669);
        NBLocation dest = new NBLocation(12.99150562, 77.61940507);

        client.showDirections(origin, dest, config, nbmapMap, this, new NBRouteLineCallback() {
            @Override
            public void onRouteRendered(NBRouteLine nbRouteLine) {
                routeLine = nbRouteLine;
                routeLine.setLineWidth(5.0f);
                routeLine.setLineColor("#34ffff");
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

//        client.showDirections(
//                new NBLocation(12.96206481,77.56687669),
//                new NBLocation(12.99150562,77.61940507),
//                "test_directions_2",
//                nbmapMap, DirectionsActivity.this);
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
