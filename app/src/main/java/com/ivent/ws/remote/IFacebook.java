package com.ivent.ws.remote;

import java.util.List;
import java.util.Map;

/**
 * This interface is for methods that deal with connection and communication
 * with Facebook
 */
public interface IFacebook {

    //request facebook user id
    public Long getFacebookID();

    //request facebook events
    public List<Map<String, Object>> getFacebookEvents();

}
