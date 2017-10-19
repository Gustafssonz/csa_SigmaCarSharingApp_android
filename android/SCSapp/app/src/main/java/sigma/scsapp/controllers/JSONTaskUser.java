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

import sigma.scsapp.model.User;
import sigma.scsapp.utility.AsyncResponseUser;

/**
 * Created by Niklas on 2017-10-09.
 */

public class JSONTaskUser extends AsyncTask<String, String, List<User>>
    {
        public AsyncResponseUser delegate = null;

        private final static String URL_TO_HIT = "";
        private ProgressDialog dialog;
        private ListView lvBookings;
        public Activity activity;


        @Override
        protected void onPreExecute()
            {
            Log.i("JSONTaskBooking", "Start the JSONTaskBooking with url: " + URL_TO_HIT);
            super.onPreExecute();
//            dialog.show();
            }

        @Override
        protected List<User> doInBackground(String... params)
            {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            Log.i("JSONTaskBooking", "Will try connect to URL ...");

            try
                {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.i("JSONTaskBooking", "Still trying to connect ... ");
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
                JSONArray parentArray = parentObject.getJSONArray("user");

                Log.i("JSONTaskBooking", "Trying to fetch Array from Object with param: " + parentArray);
                List<User> userList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++)
                    {
                    JSONObject finalobject = parentArray.getJSONObject(i);
                    //BookingTest bookingtest = new BookingTest(id, "köpa käk", "destination", "purpose", true );
                    User GsonList = gson.fromJson(finalobject.toString(), User.class); // a single line json parsing using Gson
                    userList.add(GsonList);
                    Log.i("JSONTaskBooking", "Returning the List from JSONtask");

                    }
                return userList;



                } catch (JSONException | IOException e)
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
       /* public List<Booking> makeGsonArray() {
        return doInBackground();
        }

        @Override
        protected  void onPostExecute(final List<Booking> result) {
        Log.i("OnPostExec", "result from OnPostExec" + result);
        delegate.processFinishVehicle(result);*/

        }

