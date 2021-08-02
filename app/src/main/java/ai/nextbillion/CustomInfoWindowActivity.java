package ai.nextbillion;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nbmap.nbmapsdk.annotations.BaseMarkerOptions;
import com.nbmap.nbmapsdk.annotations.Icon;
import com.nbmap.nbmapsdk.annotations.IconFactory;
import com.nbmap.nbmapsdk.annotations.Marker;
import com.nbmap.nbmapsdk.geometry.LatLng;
import com.nbmap.nbmapsdk.maps.MapView;
import com.nbmap.nbmapsdk.maps.NbmapMap;
import com.nbmap.nbmapsdk.maps.OnMapReadyCallback;
import com.nbmap.nbmapsdk.maps.Style;

public class CustomInfoWindowActivity  extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private NbmapMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NbmapMap nbmapMap) {
        mMap = nbmapMap;
        mMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                customiseInfoWindow();
                addMarker();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    private void addMarker() {
        mMap.addMarker(new CustomMarkerOptions().position(new LatLng(40.39532445, -82.91441935)).title("test Red InfoWindow").infoWindowColor(Color.RED));
        mMap.addMarker(new CustomMarkerOptions().position(new LatLng(40.41532445, -82.95441935)).title("test Blue InfoWindow").infoWindowColor(Color.BLUE));
    }


    ///////////////////////////////////////////////////////////////////////////
    // Customise InfoWindow
    ///////////////////////////////////////////////////////////////////////////

    private void customiseInfoWindow() {
        mMap.setInfoWindowAdapter(new NbmapMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                String title = marker.getTitle();
                if (marker instanceof CustomMarker) {
                    int color = ((CustomMarker) marker).getInfoWindowColor();
                    TextView textView = defaultTextView(title);
                    textView.setBackgroundColor(color);
                    return textView;
                }

                return defaultTextView(title);
            }
        });
    }

    private TextView defaultTextView(String text) {
        TextView textView = new TextView(this);
        int sixteenDp = (int) getResources().getDimension(R.dimen.attr_margin);
        textView.setText(text);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(sixteenDp, sixteenDp, sixteenDp, sixteenDp);
        return textView;
    }

    static class CustomMarker extends Marker {

        private final int infoWindowColor;

        public CustomMarker(BaseMarkerOptions baseMarkerOptions, int color) {
            super(baseMarkerOptions);
            infoWindowColor = color;
        }

        public int getInfoWindowColor() {
            return infoWindowColor;
        }
    }

    static class CustomMarkerOptions extends BaseMarkerOptions<CustomMarker, CustomMarkerOptions> {

        private int color;

        public CustomMarkerOptions() {
        }

        public CustomMarkerOptions infoWindowColor(int color) {
            this.color = color;
            return this;
        }

        @Override
        public CustomMarkerOptions getThis() {
            return this;
        }

        @Override
        public CustomMarker getMarker() {
            return new CustomMarker(this, color);
        }

        private CustomMarkerOptions(Parcel in) {
            position((LatLng) in.readParcelable(LatLng.class.getClassLoader()));
            snippet(in.readString());
            String iconId = in.readString();
            Bitmap iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
            Icon icon = IconFactory.recreate(iconId, iconBitmap);
            icon(icon);
            title(in.readString());
            infoWindowColor(in.readInt());
        }

        public static final Parcelable.Creator<CustomMarkerOptions> CREATOR
                = new Parcelable.Creator<CustomMarkerOptions>() {
            public CustomMarkerOptions createFromParcel(Parcel in) {
                return new CustomMarkerOptions(in);
            }

            public CustomMarkerOptions[] newArray(int size) {
                return new CustomMarkerOptions[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeParcelable(position, flags);
            out.writeString(snippet);
            out.writeString(icon.getId());
            out.writeParcelable(icon.getBitmap(), flags);
            out.writeString(title);
            out.writeInt(color);
        }
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
