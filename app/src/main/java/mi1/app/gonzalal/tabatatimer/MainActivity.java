package mi1.app.gonzalal.tabatatimer;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import mi1.app.gonzalal.tabatatimer.data.Tabata;

import static mi1.app.gonzalal.tabatatimer.R.id.preparePlus;

public class MainActivity extends AppCompatActivity{

    //VIEWS
    private TextView prepareTimer;
    private TextView workTimer;
    private TextView restTimer;
    private TextView cycleTimer;
    private TextView tabataTimer;

    //DATA
    Tabata tabata = new Tabata();

    public static final String PREPARE_VALUE = "prepareValue";
    public static final String WORK_VALUE = "workValue";
    public static final String REST_VALUE = "restValue";
    public static final String CYCLE_VALUE = "cycleValue";
    public static final String TABATA_VALUE = "tabataValue";
    public static final String TABATA_TIMER_OBJECT = "tabata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupération des vues
        prepareTimer = (TextView) findViewById(R.id.prepareTimer);
        workTimer = (TextView) findViewById(R.id.workTimer);
        restTimer = (TextView) findViewById(R.id.restTimer);
        cycleTimer = (TextView) findViewById(R.id.cycleTimer);
        tabataTimer = (TextView) findViewById(R.id.tabataTimer);

        //restore l'activité en cas de retournement du smartphone
        if (savedInstanceState != null) {

            tabata = savedInstanceState.getParcelable(TABATA_TIMER_OBJECT);
        }

        //affiche les valeurs des timers par défaut
        initViews();

        //enregistre les valeurs des timers dans l'objet Tabata
        recordValue();
    }

    /*permet de modifier les valeurs max des timers de prepare, work, rest, le nombre de cycles work/rest
    et le nombre de tabatas */


   public void onChangeValue(View view) {

        switch (view.getId()) {

            case preparePlus:

                tabata.addValueTimerPrepare();
                prepareTimer.setText(String.valueOf(tabata.getValueTimerPrepare()));

                break;

            case R.id.prepareMinus:

                tabata.remValueTimerPrepare();
                prepareTimer.setText(String.valueOf(tabata.getValueTimerPrepare()));

                break;

            case R.id.workPlus:

                tabata.addValueTimerWork();
                workTimer.setText(String.valueOf(tabata.getValueTimerWork()));

                break;

            case R.id.workMinus:

                tabata.remValueTimerWork();
                workTimer.setText(String.valueOf(tabata.getValueTimerWork()));

                break;

            case R.id.restPlus:

                tabata.addValueTimerRest();
                restTimer.setText(String.valueOf(tabata.getValueTimerRest()));

                break;

            case R.id.restMinus:

                tabata.remValueTimerRest();
                restTimer.setText(String.valueOf(tabata.getValueTimerRest()));

                break;

            case R.id.cyclePlus:

                tabata.addValueCycle();
                cycleTimer.setText(String.valueOf(tabata.getValueCycle()));

                break;

            case R.id.cycleMinus:

                tabata.remValueCycle();
                cycleTimer.setText(String.valueOf(tabata.getValueCycle()));

                break;

            case R.id.tabataPlus:

                tabata.addValueTabata();
                tabataTimer.setText(String.valueOf(tabata.getValueTabata()));

                break;

            case R.id.tabataMinus:

                tabata.remValueTabata();
                tabataTimer.setText(String.valueOf(tabata.getValueTabata()));

                break;
        }
    }

    public void putExtraValue(Intent intent){

        intent.putExtra(PREPARE_VALUE, tabata.getValueTimerPrepare());
        intent.putExtra(WORK_VALUE, tabata.getValueTimerWork());
        intent.putExtra(REST_VALUE, tabata.getValueTimerRest());
        intent.putExtra(CYCLE_VALUE, tabata.getValueCycle());
        intent.putExtra(TABATA_VALUE, tabata.getValueTabata());

    }

    //Demande d'intention au clic du bouton "start" vers l'activité TabataActivity au clic
    public void onTabataActivity(View view) {

        // Création d'une intention pour passer sur TabataActivity
        Intent intent = new Intent(this, TabataActivity.class);

        putExtraValue(intent);

        /*//On ajoute les paramètres à l'intention
        intent.putExtra(PREPARE_VALUE, tabata.getValueTimerPrepare());
        intent.putExtra(WORK_VALUE, tabata.getValueTimerWork());
        intent.putExtra(REST_VALUE, tabata.getValueTimerRest());
        intent.putExtra(CYCLE_VALUE, tabata.getValueCycle());
        intent.putExtra(TABATA_VALUE, tabata.getValueTabata());*/

        //on lance l'intention
        startActivity(intent);

    }

    //Récupération des valeurs définies dans l'activité ListTabataActivity ou par défaut
    public void initViews(){

        prepareTimer.setText(String.valueOf(getIntent().getLongExtra(ListTabataActivity.PREPARE_VALUE, tabata.getValueTimerPrepare())));
        workTimer.setText(String.valueOf(getIntent().getLongExtra(ListTabataActivity.WORK_VALUE, tabata.getValueTimerWork())));
        restTimer.setText(String.valueOf(getIntent().getLongExtra(ListTabataActivity.REST_VALUE, tabata.getValueTimerRest())));
        cycleTimer.setText(String.valueOf(getIntent().getLongExtra(ListTabataActivity.CYCLE_VALUE, tabata.getValueCycle())));
        tabataTimer.setText(String.valueOf(getIntent().getLongExtra(ListTabataActivity.TABATA_VALUE, tabata.getValueTabata())));
    }

    // Enregistre les valeurs dans l'objet Tabata
    public void recordValue(){

        tabata.setValueTimerPrepare(Long.valueOf(prepareTimer.getText().toString()));
        tabata.setValueTimerWork(Long.valueOf(workTimer.getText().toString()));
        tabata.setValueTimerRest(Long.valueOf(restTimer.getText().toString()));
        tabata.setValueCycle(Long.valueOf(cycleTimer.getText().toString()));
        tabata.setValueTabata(Long.valueOf(tabataTimer.getText().toString()));
    }

    public void onCreateTabataActivity(View view) {

        // Création d'une intention pour passer sur TabataActivity
        Intent intent = new Intent(this, CreateTabataActivity.class);

        putExtraValue(intent);

        //on lance l'intention
        startActivity(intent);
    }

    public void onListTabataActivity(View view) {

        // Création d'une intention pour passer sur TabataActivity
        Intent intent = new Intent(this, ListTabataActivity.class);

        //on lance l'intention
        startActivity(intent);
    }

    // sauvegarde les données en cas de retournement du smartphone
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(TABATA_TIMER_OBJECT, tabata);

    }
}






