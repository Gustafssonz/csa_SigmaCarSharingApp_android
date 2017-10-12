package sigma.scsapp.activities;

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

import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.controllers.JSONTaskUser;
import sigma.scsapp.controllers.JSONTaskVehicle;
import sigma.scsapp.model.Booking;
import sigma.scsapp.utility.AsyncResponseBooking;

/**
 * Created by Niklas on 2017-10-12.
 */

/*
public class LoadData
    {
       public JSONTaskBooking jsnBooking = new JSONTaskBooking();
       public  JSONTaskVehicle jsnVehicle = new JSONTaskVehicle();
       public  JSONTaskUser jsnUser = new JSONTaskUser();

        LoadData()
            {
            jsnBooking.execute("http://10.0.2.2:8000/servertest.json");
            jsnBooking.makeGsonObject(Booking newBooking);

            }

    }
*/
