package com.example.gourav.cameragallery;

class Imageuploadinfo {

    public String imagename;
    public String imageurl;
    public Imageuploadinfo(){

    }

    public Imageuploadinfo(String name, String url){
        this.imagename=name;
        this.imageurl=url;
    }

    public String getImagename(){ return imagename;  }
    public String getImageurl() { return  imageurl; }
}
