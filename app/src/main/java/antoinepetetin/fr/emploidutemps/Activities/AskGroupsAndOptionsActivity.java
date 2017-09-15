package antoinepetetin.fr.emploidutemps.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import antoinepetetin.fr.emploidutemps.Adapters.CheckboxesAdapter;
import antoinepetetin.fr.emploidutemps.DAO.EventsDataSource;
import antoinepetetin.fr.emploidutemps.GroupSharedPreferences;
import antoinepetetin.fr.emploidutemps.InternetInfo;
import antoinepetetin.fr.emploidutemps.Models.Event;
import antoinepetetin.fr.emploidutemps.Models.GroupsAndOptions;
import antoinepetetin.fr.emploidutemps.R;
import antoinepetetin.fr.emploidutemps.WebService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AskGroupsAndOptionsActivity extends AppCompatActivity {
    private ListView listViewGroups;
    private ListView listViewOptions;
    private List<Integer> selectedGroupsAndOptions;
    private static Menu menu;

    @Override
    public void onBackPressed() {}

    private void hook(){
        listViewGroups = (ListView)findViewById(R.id.listViewGroups);
        listViewOptions = (ListView)findViewById(R.id.listViewOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_groups_and_options);
        hook();
        selectedGroupsAndOptions = new ArrayList<>();

        //Si on a une connexion Internet
        if(InternetInfo.isConnected(this))
        {

            WebService service = WebService.retrofit.create(WebService.class);
            Call<GroupsAndOptions> call = service.getGroupsAndOptions();
            call.enqueue(new Callback<GroupsAndOptions>() {
                @Override
                public void onResponse(Response<GroupsAndOptions> response, Retrofit retrofit) {
                    //Une fois la liste des groupes et options récupérés, il faut les afficher sur la listView
                    displayGroupsAndOptions(response.body());

                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(),"Echec de récupération des groupes ! "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }else //Si on n'a pas de connexion Internet
        {
            setContentView(R.layout.no_connection);
        }
    }

    //Affiche la liste des groupes et des options dynamiquement
    private void displayGroupsAndOptions(GroupsAndOptions groupsAndOptions){
        CheckboxesAdapter dataAdapter = null;

        //On créé l'adapter pour les groupes
        dataAdapter = new CheckboxesAdapter(this,R.layout.group_info,groupsAndOptions.getGroupes(),selectedGroupsAndOptions);
        // On l'assigne à la listView Groups
        listViewGroups.setAdapter(dataAdapter);

        //ET ON DOIT FAIRE LA MEME CHOSE AVEC LES OPTIONS
        //On créé l'adapter pour les groupes
        dataAdapter = new CheckboxesAdapter(this,R.layout.group_info,groupsAndOptions.getOptions(),selectedGroupsAndOptions);
        // On l'assigne à la listView Groups
        listViewOptions.setAdapter(dataAdapter);

        //Active le bouton ok
        getMenuInflater().inflate(R.menu.menu_ask_groups_options, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(selectedGroupsAndOptions.isEmpty())
            Toast.makeText(getApplicationContext(),"Veuillez sélectionner au moins au groupe ou une option !",Toast.LENGTH_LONG).show();
        else{
            GroupSharedPreferences.saveGroup(getApplicationContext(),selectedGroupsAndOptions);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
