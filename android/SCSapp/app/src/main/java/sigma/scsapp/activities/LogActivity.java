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

import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.fragment.TimePickerFragment;
import sigma.scsapp.controllers.JSONTaskBooking;
import sigma.scsapp.model.Booking;
import sigma.scsapp.utility.AsyncResponseBooking;
import sigma.scsapp.utility.BottomNavigationViewHelper;

public class LogActivity extends AppCompatActivity implements AsyncResponseBooking //implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        private final String URL_TO_HIT = "http://10.0.2.2:8000/servertest.json";
        private TextView tvData;
        private ListView lvBookings;
        private ProgressDialog dialog;
        TimePickerFragment timepickerfrag;
        JSONTaskBooking myJsonTask = new JSONTaskBooking();




                // Git error fix - http://stackoverflow.com/questions/16614410/android-studio-checkout-github-error-createprocess-2-windows

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.log_list);


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
            myJsonTask.delegate = this;
            myJsonTask.execute(URL_TO_HIT);


            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(0);
            menuItem.setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                case R.id.ic_books:
                    Intent intent2 = new Intent(LogActivity.this, BookingActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.ic_center_focus:
                   // Intent intent3 = new Intent(LogActivity.this, MapActivity.class);
                   // startActivity(intent3);
                    break;

                case R.id.ic_backup:


                    break;
                }


                return false;
                }
            });

            }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
            {
            Log.i("OnCreateOption", "Clickable menu");

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_confirm_booking, menu);
            return true;
            }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
            {
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings)
                {
                new JSONTaskBooking().execute(URL_TO_HIT);
                return true;
                }

            return super.onOptionsItemSelected(item);
            }


        @Override
        public void processFinishBooking(final List<Booking> output)
            {
            Log.i("Result tag", " Result from JSONTASK: " + output);
            Log.i("OnPostExecute", " Trying to finish up with Row into the List with result: " + output);
            dialog.dismiss();
            if (output != null)
                {
                // the Adapter takes the Row-Layout, inserting the result into it.
                BookingAdapter adapter = new BookingAdapter(LogActivity.this, R.layout.list_row_booking, output);
                // the ListView (lvBooking) takes the adapter, in this case the Row (with the result) and add it into the ListView.
                lvBookings.setAdapter(adapter);
                lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {  // list item click opens a new detailed activity
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                            Booking booking = output.get(position); // getting the model
                            Intent intent = new Intent(LogActivity.this, DetailActivity.class);
                            //intent.putExtra("bookingkey", new Gson().toJson(booking)); // converting model json into string type and sending it via intent
                            startActivity(intent);
                            }
                    });
                } else
                {
                Toast.makeText(LogActivity.this, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
                }

            }
        public class BookingAdapter extends ArrayAdapter
            {


                private List<Booking> bookingList;
                private int resource;
                private LayoutInflater inflater;

                public BookingAdapter(Context context, int resource, List<Booking> objects)
                    {
                    super(context, resource, objects);
                    bookingList = objects;
                    Log.i("VehicleAdapter", "bookingList got info: " + bookingList);
                    this.resource = resource;
                    inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    }

                @Override
                public View getView(int position, View convertView, ViewGroup parent)
                    {
                    Log.i("VehicleAdapter", "Starting the VehicleAdapter");
                    ViewHolder holder = null;
                    if (convertView == null)
                        {
                        holder = new ViewHolder();
                        convertView = inflater.inflate(resource, null);
                        // holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
                      //  holder.tvTimeOfBooking = (TextView) convertView.findViewById(R.id.tvTimeOfBooking);
                        holder.tvStartDate = (TextView) convertView.findViewById(R.id.tvStartDate);
                        holder.tvStartTime = (TextView) convertView.findViewById(R.id.tvStartTime);
                        holder.tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
                        holder.tvEndTime = (TextView) convertView.findViewById(R.id.tvEndTime);
                        //  holder.tvIsConfirmed = (TextView) convertView.findViewById(R.id.tvIsConfirmed);
                        holder.tvErrand = (TextView) convertView.findViewById(R.id.tvErrand);
                        holder.tvDestination = (TextView) convertView.findViewById(R.id.tvDestination);
                        holder.tvPurpose = (TextView) convertView.findViewById(R.id.tvPurpose);
                        convertView.setTag(holder);

                        } else
                        {
                        holder = (ViewHolder) convertView.getTag();
                        }
                    //   holder.tvId.setText("Id" + bookingList.get(position).getId());
                   // holder.tvTimeOfBooking.setText(bookingList.get(position).getTimeOfBooking());
                    holder.tvStartDate.setText(bookingList.get(position).getStartingDate());
                    holder.tvStartTime.setText(bookingList.get(position).getStartingTime());
                    holder.tvEndDate.setText(bookingList.get(position).getEndingDate());
                    holder.tvEndTime.setText(bookingList.get(position).getEndingTime());
                    //  holder.tvIsConfirmed.setText("Is confirmed? : " + bookingList.get(position).getIsConfirmed());
                    holder.tvErrand.setText(bookingList.get(position).getErrand());
                    holder.tvDestination.setText(bookingList.get(position).getDestination());
                    holder.tvPurpose.setText(bookingList.get(position).getPurpose());

                    return convertView;

                    }

                class ViewHolder
                    {
                        private TextView tvId;
                        private TextView tvTimeOfBooking;
                        private TextView tvStartDate;
                        private TextView tvStartTime;
                        private TextView tvEndDate;
                        private TextView tvEndTime;
                        private TextView tvIsConfirmed;
                        private TextView tvErrand;
                        private TextView tvDestination;
                        private TextView tvPurpose;
                    }
            }
    }
