package sigma.scsapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import sigma.scsapp.fragment.TimePickerFragment;
import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.model.Booking;
import sigma.scsapp.model.Vehicle;
import sigma.scsapp.utility.AsyncResponseBooking;
import sigma.scsapp.utility.BottomNavigationViewHelper;

import static sigma.scsapp.utility.URL.URL_TO_HIT;
//import static sigma.scsapp.utility.URL.getAllBookings;

public class LogActivity extends AppCompatActivity implements AsyncResponseBooking, NavigationView.OnNavigationItemSelectedListener //implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private TextView tvData;
    private ListView lvBookings;
    private ProgressDialog dialog;
    TimePickerFragment timepickerfrag;
    JSONTaskBooking jsonTaskBooking = new JSONTaskBooking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity_main);


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

        lvBookings = (ListView) findViewById(R.id.LV_list);


        // To start fetching the data when app start, uncomment below line to start the async task.
        jsonTaskBooking.delegate = this;
        jsonTaskBooking.execute("http://localhost:8080/api/csa/bookings");

        addBottomBar();
        addToolBarWithDrawerFunction();
        addNavigationbar();

    }


    public void addBottomBar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_books:
                        Intent intent2 = new Intent(LogActivity.this, BookingActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(LogActivity.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:


                        break;
                }


                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void addToolBarWithDrawerFunction(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        Log.i("Tag", "You pressed the naviagion drawer --------------");
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    public void addNavigationbar () {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_manage) {
            // go back to profile-view
            startActivity(new Intent(LogActivity.this, AdminActivity.class));


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.toolbar) {
            startActivity(new Intent(LogActivity.this, UserProfileActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("OnCreateOption", "Clickable menu");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_booking, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new JSONTaskBooking().execute(URL_TO_HIT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void processFinishBooking(final List<Booking> output) {
        Log.i("Result tag", " Result from JSONTASK: " + output);
        Log.i("OnPostExecute", " Trying to finish up with Row into the List with result: " + output);
        dialog.dismiss();
        if (output != null) {
            // the Adapter takes the Row-Layout, inserting the result into it.
            BookingAdapter adapter = new BookingAdapter(this, LogActivity.this, R.layout.list_row_booking, output, new ArrayList<Vehicle>());
            // the ListView (lvBooking) takes the adapter, in this case the Row (with the result) and add it into the ListView.
            lvBookings.setAdapter(adapter);
            lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Booking booking = output.get(position); // getting the model
                    // Intent intent = new Intent(LogActivity.this, DetailActivity.class);
                    //intent.putExtra("bookingkey", new Gson().toJson(booking)); // converting model json into string type and sending it via intent
                    // startActivity(intent);
                }
            });
        } else {
            Toast.makeText(LogActivity.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
        }
    }
}
