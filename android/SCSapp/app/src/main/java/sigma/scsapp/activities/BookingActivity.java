package sigma.scsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.utility.ExpandableListAdapter;
import sigma.scsapp.utility.BottomNavigationViewHelper;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends Activity
    {

        ExpandableListAdapter listAdapter;
        ExpandableListView expListView;
        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;

        ArrayList<String> car;


        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.booking_activityview);

            expListView = (ExpandableListView) findViewById(R.id.exvListView);
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);





            /*
             * Avilable car list
             */
/*
            ListView carlist = (ListView) findViewById(R.id.lvBookingListOfCars);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, car);
            carlist.setAdapter(arrayAdapter);
            carlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    // argument position gives the index of item which is clicked
                    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
                        {
                        String selectedcar = car.get(position);
                        Log.i("Car TAG", "You have selected " + selectedcar);

                        Intent booking_form = new Intent();
                        startActivity(new Intent(BookingActivity.this, BookingFormActivity.class));
                        booking_form.putExtra("carname", selectedcar);
                        // TODO: 2017-09-21 request a timer for completion of the booking.
                        }
                });*/


            /*
             * NAV MENU
             */
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                        {
                        switch (item.getItemId())
                            {

                            case R.id.ic_books:
                                // Current
                                break;

                            case R.id.ic_center_focus:
                                //   Intent intent3 = new Intent(BookingActivity.this, MapActivity.class);
                                //   startActivity(intent3);
                                break;

                            case R.id.ic_backup:
                                Intent intent4 = new Intent(BookingActivity.this, LogActivity.class);
                                startActivity(intent4);
                                break;
                            }


                        return false;
                        }
                });

            }

        /*
         * Preparing the list data
         */
        private void prepareListData()
            {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Site");
            List<String> locations = new ArrayList<String>();
            locations.add("Gothenburg");
            locations.add("Stockholm");
            locations.add("Malmö");
            locations.add("Jonkoping");


            listDataChild.put(listDataHeader.get(0), locations);


            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
                {


                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id)
                        {
                        int position = (childPosition + 1);
                        startDateSelection();
                        String postString = String.valueOf(position);
                        TextView selectedRegion = (TextView) findViewById(R.id.tv_bookingactivity_selected_region);


                        selectedRegion.setText(postString);
                        Intent startBooking = new Intent(BookingActivity.this, BookingFormActivity.class);
                        startBooking.putExtra("site", postString);
                        startActivity(startBooking);



                        Log.e("Child click", "You clicked on site with name: " + (postString));
                        expListView.collapseGroup(groupPosition);
                        Toast.makeText(
                                getApplicationContext(),
                                listDataHeader.get(groupPosition)
                                        + " : "
                                        + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                                .show();
                        return false;
                        }

                    private void startDateSelection()
                        {

                        }
                });

            // TODO: 2017-09-20 resolve the expandablelistview to differ from the custome class.

            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
                {

                    @Override
                    public void onGroupExpand(int groupPosition)
                        {
                        Toast.makeText(getApplicationContext(),
                                listDataHeader.get(groupPosition) + " Expanded",
                                Toast.LENGTH_SHORT).show();
                        }
                });


            }


    }



