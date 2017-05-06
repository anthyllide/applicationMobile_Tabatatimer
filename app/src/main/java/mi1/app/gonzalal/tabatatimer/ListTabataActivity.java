package mi1.app.gonzalal.tabatatimer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import mi1.app.gonzalal.tabatatimer.data.Tabata;
import mi1.app.gonzalal.tabatatimer.data.TabataDAO;

public class ListTabataActivity extends AppCompatActivity {

    //VIEW
    private ListView listView;

    //CONSTANTS
    public static final String PREPARE_VALUE = "prepareValue";
    public static final String WORK_VALUE = "workValue";
    public static final String REST_VALUE = "restValue";
    public static final String CYCLE_VALUE = "cycleValue";
    public static final String TABATA_VALUE = "tabataValue";

    // Récupérer la liste des tabata dans la base de données
    final List<Tabata> tabataList = TabataDAO.selectAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tabata);

        // Récupérer l'objet graphique de type ListView
        listView = (ListView) findViewById(R.id.list_item);

        ArrayAdapter<Tabata> adapter = new ArrayAdapter<Tabata>(this, R.layout.activity_list_tabata_row, tabataList) {
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                // Inflate LAYOUT
                if(convertView == null){

                    LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = (ViewGroup)inflater.inflate(R.layout.activity_list_tabata_row, null);

                }

                // VIEW
                TextView text1 = (TextView) convertView.findViewById(R.id.text1);
                TextView text2 = (TextView) convertView.findViewById(R.id.text2);
                TextView text3 = (TextView) convertView.findViewById(R.id.text3);
                TextView text4 = (TextView) convertView.findViewById(R.id.text4);
                TextView text5 = (TextView) convertView.findViewById(R.id.text5);
                TextView text6 = (TextView) convertView.findViewById(R.id.text6);

                //BUTTON

                Button useButton = (Button) convertView.findViewById(R.id.useButton);
                Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);


                // Charger la vue avec les données
                Tabata record = tabataList.get(position);
                text1.setText(record.getNomTabata());
                text2.setText(getString(R.string.prepareTime)+" "+String.valueOf(record.getValueTimerPrepare()+" "+getString(R.string.seconde)));
                text3.setText(String.valueOf(getString(R.string.workTime)+" "+record.getValueTimerWork()+" "+getString(R.string.seconde)));
                text4.setText(String.valueOf(getString(R.string.restTime)+" "+record.getValueTimerRest()+" "+getString(R.string.seconde)));
                text5.setText(String.valueOf(getString(R.string.cycleNumber)+" "+record.getValueCycle()));
                text6.setText(String.valueOf(getString(R.string.tabataNumber)+" "+record.getValueTabata()));

                //Transmet les valeurs du tabata à MainActivity
                useButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(ListTabataActivity.this, MainActivity.class);

                        Tabata focusTabata = tabataList.get(position);

                        //On ajoute les paramètres à l'intention
                        intent.putExtra(PREPARE_VALUE, focusTabata.getValueTimerPrepare());
                        intent.putExtra(WORK_VALUE, focusTabata.getValueTimerWork());
                        intent.putExtra(REST_VALUE, focusTabata.getValueTimerRest());
                        intent.putExtra(CYCLE_VALUE, focusTabata.getValueCycle());
                        intent.putExtra(TABATA_VALUE, focusTabata.getValueTabata());

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        //on lance l'intention
                        startActivity(intent);
                    }

                });

                // Supprime l'item concerné de la base de données
                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        Tabata focusTabata = tabataList.get(position);
                        focusTabata.delete();
                        overridePendingTransition(10, 0);
                        recreate();//Mise à jour de l'activité après la supression de l'item
                    }

                });

                // retourne la view adaptée
                return convertView;
            }

        };

        listView.setAdapter(adapter);
    }

}
