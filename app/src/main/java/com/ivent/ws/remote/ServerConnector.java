package com.ivent.ws.remote;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * This class is for setup and maintain connectino with the server, and provide
 */
public class ServerConnector implements IServerInteraction {
    HttpURLConnection connection;

    @Override
    public boolean setupConnection(String urlStr) {
        boolean isSetup = false;

        try {
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            isSetup = (connection.getResponseCode() == 200);
        } catch (MalformedInputException e) {
            // TODO: handle exception here
        } catch (IOException e) {
            // TODO: handle exception here
        }

        return isSetup;
    }

    @Override
    public String getJsonStringFromConnection() {
        // TODO: Fill in implementation details
        return null;
    }
}
