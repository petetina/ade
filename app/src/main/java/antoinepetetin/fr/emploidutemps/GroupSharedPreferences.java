package antoinepetetin.fr.emploidutemps;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by antoine on 17/12/16.
 */

public class GroupSharedPreferences {
    private static final String PREFS_NAME = "MyPrefsFile";

    public static String getGroups(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("groups","");//valeur par défaut
    }
    public static boolean saveGroup(Context context, List<Integer> groups){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("groups",getIdResourcesUrl(groups));

        // Commit the edits!
        editor.commit();

        return !getGroups(context).isEmpty();
    }

    private static String getIdResourcesUrl(List<Integer> idGroupes){
        if(idGroupes.isEmpty())
            return "";
        else
        {
            String resultat = "";
            for(int i=0; i<idGroupes.size()-1; i++)
                resultat += Integer.toString(idGroupes.get(i))+",";
            resultat+=idGroupes.get(idGroupes.size()-1)+"";
            return resultat;
        }

    }
}
