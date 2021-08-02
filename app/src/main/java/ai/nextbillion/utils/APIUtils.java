package ai.nextbillion.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class APIUtils {
    public static final String BASE_URL = "https://api.nextbillion.io";
    private static String baseUrl;

    public static String getBaseUrl(@NonNull Context context) {
        if (baseUrl != null) {
            return baseUrl;
        }

        int tokenResId = context.getResources()
                .getIdentifier("nbmap_base_url", "string", context.getPackageName());
        if (tokenResId != 0)
            baseUrl = context.getString(tokenResId);

        if (baseUrl != null) {
            return baseUrl;
        }

        TypedValue typedValue = new TypedValue();
        ((Application) context).getTheme().resolveAttribute(com.nbmap.nbmapsdk.R.attr.nbmap_apiBaseUri, typedValue, true);
        int[] urlAttr = new int[]{com.nbmap.nbmapsdk.R.attr.nbmap_apiBaseUri};
        TypedArray array = context.obtainStyledAttributes(typedValue.data, urlAttr);
        baseUrl = array.getString(0);
        array.recycle();
        if (baseUrl == null) {
            baseUrl = BASE_URL;
        }
        return baseUrl;
    }
}
