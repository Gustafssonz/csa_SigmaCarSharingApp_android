package sigma.scsapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;

import sigma.scsapp.R;
import sigma.scsapp.fragment.DatePickerFragment;
import sigma.scsapp.fragment.EndDatePickerFragment;
import sigma.scsapp.fragment.EndTimePickerFragment;
import sigma.scsapp.fragment.StartDatePickerFragment;
import sigma.scsapp.fragment.StartTimePickerFragment;
import sigma.scsapp.fragment.TimePickerFragment;

public class BookingFormActivity
        extends FragmentActivity
        implements StartTimePickerFragment.StartTimeListener, EndTimePickerFragment.EndTimeListener, StartDatePickerFragment.StartDateListener, EndDatePickerFragment.EndDateListener
    {

        TimePickerFragment timePickerStart;
        TimePickerFragment timePickerEnd;

        DatePickerFragment datePickerStart;
        DatePickerFragment datePickerEnd;

        Button btn_done_resultDateTime;

        private int startHour;
        private int startMinute;

        private int endHour;
        private int endMinute;

        private int startYear;
        private int startMonth;
        private int startDay;

        private int endYear;
        private int endMonth;
        private int endDay;


        private String format = "";
        private TextView time;

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.bookingform_activity);

            btn_done_resultDateTime = (Button) findViewById(R.id.btn_ResultDateTime);
            btn_done_resultDateTime.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                        {
                        Intent goBackToBooking = new Intent(BookingFormActivity.this, BookingActivity.class);
                        goBackToBooking.putExtra("ResultStart","Start Date: "+startDay +"-"+ startMonth +"-"+ startDay+"\n" + "Start Time: "+ startHour +":"+ startMinute );
                        goBackToBooking.putExtra("ResultEnd", "End Date: " + endDay +"-"+ endMonth +"-"+ endDay + "\n" + "End Time: " + endHour + ":" + endMinute);
                        startActivity(goBackToBooking);
                        }
                });
            }

        // TIME START AND END
        public void showTimePickerDialogStart(View v)
            {
            Log.i("Tag Time", "Clicked on Start Time");
            timePickerStart = new StartTimePickerFragment();
            timePickerStart.show(getFragmentManager(), "timePickerStart");
            }

        public void showTimePickerDialogEnd(View v)
            {
            Log.i("Tag Time", "Clicked on End Time");
            timePickerEnd = new EndTimePickerFragment();
            timePickerEnd.show(getFragmentManager(), "timePickerEnd");
            }


        // DATE START AND END
        public void showDatePickerDialogStart(View v)
            {
            Log.i("Tag Date", "Clicked on Start Date");
            datePickerStart = new StartDatePickerFragment();
            datePickerStart.show(getFragmentManager(), "datePickerStart");
            }

        public void showDatePickerDialogEnd(View v)
            {
            Log.i("Tag Date", "Clicked on End Date");
            datePickerEnd = new EndDatePickerFragment();
            datePickerEnd.show(getFragmentManager(), "datePickerEnd");
            }


      /* public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
            Log.i("Setting date", year + " " + month + " " + dayOfMonth);
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            ((TextView) findViewById(R.id.tv_bookingform_set_date_end)).setText(dateFormat.format(calendar.getTime()));
            }*/

        @Override
        public void onEndDateSet(int year, int month, int day)
            {
            Log.i("Booking", "Setting end date: " + year + "," + month + " , " + day);
            endYear = year;
            endMonth = month;
            endDay = day;
            ((TextView) findViewById(R.id.tv_bookingform_set_date_end)).setText( endDay + "-" + endMonth + "-" + endYear);
            }

        @Override
        public void onStartDateSet(int year, int month, int day)
            {
            Log.i("Booking", "Setting start date: " + year + ":" + month + " " + day);
            startYear = year;
            startMonth = month;
            startDay = day;

            ((TextView) findViewById(R.id.tv_bookingform_set_date_start)).setText(startDay + "-"+ startMonth + "-" + startYear) ;
            }


       /* public void setTime(int hour, int min)
            {
            Log.i("Setting time", hour + " " + min);
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            int hour2 = hour;
            int min2 = min;
            ((TextView) findViewById(R.id.tv_bookingform_set_time_start)).setText(hour2 + " : " + min2);
            }*/

        @Override
        public void onEndTimeSet(int hour, int minutes)
            {
            Log.i("Booking", "Setting end time: " + hour + ":" + minutes);
            endHour = hour;
            endMinute = minutes;
            ((TextView) findViewById(R.id.tv_bookingform_set_time_end)).setText(endHour + " : " + endMinute);
            }

        @Override
        public void onStartTimeSet(int hour, int minutes)
            {
            Log.i("Booking", "Setting start time: " + hour + ":" + minutes);
            startHour = hour;
            startMinute = minutes;
            ((TextView) findViewById(R.id.tv_bookingform_set_time_start)).setText(startHour + " : " + startMinute);
            }

    }



