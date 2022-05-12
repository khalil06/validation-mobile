package com.company.myapp.Entities;

import java.util.Date;
import com.company.myapp.utils.*;

public class Vol implements Comparable<Vol> {

    private int id;
    private Company company;
    private String origin;
    private String destination;
    private Date departureDate;
    private Date arrivalDate;
    private int status;
    private float initialPrice;

    public Vol() {}

    public Vol(int id, Company company, String origin, String destination, Date departureDate, Date arrivalDate, int status, float initialPrice) {
        this.id = id;
        this.company = company;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.initialPrice = initialPrice;
    }

    public Vol(Company company, String origin, String destination, Date departureDate, Date arrivalDate, int status, float initialPrice) {
        this.company = company;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.initialPrice = initialPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(float initialPrice) {
        this.initialPrice = initialPrice;
    }


    @Override
    public int compareTo(Vol vol) {
        switch (Statics.compareVar) {
            case "Company":
                return this.getCompany().getName().compareTo(vol.getCompany().getName());
            case "Origin":
                return this.getOrigin().compareTo(vol.getOrigin());
            case "Destination":
                return this.getDestination().compareTo(vol.getDestination());
            case "DepartureDate":
                DateUtils.compareDates(this.getDepartureDate(), vol.getDepartureDate());
            case "ArrivalDate":
                DateUtils.compareDates(this.getArrivalDate(), vol.getArrivalDate());
            case "Status":
                return Integer.compare(this.getStatus(), vol.getStatus());
            case "InitialPrice":
                return Float.compare(this.getInitialPrice(), vol.getInitialPrice());

            default:
                return 0;
        }
    }

}
