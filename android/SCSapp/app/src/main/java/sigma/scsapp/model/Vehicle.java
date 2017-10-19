package sigma.scsapp.model;


import android.widget.ImageView;

/**
 * Created by Niklas on 2017-09-14.
 */

public class Vehicle {
    private String vehicleId;
    private String reg;
    private String year;
    private String mileage;
    private String body;
    private String equipment;
    private String model;
    private String fuel;
    private String site;
    private String responsible;
    //private ImageView vehicleImage;
    //private boolean isAvalible;
    private String vehicleImageLink;


    public Vehicle(String vehicleId, String reg, String year, String mileage, String body, String equipment, String model, String fuel, String site, String responsible, String vehicleImageLink)
        {
        this.vehicleId = vehicleId;
        this.reg = reg;
        this.year = year;
        this.mileage = mileage;
        this.body = body;
        this.equipment = equipment;
        this.model = model;
        this.fuel = fuel;
        this.site = site;
        this.responsible = responsible;
        this.vehicleImageLink = vehicleImageLink;
        }

    // GETTERS AND SETTERS


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getVehicleImageLink() {
        return vehicleImageLink;
    }

    public void setVehicleImageLink(String vehicleImageLink) {
        this.vehicleImageLink = vehicleImageLink;
    }

    @Override
    public String toString()
        {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", reg='" + reg + '\'' +
                ", year='" + year + '\'' +
                ", mileage='" + mileage + '\'' +
                ", body='" + body + '\'' +
                ", equipment='" + equipment + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", site='" + site + '\'' +
                ", responsible='" + responsible + '\'' +
                ", vehicleImageLink='" + vehicleImageLink + '\'' +
                '}';
        }
}
