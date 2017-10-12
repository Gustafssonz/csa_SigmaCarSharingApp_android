package sigma.scsapp.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseVehicle;

/**
 * Created by Niklas on 2017-10-09.
 */

public class JSONTaskVehicle extends AsyncTask<String, String, List<Vehicle>>
    {
        public AsyncResponseVehicle delegate = null;

        private final static String URL_TO_HIT = "";
        private ProgressDialog dialog;
        private ListView lvBookings;
        public Activity activity;


        @Override
        protected void onPreExecute()
            {
            Log.i("JSONTaskVehicle", "Start the JSONTaskVehciel with url: " + URL_TO_HIT);
            super.onPreExecute();
//            dialog.show();
            }

        @Override
        protected List<Vehicle> doInBackground(String... params)
            {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
                {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.i("JSONTaskVehicle", "Still trying to connect ... ");
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    {
                    buffer.append(line);
                    }

                String finalJson = buffer.toString();
                Log.i("JSONTaskVehicle", "FinalJson is now: " + finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                try
                    {
                    List<Vehicle> list;
                    JSONArray parentArray = parentObject.getJSONArray("");
                    list = makeGsonArray(parentArray);
                    Log.i("JSONARRAY TRY Vehicle", "trying prase array");
                    return list;
                    } catch (JSONException e)
                    {
                    Log.i("JSONARRAY TRY vehicle", "No array found : " + e);
                    Log.i("Try catch vehicle", "trying prase object");
                    List<Vehicle> list = new ArrayList<>();
                    list.add((makeGsonObject(finalJson)));
                    return list;

                    }

                } catch (MalformedURLException e)
                {
                e.printStackTrace();
                } catch (IOException e)
                {
                e.printStackTrace();
                } catch (JSONException e)
                {
                e.printStackTrace();
                } finally
                {
                if (connection != null)
                    {
                    connection.disconnect();
                    }
                try
                    {
                    if (reader != null)
                        {
                        reader.close();
                        }
                    } catch (IOException e)
                    {
                    e.printStackTrace();
                    }
                }
            return null;

            }


        @Override
        protected void onPostExecute(final List<Vehicle> result)
            {
            Log.i("OnPostExec Vehicle", "result from OnPostExec" + result);
            delegate.processFinishVehicle(result);

            }


        private List<Vehicle> makeGsonArray (JSONArray parentArray)
            {
            List<Vehicle> list = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < parentArray.length(); i++)
                {
                JSONObject finalobject = null;
                try
                    {
                    finalobject = parentArray.getJSONObject(i);
                    } catch (JSONException e)
                    {
                    e.printStackTrace();
                    }
                Vehicle bookingGson = gson.fromJson(finalobject.toString(), Vehicle.class);
                list.add(bookingGson);
                Log.i("JSONTaskVehcile", "Returning the List from JSONtask with list:" + list);

                }
            return list;
            }


        private Vehicle makeGsonObject(String finalJson)
            {
            Gson gson = new Gson();
            Vehicle bookingGson = gson.fromJson(finalJson, Vehicle.class);
            return bookingGson;
            }
    }

