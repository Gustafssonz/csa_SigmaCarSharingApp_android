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

import sigma.scsapp.model.Booking;
import sigma.scsapp.utility.AsyncResponseBooking;

/**
 * Created by Niklas on 2017-10-09.
 */

public class JSONTaskBooking extends AsyncTask<String, String, List<Booking>>
    {
        public AsyncResponseBooking delegate = null;

        private ProgressDialog dialog;
        private ListView lvBookings;
        public Activity activity;


        @Override
        protected void onPreExecute()
            {
            super.onPreExecute();
//            dialog.show();
            }

        @Override
        protected List<Booking> doInBackground(String... params)
            {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
                {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    {
                    buffer.append(line);
                    }

                String finalJson = buffer.toString();
                Log.i("JSONTaskBooking", "FinalJson is now: " + finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
// TODO: 2017-10-11  Fixa så att Vehicle syns i Result i tagg 
                try
                    {
                    List<Booking> list;
                    JSONArray parentArray = parentObject.getJSONArray("");
                    list = makeGsonArray(parentArray);
                    Log.i("JSONARRAY TRY Booking", "trying prase array");
                    return list;
                    } catch (JSONException e)
                    {
                    Log.i("JSONARRAY TRY booking", "No array found : " + e);
                    Log.i("JSONARRAY TRY booking", "trying prase object");
                    List<Booking> list = new ArrayList<>();
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
        protected void onPostExecute(final List<Booking> result)
            {
            Log.i("OnPostExec Booking ", "result from OnPostExec" + result);

            delegate.processFinishBooking(result);

            }

        private List<Booking> makeGsonArray(JSONArray parentArray)
            {
            List<Booking> list = new ArrayList<>();
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
                Booking bookingGson = gson.fromJson(finalobject.toString(), Booking.class);
                list.add(bookingGson);
                Log.i("JSONTaskBooking", "Returning the List from JSONtask with list" + list);

                }
            return list;
            }

        private Booking makeGsonObject(String finalJson)
            {
            Gson gson = new Gson();
            Booking bookingGson = gson.fromJson(finalJson, Booking.class);
            return bookingGson;
            }
    }



