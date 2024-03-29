package Classes;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


public class Attraction extends Object implements Serializable {
    private    int attractionId;
    private String name;
    private String location;
    private String description;
    private String Programme;
    private float attractionRate;
    private String category;
    private Long priceAdult;
    private Long priceStudent;
    private Long priceRetired;

    public Attraction( String AttractionName, String attractionLocation, String attractionDescription, String attractionProgramme, String category, Long priceAd, Long priceStud, Long priceRe) {

        this.name = AttractionName;
        this.location = attractionLocation;
        this.description = attractionDescription;
        this.Programme = attractionProgramme;
        this.category = category;
        this.priceAdult = priceAd;
        this.priceStudent = priceStud;
        this.priceRetired = priceRe;

    }


    public Attraction(){

    }
    public Attraction(String name){
        this.name =name;

    }

    public Long getPriceAdult() {
        return priceAdult;
    }

    public void setPriceAdult(Long priceAdult) {
        this.priceAdult = priceAdult;
    }

    public Long getPriceStudent() {
        return priceStudent;
    }

    public void setPriceStudent(Long priceStudent) {
        this.priceStudent = priceStudent;
    }

    public Long getPriceRetired() {
        return priceRetired;
    }

    public void setPriceRetired(Long priceRetired) {
        this.priceRetired = priceRetired;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgramme() {
        return Programme;
    }

    public void setProgramme(String programme) {
        Programme = programme;
    }

    public float getAttractionRate() {
        return attractionRate;
    }

    public void setAttractionRate(float attractionRate) {
        this.attractionRate = attractionRate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

