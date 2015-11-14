package com.ivent.ws.remote;

/**
 * This interface is for methods that deal with connection and communication
 * with the backend server
 */
public interface IServerInteraction {
    /**
     * Setup connection with the server based on the given url string
     * @param urlStr url string
     * @return whether the connection has been successfully setup
     */
    public boolean setupConnection(String urlStr);

    /**
     * Get JSON string from server's response in the connection
     * @return JSON-format string
     */
    public String getJsonStringFromConnection();

    // TODO: Add more methods later
}
