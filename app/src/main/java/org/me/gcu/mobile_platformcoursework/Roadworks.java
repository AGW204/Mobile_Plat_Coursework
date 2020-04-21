package org.me.gcu.mobile_platformcoursework;

import android.util.Log;

// Roadwork class for storing parsed data
//
//// Created by Andrew Gaw S1626042
////
////


public class Roadworks {

    String Road;
    String Description;
    String Link;
    String Coordinates;
    double Latitude;
    double Longitude;
    String Start_date;
    String End_date;


    public Roadworks() {

        Road = "";
        Description = "";
        Link = "";
        Coordinates = "";
        Start_date = "";
        End_date="";

    }

    public String getRoad() {
        return Road;
    }

    public void setRoad(String Road) {
        this.Road = Road;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String Coordinates) {
        this.Coordinates = Coordinates;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getStart_date(){
        return Start_date;
    }

    public void setStart_date(String Start_date){
        this.Start_date = Start_date;
    }

    public String getEnd_date(){
        return End_date;
    }

    public void setEnd_date(String End_date){
        this.End_date = End_date;
    }

    public void Separate_Coordinates (String Coordinates){
        String [] Separate = Coordinates.split(" ");

        Double Lat = Double.parseDouble(Separate[0]);
        Double Long = Double.parseDouble(Separate[1]);

        setLatitude(Lat);
        setLongitude(Long);
    }

    public void Separate_Description (String Description){

        String [] Separate = Description.split("<br />");

        if (Separate.length > 1){

            String temp_start = (Separate[0]);
            String temp_end  = (Separate[1]);

            setStart_date(temp_start);
            setEnd_date(temp_end);
        }

    }



    @Override
    public String toString() {

        String temp;
        temp = ("Title: " + Road + "\n" + Start_date + "\n"  + End_date +  "\n" + "\n");
        return temp;
    }
}