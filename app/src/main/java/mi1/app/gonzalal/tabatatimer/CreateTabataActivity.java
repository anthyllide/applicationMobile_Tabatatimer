package mi1.app.gonzalal.tabatatimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mi1.app.gonzalal.tabatatimer.data.Tabata;

public class CreateTabataActivity extends AppCompatActivity {

    //VIEWS
    private EditText tabataName;
    private TextView prepareTimer;
    private TextView workTimer;
    private TextView restTimer;
    private TextView cycleTimer;
    private TextView tabataTimer;

    //DATA
    Tabata tabata = new Tabata();

    public static final String TABATA_TIMER_OBJECT = "tabata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tabata);

        //Récupération des vues
        tabataName = (EditText)  findViewById(R.id.tabataName);
        prepareTimer = (TextView) findViewById(R.id.prepareTimer);
        workTimer = (TextView) findViewById(R.id.workTimer);
        restTimer = (TextView) findViewById(R.id.restTimer);
        cycleTimer = (TextView) findViewById(R.id.cycleTimer);
        tabataTimer = (TextView) findViewById(R.id.tabataTimer);


        //restore l'activité en cas de retournement du smartphone
        if (savedInstanceState != null) {

            tabata = savedInstanceState.getParcelable(TABATA_TIMER_OBJECT);
        }

        initViews();
        setValue();

    }

    //Récupération des valeurs définies dans l'activité ListTabataActivity ou par défaut
    public void initViews(){

        prepareTimer.setText(String.valueOf(getIntent().getLongExtra(MainActivity.PREPARE_VALUE, tabata.getValueTimerPrepare())));
        workTimer.setText(String.valueOf(getIntent().getLongExtra(MainActivity.WORK_VALUE, tabata.getValueTimerWork())));
        restTimer.setText(String.valueOf(getIntent().getLongExtra(MainActivity.REST_VALUE, tabata.getValueTimerRest())));
        cycleTimer.setText(String.valueOf(getIntent().getLongExtra(MainActivity.CYCLE_VALUE, tabata.getValueCycle())));
        tabataTimer.setText(String.valueOf(getIntent().getLongExtra(MainActivity.TABATA_VALUE, tabata.getValueTabata())));
    }

    public void addTabata(View view) {

        //on ajoute le nom du tabata saisi par l'utilisateur
        String nomTabata = tabataName.getText().toString();

        tabata.setNomTabata(nomTabata);

        //on sauve dans la BDD les données saisies par l'utilisateur
        tabata.save();

        Intent intent = new Intent(CreateTabataActivity.this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //on lance l'intention
        startActivity(intent);
    }

    //permet d'enregistrer les valeurs dans l'objet Tabata
    public void setValue(){

        tabata.setValueTimerPrepare((long) Integer.parseInt (prepareTimer.getText().toString()));
        tabata.setValueTimerWork((long) Integer.parseInt (workTimer.getText().toString()));
        tabata.setValueTimerRest((long) Integer.parseInt (restTimer.getText().toString()));
        tabata.setValueCycle((long) Integer.parseInt (cycleTimer.getText().toString()));
        tabata.setValueTabata((long) Integer.parseInt (tabataTimer.getText().toString()));

    }


    /*permet de modifier les valeurs max des timers de prepare, work, rest, le nombre de cycles work/rest
    et le nombre de tabatas */

    public void onChangeValue(View view) {

        switch (view.getId()) {

            case R.id.preparePlus:

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

    // sauvegarde les données en cas de retournement du smartphone
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(TABATA_TIMER_OBJECT, tabata);

    }
}
