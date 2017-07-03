package com.beta.app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFinacne {
    private  String  fiance;

    public String getFiance() {
        return fiance;
    }

    public void setFiance(String fiance) {
        this.fiance = fiance;
    }
    
    public static void main(String[] args) {
		SimpleDateFormat  sdf =  new SimpleDateFormat("yyyy-MM-dd");
		
		sdf.format(new Date()).toString();
	}
}
