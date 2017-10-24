package sigma.scsapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.adapters.BookingAdapter;
import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.fragment.TimePickerFragment;
import sigma.scsapp.model.Booking;
import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseBooking;
import sigma.scsapp.utility.BottomNavigationViewHelper;

public class ActiveBookingsActivity extends AppCompatActivity implements AsyncResponseBooking //implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private final String URL_TO_HIT = "http://10.0.2.2:8000/";
    String getActiveBookingBooking = "booking2.json";

    private TextView tvData;
    private ListView lvBookings;
    private ProgressDialog dialog;
    TimePickerFragment timepickerfrag;
    JSONTaskBooking myJsonTask = new JSONTaskBooking();


    // Git error fix - http://stackoverflow.com/questions/16614410/android-studio-checkout-github-error-createprocess-2-windows

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activebookings);


        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        lvBookings = (ListView) findViewById(R.id.lv_activeBookings);


        // To start fetching the data when app start, uncomment below line to start the async task.
        myJsonTask.delegate = this;
        myJsonTask.execute(URL_TO_HIT + getActiveBookingBooking);
    }




    @Override
    public void processFinishBooking(final List<Booking> output) {
        Log.i("Result tag", " Result from JSONTASK: " + output);
        Log.i("OnPostExecute", " Trying to finish up with Row into the List with result: " + output);
        dialog.dismiss();
        if (output != null) {
            // the Adapter takes the Row-Layout, inserting the result into it.
            BookingAdapter adapter = new BookingAdapter(this, ActiveBookingsActivity.this, R.layout.list_row_booking, output, new ArrayList<Vehicle>());
            // the ListView (lvBooking) takes the adapter, in this case the Row (with the result) and add it into the ListView.
            lvBookings.setAdapter(adapter);
            lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Booking booking = output.get(position); // getting the model
                    // Intent intent = new Intent(ActiveBookingsActivity.this, DetailActivity.class);
                    //intent.putExtra("bookingkey", new Gson().toJson(booking)); // converting model json into string type and sending it via intent
                    // startActivity(intent);
                }
            });
        } else {
            Toast.makeText(ActiveBookingsActivity.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
        }
    }
}
