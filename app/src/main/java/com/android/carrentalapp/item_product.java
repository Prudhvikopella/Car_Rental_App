package com.android.carrentalapp;

public class item_product {

      public int id;
      public String imageurl,code,companyname,modelname,bits,catogery;

     public item_product(int id, String imageurl, String code, String companyname, String modelname, String bits, String catogery){
         this.id = id;
         this.imageurl = imageurl;
         this.code = code;
         this.companyname = companyname;
         this.modelname = modelname;
         this.bits = bits;
         this.catogery = catogery;

     }

    public int getId() {
        return id;
    }

    public String getcode() { return code; }

    public String getCatogery() {
        return catogery;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getBits() {
        return bits;
    }

    public String getCompanyname() {
        return companyname;
    }
    public String getModelname() {
        return modelname;
    }
}
