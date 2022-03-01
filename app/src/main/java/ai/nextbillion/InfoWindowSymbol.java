package ai.nextbillion;


import static com.nbmap.nbmapsdk.style.expressions.Expression.eq;
import static com.nbmap.nbmapsdk.style.expressions.Expression.get;
import static com.nbmap.nbmapsdk.style.expressions.Expression.literal;
import static com.nbmap.nbmapsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.iconAnchor;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.iconImage;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.iconOffset;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.iconSize;

import android.graphics.Bitmap;
import android.view.View;

import com.nbmap.geojson.Feature;
import com.nbmap.geojson.Point;
import com.nbmap.nbmapsdk.geometry.LatLng;
import com.nbmap.nbmapsdk.maps.Style;
import com.nbmap.nbmapsdk.style.layers.Layer;
import com.nbmap.nbmapsdk.style.layers.SymbolLayer;
import com.nbmap.nbmapsdk.style.sources.GeoJsonOptions;
import com.nbmap.nbmapsdk.style.sources.GeoJsonSource;

import ai.nextbillion.utils.SymbolGenerator;

public class InfoWindowSymbol {
    public static final String SYMBOL_ID = "symbol-id";
    private static final String SYMBOL_SELECTED = "Symbol-selected";

    private GeoJsonSource locationSource;
    private Layer infoWindowLayer;
    private Layer symbolLayer;
    private final String symbolId, sourceId, infoWindowLayerId, symbolLayerId;
    private boolean isSelected = false;
    private Feature feature;


    public InfoWindowSymbol(String symbolId) {
        this.symbolId = symbolId;
        sourceId = symbolId + "-source";
        infoWindowLayerId = symbolId + "-info-window-layer";
        symbolLayerId = symbolId + "-symbol-layer";
    }

    public String getSymbolId() {
        return symbolId;
    }

    public void init(Style style, String iconId) {
        GeoJsonOptions jsonOptions = new GeoJsonOptions().withMaxZoom(18);
        locationSource = new GeoJsonSource(sourceId, jsonOptions);
        style.addSource(locationSource);
        symbolLayer = new SymbolLayer(symbolLayerId, sourceId).withProperties(
                iconImage(iconId),
                iconAnchor(ICON_ANCHOR_BOTTOM),
                iconAllowOverlap(true),
                iconOffset(new Float[]{1f, 15f}),
                iconSize(0.5f)
        );
        style.addLayer(symbolLayer);
        infoWindowLayer = new SymbolLayer(infoWindowLayerId, sourceId).withProperties(
                iconImage("{symbol-id}"),
                iconAnchor(ICON_ANCHOR_BOTTOM),
                /* all info window and marker image to appear at the same time*/
                iconAllowOverlap(true),
                /* offset the info window to be above the marker */
                iconOffset(new Float[]{0f, -50f})
        ).withFilter(eq((get(SYMBOL_SELECTED)), literal(true)));
        style.addLayer(infoWindowLayer);
    }

    public void update(LatLng latLng) {
        feature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
        feature.addBooleanProperty(SYMBOL_SELECTED, isSelected);
        feature.addStringProperty(SYMBOL_ID, symbolId);
        locationSource.setGeoJson(feature);
    }

    public void onClick() {
        if (feature != null) {
            isSelected = !isSelected;
            feature.addBooleanProperty(SYMBOL_SELECTED, isSelected);
            locationSource.setGeoJson(feature);
        }
    }

    public void addInfoWindow(Style style, View view) {
        Bitmap infoWindow = SymbolGenerator.generate(view);
        style.addImage(symbolId, infoWindow);
    }
}
