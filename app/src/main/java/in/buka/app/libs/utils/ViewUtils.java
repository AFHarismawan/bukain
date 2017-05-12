package in.buka.app.libs.utils;

import android.content.Context;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ViewUtils {

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidthDp( Context context) {
        return context.getResources().getConfiguration().screenWidthDp;
    }
}
