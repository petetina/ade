package antoinepetetin.fr.emploidutemps;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by antoine on 17/12/16.
 */

public abstract class InternetInfo {
    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isMobileConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return isConnected(context) && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isWifiConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return isConnected(context) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }
}
