package com.phereapp.phere.pojo;

import java.io.Serializable;

public class PherePlaylist implements Serializable {
    private String id;
    private String ownerId;
    private String name;

    public PherePlaylist(){};

    public PherePlaylist(String id, String ownerId, String name){
        this.id = id;
        this.ownerId = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }
}
