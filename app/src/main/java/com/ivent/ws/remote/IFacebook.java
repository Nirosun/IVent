package com.ivent.ws.remote;

import java.util.List;
import java.util.Map;

/**
 * Created by Luciferre on 11/13/15.
 */
public interface IFacebook {

    public Long getFacebookID();

    public List<Map<String, Object>> getFacebookEvents();

}
