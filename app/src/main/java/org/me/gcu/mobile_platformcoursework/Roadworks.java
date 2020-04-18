package org.me.gcu.mobile_platformcoursework;

public class Roadworks {

    String Road;
    String Description;
    String Link;
    String Coordinates;
    String Published;

    public Roadworks() {

        Road = "";
        Description = "";
        Link = "";
        Coordinates = "";
        Published = "";

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

    public String getPublished() {
        return Published;
    }

    public void setPublished(String Published) {
        this.Published = Published;
    }

    @Override
    public String toString() {

        String temp;
        temp = ("Title: " + Road + "\n" + "Description: " + Description + "\n" + "Link: " + Link + "\n" + "Coordinates: " + Coordinates + "\n" + "Published: " + Published);
        return temp;
    }
}