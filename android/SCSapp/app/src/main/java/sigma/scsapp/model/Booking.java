package sigma.scsapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sigma.scsapp.controllers.JSONTaskBooking;

/**
 * Created by Niklas on 2017-10-03.
 */

public class Booking
    {

        private String id;
        private String timeOfBooking;
        private String startingDate;
        private String startingTime;
        private String endingDate;
        private String endingTime;
        private String errand;
        private String destination;
        private String purpose;
        private Boolean isConfirmed;
        private String userId;
        private String vehicleId;

        public Booking()
            {
            }

        public Booking(String id, String timeOfBooking, String startingDate, String startingTime, String endingDate, String endingTime, String errand, String destination, String purpose, Boolean isConfirmed, String userId, String vehicleId)
            {
            this.id = id;
            this.timeOfBooking = timeOfBooking;
            this.startingDate = startingDate;
            this.startingTime = startingTime;
            this.endingDate = endingDate;
            this.endingTime = endingTime;
            this.errand = errand;
            this.destination = destination;
            this.purpose = purpose;
            this.isConfirmed = isConfirmed;
            this.userId = userId;
            this.vehicleId = vehicleId;
            }

        public String getId()
            {
            return id;
            }

        public void setId(String id)
            {
            this.id = id;
            }

        public String getTimeOfBooking()
            {
            return timeOfBooking;
            }

        public void setTimeOfBooking(String timeOfBooking)
            {
            this.timeOfBooking = timeOfBooking;
            }

        public String getStartingDate()
            {
            return startingDate;
            }

        public void setStartingDate(String startingDate)
            {
            this.startingDate = startingDate;
            }

        public String getStartingTime()
            {
            return startingTime;
            }

        public void setStartingTime(String startingTime)
            {
            this.startingTime = startingTime;
            }

        public String getEndingDate()
            {
            return endingDate;
            }

        public void setEndingDate(String endingDate)
            {
            this.endingDate = endingDate;
            }

        public String getEndingTime()
            {
            return endingTime;
            }

        public void setEndingTime(String endingTime)
            {
            this.endingTime = endingTime;
            }

        public String getErrand()
            {
            return errand;
            }

        public void setErrand(String errand)
            {
            this.errand = errand;
            }

        public String getDestination()
            {
            return destination;
            }

        public void setDestination(String destination)
            {
            this.destination = destination;
            }

        public String getPurpose()
            {
            return purpose;
            }

        public void setPurpose(String purpose)
            {
            this.purpose = purpose;
            }

        public Boolean getIsConfirmed()
            {
            return isConfirmed;
            }

        public void setIsConfirmed(Boolean confirmed)
            {
            isConfirmed = confirmed;
            }

        public String getUserId()
            {
            return userId;
            }

        public void setUserId(String userId)
            {
            this.userId = userId;
            }

        public String getVehicleId()
            {
            return vehicleId;
            }

        public void setVehicleId(String vehicleId)
            {
            this.vehicleId = vehicleId;
            }

       /* public List<Booking> getBookingsFromUser(String userId){
        JSONTaskBooking jtb = new JSONTaskBooking();
        jtb.getJSONArray();
        Log.i("Tag for JSONARray", " "+ jtb.getJSONArray());
        List<Booking> listOfBookings = new ArrayList<>();
        return null;*/


        @Override
        public String toString()
            {
            return "Booking{" +
                    "id=" + id +
                    ", timeOfBooking='" + timeOfBooking + '\'' +
                    ", startingDate='" + startingDate + '\'' +
                    ", startingTime='" + startingTime + '\'' +
                    ", endingDate='" + endingDate + '\'' +
                    ", endingTime='" + endingTime + '\'' +
                    ", errand='" + errand + '\'' +
                    ", destination='" + destination + '\'' +
                    ", purpose='" + purpose + '\'' +
                    ", isConfirmed=" + isConfirmed +
                    '}';
            }
    }
