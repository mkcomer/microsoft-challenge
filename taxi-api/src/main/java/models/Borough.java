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

@Entity
@Table(name = "borough")
@JsonPropertyOrder({"borough", "zone", "locationid"})
public class Borough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index")
    private Long id;

    @Column(name = "locationid")
    private Long locationid;

    @Column(name = "borough")
    private String borough;

    @Column(name = "zone")
    private String  zone;

    @Column(name = "service_zone")
    private String service_zone;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setLocationid(Long locationid) {
        this.locationid = locationid;
    }
    public Long getLocationid() {
        return locationid;
    }
    public void setBorough(String borough) {
        this.borough = borough;
    }
    public String getBorough() {
        return borough;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
    public String getZone() {
        return zone;
    }

    @Override
    public String toString(){
        Borough returnBorough = new Borough();
        returnBorough.setId(id);
        returnBorough.setBorough(borough);
        returnBorough.setLocationid(locationid);
        returnBorough.setZone(zone);
        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(returnBorough);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}