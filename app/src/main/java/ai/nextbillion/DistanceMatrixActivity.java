package ai.nextbillion;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.api.NBMapAPIClient;
import com.nbmap.nbmapsdk.lite.diretions.NBRouteLine;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;
import com.nbmap.nbmapsdk.maps.OnMapReadyCallback;
import com.nbmap.nbmapsdk.maps.Style;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ai.nextbillion.api.directions.NBDirectionsResponse;
import ai.nextbillion.api.distancematrix.NBDistanceMatrixResponse;
import ai.nextbillion.api.models.NBDistanceMatrixItem;
import ai.nextbillion.api.models.NBDistanceMatrixRow;
import ai.nextbillion.api.models.NBLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistanceMatrixActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private NbmapMap mMap;
    private View matrixView;
    private TextView distanceView;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        timeView = findViewById(R.id.time_view);
        distanceView = findViewById(R.id.distance_view);
        matrixView = findViewById(R.id.matrix_view);
    }

    @Override
    public void onMapReady(@NonNull NbmapMap nbmapMap) {
        mMap = nbmapMap;
        nbmapMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                calcDistanceMatrix();
            }
        });
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

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_matrix, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_o1_d1:
                showDirections(origin1, destination1, "o1-d1", "#00ff00");
                displayMatrixInfo(0, 0);
                return true;
            case R.id.action_o1_d2:
                showDirections(origin1, destination2, "o1-d2", "#ff0000");
                displayMatrixInfo(0, 1);
                return true;
            case R.id.action_o2_d1:
                showDirections(origin2, destination1, "o2-d1", "#0000ff");
                displayMatrixInfo(1, 0);
                return true;
            case R.id.action_o2_d2:
                showDirections(origin2, destination2, "o2-d2", "#00ffdd");
                displayMatrixInfo(1, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayMatrixInfo(int originalIndex, int destinationIndex) {
        if (rows != null && rows.size() > originalIndex) {
            NBDistanceMatrixRow row = rows.get(originalIndex);
            if (row.elements != null && row.elements.size() > destinationIndex) {
                NBDistanceMatrixItem item = row.elements.get(destinationIndex);
                int distanceInMeters = item.distance().value;
                int timeInSeconds = item.duration().value;
                distanceView.setText(distanceInMeters + "m");
                timeView.setText(timeInSeconds + "s");
            }
        }
    }

    List<NBDistanceMatrixRow> rows = new ArrayList<>();
    NBRouteLine currentRoute;
    NBLocation origin1 = new NBLocation(33.88128524,-118.03812790);
    NBLocation origin2 = new NBLocation(33.88805254,-117.96152865);
    NBLocation destination1 = new NBLocation(33.89636582,-117.89832633);
    NBLocation destination2 = new NBLocation(33.86152174,-117.80937602);

    private void calcDistanceMatrix() {
        //O1 39.11964617,-84.52494380
        //O2 39.10625083,-84.51918908
        //D1 39.12631053,-84.50184151
        //D2 39.11673435,-84.49742122
        NBMapAPIClient apiClient = new NBMapAPIClient();
        List<NBLocation> origins = new ArrayList<>();
        List<NBLocation> destinations = new ArrayList<>();

        origins.add(origin1);
        origins.add(origin2);
        destinations.add(destination1);
        destinations.add(destination2);
        try {
            apiClient.enqueueGetDistanceMatrix(origins, destinations, new Callback<NBDistanceMatrixResponse>() {
                @Override
                public void onResponse(@NonNull Call<NBDistanceMatrixResponse> call, @NonNull Response<NBDistanceMatrixResponse> response) {
                    rows = response.body().rows();
                    matrixView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<NBDistanceMatrixResponse> call, @NonNull Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDirections(NBLocation origin, NBLocation destination, String name, String color) {
        NBMapAPIClient client = new NBMapAPIClient();
        NBRouteLine routeLine = new NBRouteLine(mMap, this, name);
        if (currentRoute != null) {
            currentRoute.remove();
        }
        try {
            routeLine.setLineColor(color);
            client.enQueueGetDirections(origin, destination, new Callback<NBDirectionsResponse>() {
                @Override
                public void onResponse(@NonNull Call<NBDirectionsResponse> call, @NonNull Response<NBDirectionsResponse> response) {
                    routeLine.drawRoute(response.body().routes().get(0));
                }

                @Override
                public void onFailure(@NonNull Call<NBDirectionsResponse> call, @NonNull Throwable t) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentRoute = routeLine;
    }
}