package com.ivent.entities.adapter;

import android.content.Context;

/**
 * Created by zzuo on 11/22/15.
 */
public class BuildEntities extends ProxyEntities implements CreateEntities, FetchEntities {
    public BuildEntities(Context context) {
        super(context);
    }
}
