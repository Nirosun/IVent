package com.ivent.ws.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ivent.entities.model.Category;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for setup and maintain connection with the server
 */
public class ServerConnector implements IServerInteraction {
    private static final String BASE_URL = "http://128.237.160.251:8080/IVentServer/ivent/";

    @Override
    public boolean createUserOnServer(User user) {
        HttpURLConnection connection = null;
        boolean success = false;
        String urlStr = BASE_URL + "user";
        String params = generateParameters(
                new String[] {"name", "password", "photoLink"},
                new String[] {user.getName(), user.getPassword(), user.getPhoto()});
        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == 200) {
                success = true;
            } else {
                success = false;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return success;
    }

    @Override
    public User getUserFromServer(String name) {
        HttpURLConnection connection = null;
        String urlStr = BASE_URL + "user/" + name;
        User user = null;

        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);

            if (connection.getResponseCode() == 200) {
                String jsonStr = getStringFromInputStream(connection.getInputStream());

                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse(jsonStr).getAsJsonObject();
                String password = o.get("password").getAsString();
                String photoLink = o.get("photoLink").getAsString();

                user.setName(name);
                user.setPassword(password);
                user.setPassword(photoLink);
            } else {
                return null;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return user;
    }

    @Override
    public boolean createCategoryOnServer(Category category) {
        HttpURLConnection connection = null;
        boolean success = false;
        String urlStr = BASE_URL + "category";
        String params = generateParameters(
                new String[] {"userId", "name"},
                new String[] {"1", category.getName()});
        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == 200) {
                success = true;
            } else {
                success = false;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return success;
    }

    @Override
    public List<Category> getCategoriesFromServer(long userId) {
        HttpURLConnection connection = null;
        String urlStr = BASE_URL + "category/user_" + userId;
        List<Category> categories = new ArrayList<>();

        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);

            if (connection.getResponseCode() == 200) {
                String jsonStr = getStringFromInputStream(connection.getInputStream());
                JsonParser parser = new JsonParser();
                JsonArray arr = parser.parse(jsonStr).getAsJsonArray();

                for (int i = 0; i < arr.size(); i ++) {
                    Category c = new Category();
                    c.setName(arr.get(i).getAsJsonObject().get("name").getAsString());
                    categories.add(c);
                }
            } else {
                return null;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return categories;
    }

    @Override
    public boolean createEventOnServer(Event event) {
        HttpURLConnection connection = null;
        boolean success = false;
        String urlStr = BASE_URL + "event";
        String params = generateParameters(
                new String[] {"name", "eventTime", "location", "description", "imageLink"},
                new String[] {event.getName(), event.getEventTime().toString(),
                        event.getLocation(), event.getDescription(), event.getImageLink()});

        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == 200) {
                success = true;
            } else {
                success = false;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return success;
    }

    @Override
    public List<Event> getEventsFromServer(long categoryId) {
        HttpURLConnection connection = null;
        String urlStr = BASE_URL + "event/category_" + categoryId;
        List<Event> events = new ArrayList<>();

        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);

            if (connection.getResponseCode() == 200) {
                String jsonStr = getStringFromInputStream(connection.getInputStream());
                JsonParser parser = new JsonParser();
                JsonArray arr = parser.parse(jsonStr).getAsJsonArray();

                for (int i = 0; i < arr.size(); i ++) {
                    Event e = new Event();
                    JsonObject o = arr.get(i).getAsJsonObject();
                    e.setName(o.get("name").getAsString());
                    e.setLocation(o.get("location").getAsString());
                    e.setDescription(o.get("description").getAsString());
                    e.setImageLink(o.get("imageLink").getAsString());
                    events.add(e);
                }
            } else {
                return null;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return events;
    }

    @Override
    public boolean createPostOnServer(Post post) {
        HttpURLConnection connection = null;
        boolean success = false;
        String urlStr = BASE_URL + "post";
        String params = generateParameters(
                new String[] {"userId", "eventId", "postText", "ts"},
                new String[] {post.getUsername(), post.getEventName(),
                        post.getText(), post.getTs().toString()});
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == 200) {
                success = true;
            } else {
                success = false;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return success;
    }

    @Override
    public List<Post> getPostsFromServer(long eventId) {
        HttpURLConnection connection = null;
        String urlStr = BASE_URL + "post/event_" + eventId;
        List<Post> posts = new ArrayList<>();

        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);

            if (connection.getResponseCode() == 200) {
                String jsonStr = getStringFromInputStream(connection.getInputStream());
                JsonParser parser = new JsonParser();
                JsonArray arr = parser.parse(jsonStr).getAsJsonArray();

                for (int i = 0; i < arr.size(); i ++) {
                    Post p = new Post();
                    JsonObject o = arr.get(i).getAsJsonObject();
                    p.setUsername(o.get("userName").getAsString());
                    p.setEventName(o.get("eventName").getAsString());
                    p.setText(o.get("postText").getAsString());
                    posts.add(p);
                }
            } else {
                return null;
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return posts;
    }

    private String generateParameters(String[] names, String[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length; i ++) {
            sb.append(names[i]).append("=").append(values[i]).append("&");
        }
        return sb.toString();
    }

    /**
     * Get string from input stream
     *
     * @param in
     * @return
     */
    private String getStringFromInputStream(InputStream in) {
        String str = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        try {
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            str = new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
