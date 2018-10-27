package com.example.gourav.cameragallery;

public class mapdir{

    private Double lat;
    private Double lng;


     public  mapdir()
     {

     }



        public mapdir(Double lat, Double lng){
        this.lat= lat;
        this.lng= lng;
    }

    public Double getLat(){ return lat; }
    public Double getLng(){ return lng; }
    public void setLat(double lat){
         this.lat=lat;
    }
    public void setLng(double lng){
        this.lng=lng;
    }
}


