package com.ivent.entities.adapter;

import android.content.Context;

//Intended for null
public class BuildEntities extends ProxyEntities implements CreateEntities, FetchEntities {
    public BuildEntities(Context context) {
        super(context);
    }
}
