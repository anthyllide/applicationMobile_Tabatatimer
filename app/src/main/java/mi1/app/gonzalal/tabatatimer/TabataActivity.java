package mi1.app.gonzalal.tabatatimer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

/**
 * Created by jiyou on 07/10/2016.
 */

public class TabataActivity  extends Activity {

    //LAYOUT
    private RelativeLayout layout;

    // VIEWS
    private Button startButton;
    private Button stopButton;
    private TextView timerValue;
    private TextView tabataCycle;
    private TextView tabataTitle;


    // DATA
    public long updatedTime = 0;
    private CountDownTimer timer;
    public long prepareMaxTimer = 0;
    public long workMaxTimer = 0;
    public long restMaxTimer = 0;
    public long cycleMaxNb = 0;
    public long cycleMax =0;
    public long tabataMaxNb = 0;
    public long tabataCycleTransmis;
    public int i=1; //permet d'alterner un timer WORK/REST

    // CONSTANTS
    public final static String TABATA_VALUE = "tabataValue";
    public final static String CYCLE_MAX = "cycleMax";
    public final static String PREPARE_VALUE = "prepareValue";
    public final static String WORK_VALUE = "workValue";
    public final static String REST_VALUE = "restValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.son);//Ajout d'un mediaplayer
        mediaPlayer.start();//Démarre le bruitage

        setContentView(R.layout.activity_tabata);

        //Récupérer le layout
        layout = (RelativeLayout) findViewById(R.id.activity_tabata);


        // Récupérer les views
        startButton = (Button) findViewById(R.id.playButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        timerValue = (TextView) findViewById(R.id.timerValue);
        tabataCycle = (TextView) findViewById(R.id.tabataCycle);
        tabataTitle = (TextView) findViewById(R.id.tabataTitle);

        //Récupération des valeurs définies dans l'activité précédente
        getIntentValues();

        // Initialisation de la variable updatedTime (on commence par prepareMaxTimer)
        updatedTime = prepareMaxTimer;

        //Récupération des valeurs après la relance de l'activité
        if (cycleMaxNb == 0){

            getValuesStateTabata();

            // Initialisation de la variable uptedTime (on commence par prepareMaxTimer)
            updatedTime = prepareMaxTimer;

        }

        // Récupération de la valeur tabataCycleTransmis
        tabataCycleTransmis = getIntent().getLongExtra(TabataActivity.TABATA_VALUE, tabataMaxNb);

        // Mise à jour de la variable tabataMaxNb
        tabataMaxNb = tabataCycleTransmis;

        //si tous les cycles de tababa sont passés, alors on arrête le timer
        if (tabataCycleTransmis == 0) {

            tabataTitle.setText(getString(R.string.finish));
            timerValue.setTextSize(40);
            timerValue.setText(getString(R.string.good));

            // affiche l'écran de fin pendant 5s, puis retour à la MainActivity
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(TabataActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, secondsDelayed * 5000);

        } else {

            //Mise à jour du timer
            miseAJour();

            // lancement du timer à la création de l'activité

            launchTimer();
        }
    }

    public void launchTimer() {

        timer = new CountDownTimer(updatedTime, 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();
            }

            public void onFinish() {
                updatedTime = 0;
                miseAJour();

                if (cycleMaxNb > 1 && i == 1) {

                    tabataCycle.setText(String.valueOf(1+(cycleMax-cycleMaxNb))+"/"+String.valueOf(cycleMax));
                    tabataTitle.setText(getString(R.string.work));
                    layout.setBackgroundColor(Color.parseColor("#DC0A0A"));
                    i--;
                    updatedTime = workMaxTimer;
                    launchTimer();

                } else if (cycleMaxNb > 1 && i == 0) {

                    tabataCycle.setText(String.valueOf(1+(cycleMax-cycleMaxNb))+"/"+String.valueOf(cycleMax));
                    tabataTitle.setText(getString(R.string.rest));
                    layout.setBackgroundColor(Color.parseColor("#73DC0A"));
                    i=1;
                    cycleMaxNb--;
                    updatedTime = restMaxTimer;
                    launchTimer();

                } else if (cycleMaxNb == 1) {

                    tabataCycle.setText(String.valueOf(1+(cycleMax-cycleMaxNb))+"/"+String.valueOf(cycleMax));
                    tabataTitle.setText(getString(R.string.work));
                    layout.setBackgroundColor(Color.parseColor("#DC0A0A"));
                    cycleMaxNb--;
                    updatedTime = workMaxTimer;
                    launchTimer();

                } else if (cycleMaxNb == 0 && tabataMaxNb > 0){

                    tabataMaxNb--;
                    reload();
                }
            }

        }.start();

    }

    // Lancement du timer
    public void onStart(View view) {

        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);

        launchTimer();

    }

    // Mise en pause du timer
    public void onPause(View view) {
        if (timer != null) {

            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);

            timer.cancel();
        }
    }

    // Mise à jour graphique
    private void miseAJour() {

        // Décompositions en secondes et minutes
        int secs = (int) (updatedTime / 1000);
        secs = secs % 60;

        // Affichage du "timer"
        timerValue.setText(String.format("%2d", secs));
    }

    //Relance l'activité après un tabata et transmet le nombre de tabata restant
    public void reload() {

        Intent intent = new Intent (this, TabataActivity.class);
        intent.putExtra(TABATA_VALUE, tabataMaxNb); //transmission des données
        intent.putExtra(CYCLE_MAX, cycleMax);
        intent.putExtra(PREPARE_VALUE, prepareMaxTimer);
        intent.putExtra(WORK_VALUE, workMaxTimer);
        intent.putExtra(REST_VALUE, restMaxTimer);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(0, 0);
        startActivity(intent); //relance l'activité
        finish(); //Termine l'activité
    }

    //Récupération des valeurs définies dans l'activité précédente
    public void getIntentValues(){

        prepareMaxTimer = (getIntent().getLongExtra(MainActivity.PREPARE_VALUE, 0)) * 1000;
        workMaxTimer = (getIntent().getLongExtra(MainActivity.WORK_VALUE, 0)) * 1000;
        restMaxTimer = (getIntent().getLongExtra(MainActivity.REST_VALUE, 0)) * 1000;
        cycleMaxNb = getIntent().getLongExtra(MainActivity.CYCLE_VALUE, 0);
        cycleMax = getIntent().getLongExtra(MainActivity.CYCLE_VALUE, 0);
        tabataMaxNb = getIntent().getLongExtra(MainActivity.TABATA_VALUE, 0);
    }

    public void getValuesStateTabata(){

        cycleMaxNb = getIntent().getLongExtra(TabataActivity.CYCLE_MAX, cycleMaxNb);
        cycleMax = getIntent().getLongExtra(TabataActivity.CYCLE_MAX, cycleMaxNb);
        prepareMaxTimer = getIntent().getLongExtra(TabataActivity.PREPARE_VALUE, prepareMaxTimer);
        workMaxTimer = getIntent().getLongExtra(TabataActivity.WORK_VALUE, workMaxTimer);
        restMaxTimer = getIntent().getLongExtra(TabataActivity.REST_VALUE, restMaxTimer);
    }

    // Stoppe le timer quand l'utilisateur appuie sur le bouton back
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            timer.cancel();
        }

        return super.onKeyDown(keyCode, event);
    }

}
