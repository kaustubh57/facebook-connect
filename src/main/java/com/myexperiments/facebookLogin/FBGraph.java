package com.myexperiments.facebookLogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FBGraph {
    private String accessToken;

    public FBGraph(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFBGraph() {
        String graph;
        try {
            String g = "https://graph.facebook.com/v2.7/me?fields=name,first_name,email,gender,age_range,picture,location&" + accessToken;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
            System.out.println(graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map<String, String> getGraphData(String fbGraph) {
        Map<String, String> fbProfile = new HashMap<>();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email"))
                fbProfile.put("email", json.getString("email"));
            if (json.has("gender"))
                fbProfile.put("gender", json.getString("gender"));
            if (json.has("name"))
                fbProfile.put("name", json.getString("name"));
            if (json.has("age_range"))
                fbProfile.put("age_range", json.getString("age_range"));
            if (json.has("picture"))
                fbProfile.put("picture", json.getString("picture"));
            if (json.has("location"))
                fbProfile.put("location", json.getString("location"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}
