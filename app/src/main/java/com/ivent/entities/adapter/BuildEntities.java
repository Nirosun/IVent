package com.ivent.entities.adapter;

import android.content.Context;

/**
 * This class is for use by UI classes
 */
public class BuildEntities extends ProxyEntities implements CreateEntities, FetchEntities {
    public BuildEntities(Context context, boolean isNetowrkOn) {
        super(context, isNetowrkOn);
    }
}
