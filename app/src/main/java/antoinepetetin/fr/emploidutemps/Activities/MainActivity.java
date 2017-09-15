package antoinepetetin.fr.emploidutemps.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

import antoinepetetin.fr.emploidutemps.DAO.EventsDataSource;
import antoinepetetin.fr.emploidutemps.GroupSharedPreferences;
import antoinepetetin.fr.emploidutemps.InternetInfo;
import antoinepetetin.fr.emploidutemps.Models.Event;
import antoinepetetin.fr.emploidutemps.R;
import antoinepetetin.fr.emploidutemps.WebService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private WeekView weekView;
    private ProgressBar progressBar;
    private List<Event> eventList;
    private boolean ok = false;

    @Override
    public void onBackPressed() {}

    private void hook(){
        weekView = (WeekView)findViewById(R.id.weekView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //On implémente le listener avec une liste vide car la librairie nous oblige d'implémenter ce listener
        //Problème de tâche asynchrone !
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                ok = true;
                return new ArrayList<>();
            }
        });
        weekView.goToHour(8);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hook();


        String groupsUrl = GroupSharedPreferences.getGroups(this);
        //Pour la première utilisation de l'appli
        if(groupsUrl=="")
        {
            Intent intent = new Intent(getApplicationContext(),AskGroupsAndOptionsActivity.class);
            startActivity(intent);
        }else{//Si on a déjà sélectionné les groupes et/ou options
            eventList = new ArrayList<>();

            if(InternetInfo.isConnected(this)) {
                //On fait un appel au WS de manière synchrone
                WebService service = WebService.retrofit.create(WebService.class);
                Call<List<Event>> call = service.getEvents(GroupSharedPreferences.getGroups(getApplicationContext()));
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(final Response<List<Event>> response, Retrofit retrofit) {
                        eventList = response.body();
                        //On fait disparaitre la progressBar
                        progressBar.setVisibility(View.GONE);
                        if(ok)
                        {
                            weekView.notifyDatasetChanged();
                            ok=false;
                        }

                        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
                            @Override
                            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                                List<WeekViewEvent> resultat = Event.getWeekViewEvents(getApplicationContext(),eventList,newMonth,newYear);
                                return resultat;
                            }
                        });

                        //Enregistrement dans la base de données seulement si la liste n'est pas vide
                        if(eventList.isEmpty())
                            Toast.makeText(getApplicationContext(),"Aucun cours à afficher !", Toast.LENGTH_LONG).show();
                        else
                        {
                            EventsDataSource eventsDataSource = new EventsDataSource(getApplicationContext());
                            long nbEventsSaved = eventsDataSource.saveAll(eventList);
                            if(nbEventsSaved>0)
                                Toast.makeText(getApplicationContext(), "Les cours ont été affiché et sauvegardé avec succès !", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"Les cours n'ont pas été sauvegardé !",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        //On va chercher dans la base de données
                        EventsDataSource eventsDataSource = new EventsDataSource(getApplicationContext());
                        eventList = eventsDataSource.getAllEvents();

                        if(eventList.isEmpty())
                            Toast.makeText(getApplicationContext(),"Aucun cours à afficher !",Toast.LENGTH_LONG).show();

                        if(ok)
                        {
                            weekView.notifyDatasetChanged();
                            ok=false;
                        }

                        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
                            @Override
                            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                                return Event.getWeekViewEvents(getApplicationContext(),eventList,newMonth,newYear);
                            }
                        });
                        Toast.makeText(getApplicationContext(),"Le serveur distant ne répond pas !",Toast.LENGTH_LONG).show();
                    }
                });

            }else{
                progressBar.setVisibility(View.GONE);
                //On va chercher dans la base de données
                EventsDataSource eventsDataSource = new EventsDataSource(getApplicationContext());
                eventList = eventsDataSource.getAllEvents();

                if(eventList.isEmpty())
                    Toast.makeText(getApplicationContext(),"Aucun cours à afficher !",Toast.LENGTH_LONG).show();

                if(ok)
                {
                    weekView.notifyDatasetChanged();
                    ok=false;
                }

                weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
                    @Override
                    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                        return Event.getWeekViewEvents(getApplicationContext(),eventList,newMonth,newYear);
                    }
                });

            }
        }


    }
}
