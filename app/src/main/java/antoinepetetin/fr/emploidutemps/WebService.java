package antoinepetetin.fr.emploidutemps;

import com.squareup.okhttp.ResponseBody;

import java.util.List;

import antoinepetetin.fr.emploidutemps.Models.Event;
import antoinepetetin.fr.emploidutemps.Models.GroupsAndOptions;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by antoine on 17/12/16.
 */

public interface WebService {
    @GET("ade/groups")
    Call<GroupsAndOptions> getGroupsAndOptions();
    @GET("adev2/cours.txt")
    Call<List<Event>> getEvents(@Query("ids") String ids);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://antoinepetetin.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
