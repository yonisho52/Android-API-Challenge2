package com.example.android_api_challenge2.Service;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.example.android_api_challenge2.Activity.MainActivity;
import com.example.android_api_challenge2.Model.State;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataService {

    private ArrayList<State> arrState = new ArrayList<>();


    public ArrayList<State> getStateBorders(List<String> stateCode)
    {
        try {
            ArrayList<State> bordersList = new GetBorders(stateCode).execute().get();
            return bordersList;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetBorders extends AsyncTask<String,Void,ArrayList<State>> {

        List<String> stateCodeArray;

        public GetBorders(List<String> stateCodeArray) {
            this.stateCodeArray = stateCodeArray;
        }

        @Override
        protected ArrayList<State> doInBackground(String... strings) {
            ArrayList<State> arrS = new ArrayList<>();

            for(String stateCode : stateCodeArray)
            {
                String sURL1 = "https://restcountries.eu/rest/v2/alpha/" + stateCode + "?fields=name;nativeName"; // gets a state by code
                JsonObject rootobj = null;
                State s = null;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                // Connect to the URL using java's native library
                URL url = null;
                try {
                    url = new URL(sURL1);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection request = (HttpURLConnection) url.openConnection();
                    Log.d("result", "trying to connect");
                    request.connect();
                    Log.d("result", "connected");

                    JsonParser jp = new JsonParser(); //from gson
                    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

                    rootobj = root.getAsJsonObject();

                    JsonElement entriesname = rootobj.get("name");
                    JsonElement entriesnative = rootobj.get("nativeName");

                    String nameR = entriesname.toString().replace("\"", ""); // convert to pure string
                    String nativen = entriesnative.toString().replace("\"", "");

                    s = new State(nameR, nativen);
                    arrS.add(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return arrS;
        }
    }

    public void getStates() {
        new GetData().execute();
    }

    private class GetData extends AsyncTask<String, Void, ArrayList<State>> {
        private  ArrayList<State> arrState = new ArrayList<>();
        private final String sURL = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;flag";

        @Override
        protected void onPostExecute(ArrayList<State> states) {

            MainActivity mainActivity = new MainActivity();
            mainActivity.SetAllStates(arrState);
        }

        @Override
        protected ArrayList<State> doInBackground(String... strings) {
            URL url = null;
            JsonArray rootobj = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                url = new URL(sURL);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                Log.d("result", "trying to connect");
                request.connect();
                Log.d("result", "connected");
                // Convert to a JSON object to print data
                JsonParser jp = new JsonParser(); //from gson
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

                rootobj = root.getAsJsonArray();

                for (JsonElement je : rootobj) {
                    JsonObject obj = je.getAsJsonObject(); //since you know it's a JsonObject
                    JsonElement entriesname = obj.get("name");//will return members of your object
                    JsonElement entriesnative = obj.get("nativeName");
                    JsonElement entriesborders = obj.get("borders");
                    JsonElement entriesflag = obj.get("flag");

                    String name = entriesname.toString().replace("\"", "");
                    String nativen = entriesnative.toString().replace("\"", "");
                    String flag = entriesflag.toString().replace("\"", "");

                    ArrayList<String> arrBorders = new ArrayList<String>();
                    JsonArray entriesbordersArray = entriesborders.getAsJsonArray();
                    Log.d("result", "yes");

                    for (JsonElement j : entriesbordersArray) {

                        String s = j.toString().replace("\"", "");
                        arrBorders.add(s);
                        Log.d("result", "no");
                    }
                    arrState.add(new State(name, arrBorders, nativen, flag));
                }
                return arrState;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("result", "catched " + e);
            }

            return arrState;
        }
    }
}



