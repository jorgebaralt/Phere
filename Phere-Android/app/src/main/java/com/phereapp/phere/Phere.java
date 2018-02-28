package com.phereapp.phere;

/**
 * Created by Andres on 2/24/2018.
 */

public class Phere {
    private String phereName;
    private String phereLocation;
    private String privacy;
    private String host;

    // Constructor
    public Phere(String phereName, String phereLocation, String privacy, String host){
        this.phereName = phereName;
        this.phereLocation = phereLocation;
        this.privacy = privacy;
        this.host = host;
    }

    //getters
    public String getPhereName() { return phereName;}
    public String getPhereLocation() { return phereLocation;}
    public String getPrivacy() { return privacy;}
    public String getHost() {return host;}


}
