package models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;

@Entity
@Table(name = "taxi")
@JsonPropertyOrder({"id", "pickupTime", "dropoffTime", "puLocationId", "doLocationId", "tripDistance", "totalAmount"})
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index")
    private Long id;

    @Column(name = "vendoriD")
    private Double VendorID;

    @Column(name = "dispatching_base_num")
    private String  dispatching_base_num;

    @Column(name = "lpep_pickup_datetime")
    private String pickupTime;

    @Column(name = "lpep_dropoff_datetime")
    private String dropoffTime;

    @Column(name = "store_and_fwd_flag")
    private String storeFwdFlag;

    @Column(name = "ratecodeID")
    private Double rateCodeId;

    @Column(name = "pulocationiD")
    private Long puLocationid;

    @Column(name = "dolocationiD")
    private Long doLocationId;

    @Column(name = "passenger_count")
    private Double passengerCount;

    @Column(name = "trip_distance")
    private Double tripDistance;

    @Column(name = "fare_amount")
    private Double fareAmount;

    @Column(name = "extra")
    private Double extra;

    @Column(name = "mta_tax")
    private Double mta_tax;

    @Column(name = "tip_amount")
    private Double tip_amount;

    @Column(name = "tolls_amount")
    private Double tolls_amount;
//
//    @Column(name = "ehail_fee")
//    private Double ehailFee;

    @Column(name = "improvement_surcharge")
    private Double improvementSurcharge;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "payment_type")
    private Double paymentType;

    @Column(name = "type")
    private String taxiType;

    @Column(name = "sr_flag")
    private Double sr_flag;

    public Taxi() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setType(String taxiType) {
        this.taxiType = taxiType;
    }
    public String getType() {
        return taxiType;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
    public String getPickupTime() {
        return pickupTime;
    }
    public void setDropoffTime(String dropoffTime) {
        this.dropoffTime = dropoffTime;
    }
    public String getDropoffTime() {
        return dropoffTime;
    }

    public void setPuLocationid(Long puLocationid) {
        this.puLocationid = puLocationid;
    }
    public Long getPuLocationId() {
        return puLocationid;
    }

    public void setDoLocationid(Long doLocationid) {
        this.doLocationId = doLocationid;
    }
    public Long getDoLocationId() {
        return doLocationId;
    }

    public void setTripDistance(Double tripDistance) {
        this.tripDistance = tripDistance;
    }
    public Double getTripDistance() {
        return tripDistance;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }

    // Creating toString for return API data
    @Override
      public String toString(){
        Taxi taxi = new Taxi();
        taxi.setId(id);
        taxi.setType(taxiType);
        taxi.setPickupTime(pickupTime);
        taxi.setDropoffTime(dropoffTime);
        taxi.setPuLocationid(puLocationid);
        taxi.setDoLocationid(doLocationId);
        taxi.setTripDistance(tripDistance);
        taxi.setTotalAmount(totalAmount);
        //Creating the ObjectMapper object

        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(taxi);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public Taxi(String taxiType, String pickupTime, String dropoffTime, Long puLocation, Long doLocation, Double tripDistance, Double totalAmount) {
        this.taxiType = taxiType;
        this.pickupTime= pickupTime;
        this.dropoffTime = dropoffTime;
        this.puLocationid = puLocation;
        this.doLocationId = doLocation;
        this.tripDistance = tripDistance;
        this.totalAmount = totalAmount;
    }


    public void deserialize(String taxi) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Taxi obj = mapper.readValue(taxi, Taxi.class);
        taxiType = obj.getType();
        pickupTime = obj.getPickupTime();
        dropoffTime = obj.getDropoffTime();
        puLocationid = obj.getPuLocationId();
        doLocationId = obj.getDoLocationId();
        tripDistance = obj.getTripDistance();
        totalAmount = obj.getTotalAmount();
    }





}
