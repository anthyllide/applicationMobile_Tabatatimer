package mi1.app.gonzalal.tabatatimer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by gonzalal on 17/10/16.
 */

public class Tabata extends SugarRecord implements Parcelable {

    private long valueTimerPrepare = 10;
    private long valueTimerWork = 10;
    private long valueTimerRest = 10;
    private long valueCycle = 3;
    private long valueTabata = 1;
    private String nomTabata;

    //////////////CONSTRUCTEURS//////////////////
    public Tabata(){

    }

    public Tabata(long valueTimerPrepare, long valueTimerWork, long valueTimerRest, long valueCycle, long valueTabata, String nomTabata) {
        setValueTimerPrepare(valueTimerPrepare);
        setValueTimerWork(valueTimerWork);
        setValueTimerRest(valueTimerRest);
        setValueCycle(valueCycle);
        setValueTabata(valueTabata);
        setNomTabata(nomTabata);
    }

    /////////////////GETTERS//////////////////

    public long getValueTimerPrepare(){
        return valueTimerPrepare;
    }

    public long getValueTimerWork(){
        return valueTimerWork;
    }

    public long getValueTimerRest(){
        return valueTimerRest;
    }

    public long getValueCycle(){
        return valueCycle;
    }

    public long getValueTabata(){
        return valueTabata;
    }

    public String getNomTabata() { return nomTabata; }

    ////////////////////////////////////////////

    /////////////////SETTERS///////////////////

    public void setValueTimerPrepare(long valueTimerPrepare) {
        this.valueTimerPrepare = valueTimerPrepare;
    }

    public void setValueTimerWork(long valueTimerWork) {
        this.valueTimerWork = valueTimerWork;
    }

    public void setValueTimerRest(long valueTimerRest) {
        this.valueTimerRest = valueTimerRest;
    }

    public void setValueCycle(long valueCycle) {
        this.valueCycle = valueCycle;
    }

    public void setValueTabata(long valueTabata) {
        this.valueTabata = valueTabata;
    }

    public void setNomTabata(String nomTabata) { this.nomTabata = nomTabata; }

    //////////////////////////////////////////

    public void addValueTimerPrepare(){

        if(valueTimerPrepare < 60 ){
            valueTimerPrepare++;
        }

    }

    public void remValueTimerPrepare(){
        if(valueTimerPrepare > 1){
            valueTimerPrepare--;
        }

    }

    //////////////////////////////////

    public void addValueTimerWork(){

        if(valueTimerWork < 60 ){
            valueTimerWork++;
        }

    }

    public void remValueTimerWork(){
        if(valueTimerWork > 1){
            valueTimerWork--;
        }

    }

    ////////////////////////////////

    public void addValueTimerRest(){

        if(valueTimerRest < 60 ){
            valueTimerRest++;
        }

    }

    public void remValueTimerRest(){
        if(valueTimerRest > 1){
            valueTimerRest--;
        }

    }

    /////////////////////////////////

    public void addValueCycle(){
        if (valueCycle < 20){
            valueCycle++;
        }
    }

    public void remValueCycle(){
        if(valueCycle > 1){
            valueCycle--;
        }
    }

    ////////////////////////////////

    public void addValueTabata() {
        if (valueTabata < 10) {
            valueTabata++;
        }
    }

    public void remValueTabata(){
        if(valueTabata > 1){
            valueTabata--;
        }
    }

    //////////////////////////
    // Implémentation du Parcelable

    public Tabata(Parcel in) {
        valueTimerPrepare = in.readLong();
        valueTimerWork = in.readLong();
        valueTimerRest = in.readLong();
        valueCycle = in.readLong();
        valueTabata = in.readLong();
    }

    public static final Creator<Tabata> CREATOR = new Creator<Tabata>() {
        @Override
        public Tabata createFromParcel(Parcel in) {
            return new Tabata(in);
        }

        @Override
        public Tabata[] newArray(int size) {
            return new Tabata[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // On ajoute les objets dans l'ordre dans lequel on les a déclarés
        dest.writeLong(getValueTimerPrepare());
        dest.writeLong(getValueTimerWork());
        dest.writeLong(getValueTimerRest());
        dest.writeLong(getValueCycle());
        dest.writeLong(getValueTabata());
    }
}
