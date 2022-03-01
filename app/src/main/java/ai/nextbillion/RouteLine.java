package ai.nextbillion;

import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.lineCap;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.lineColor;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.lineJoin;
import static com.nbmap.nbmapsdk.style.layers.PropertyFactory.lineWidth;

import com.nbmap.core.constants.Constants;
import com.nbmap.geojson.Feature;
import com.nbmap.geojson.LineString;
import com.nbmap.geojson.Point;
import com.nbmap.nbmapsdk.maps.Style;
import com.nbmap.nbmapsdk.style.layers.Layer;
import com.nbmap.nbmapsdk.style.layers.LineLayer;
import com.nbmap.nbmapsdk.style.layers.Property;
import com.nbmap.nbmapsdk.style.sources.GeoJsonOptions;
import com.nbmap.nbmapsdk.style.sources.GeoJsonSource;

import java.util.List;

public class RouteLine {

    private String routeLineId;

    private String routeLineSourceId;
    private String routeLineLayerId;

    private GeoJsonSource routeSource;
    private Layer routeLayer;


    private float lineWidth = 2.0f;
    private String lineColor = "#70DF70";
    private String lineCap = Property.LINE_CAP_ROUND;
    private String lineJoin = Property.LINE_JOIN_ROUND;

    public RouteLine(Style style, String routeLineId) {
        this.routeLineId = routeLineId;
        routeLineSourceId = routeLineId + "_source";
        routeLineLayerId = routeLineId + "_layer";
        init(style);
    }

    private void init(Style style) {
        style.removeLayer(routeLineLayerId);
        style.removeSource(routeLineSourceId);
        GeoJsonOptions routeLineGeoJsonOptions = new GeoJsonOptions().withMaxZoom(16);
        routeSource = new GeoJsonSource(routeLineSourceId, routeLineGeoJsonOptions);
        routeLayer = new LineLayer(routeLineLayerId, routeLineSourceId).withProperties(
                lineWidth(lineWidth),
                lineColor(lineColor),
                lineCap(lineCap),
                lineJoin(lineJoin)
        );
        style.addSource(routeSource);
        style.addLayer(routeLayer);
    }

    public void drawRouteLine(String geometry) {
        routeSource.setGeoJson(generateRouteLineFeature(geometry, Constants.PRECISION_5));
    }

    private Feature generateRouteLineFeature(String geometry, int precision) {
        LineString routeGeometry = LineString.fromPolyline(geometry, precision);
        return Feature.fromGeometry(routeGeometry);
    }

    public void updateRoute(List<Point> newRoute) {
        LineString lineString = LineString.fromLngLats(newRoute);
        Feature routeFeature = Feature.fromGeometry(lineString);
        this.routeSource.setGeoJson(routeFeature);
    }
}
