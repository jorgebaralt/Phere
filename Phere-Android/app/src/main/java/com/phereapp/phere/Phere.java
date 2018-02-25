package com.phereapp.phere;

/**
 * Created by Andres on 2/24/2018.
 */

public class Phere {
    private String phereName;
    private String phereLocation;
    private String privacy;

    // Constructor
    public Phere(String phereName, String phereLocation, String privacy){
        this.phereName = phereName;
        this.phereLocation = phereLocation;
        this.privacy = privacy;
    }

    public String getPhereName() { return phereName;}
    public String getPhereLocation() { return phereLocation;}
    public String getPrivacy() { return privacy;}
}
