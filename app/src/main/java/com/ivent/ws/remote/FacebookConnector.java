package com.ivent.ws.remote;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.ivent.util.JsonTools;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Luciferre on 11/13/15.
 */
public class FacebookConnector implements IFacebook {

    private static final String TAG = "debug";

    public Long getFacebookID() {
        GraphResponse graphResponse = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                null
        ).executeAndWait();

        String userInfo = graphResponse.getRawResponse();
        Log.d("RESPONSE", userInfo);
        Map<String, Object> user = JsonTools.getUserFromFB(userInfo);
        Long facebookId = (Long) user.get("facebookId");
        String name = (String) user.get("name");

        return facebookId;
    }

    public List<Map<String, Object>> getFacebookEvents() {
        final List<String> eventIDs = new ArrayList<>();
        GraphResponse eventsResponse = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/events",
                null,
                HttpMethod.GET,
                null
        ).executeAndWait();
        JSONObject jsonEvents = eventsResponse.getJSONObject();
        Log.d(TAG, jsonEvents.toString());
        eventIDs.addAll(JsonTools.getEventIDsFromFB("data", jsonEvents.toString()));

        Log.d(TAG, "Between getting eventIDs and events");

        //get events
        List<GraphRequest> requests = new ArrayList<>();
        for (String id : eventIDs) {
            requests.add(
                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/" + id,
                            null,
                            HttpMethod.GET,
                            null
                    ));
        }
        GraphRequestBatch batch = new GraphRequestBatch(requests);
        List<GraphResponse> responses = batch.executeAndWait();

        final List<Map<String, Object>> events = new ArrayList<>();
        for (GraphResponse res : responses) {
            Log.d(TAG, "Get FB events: " + res.getRawResponse());
            events.add(JsonTools.getEventFromFB(res.getRawResponse()));
        }

        return events;
    }
}
