package sigma.scsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.model.Booking;
import sigma.scsapp.model.Vehicle;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by christopher on 2017-10-15.
 */

public class BookingAdapter extends ArrayAdapter {
    private List<Booking> bookingList;
    private List<Vehicle> vehicleList;
    private int resource;
    private LayoutInflater inflater;

    public BookingAdapter(Activity activity, Context context, int resource, List<Booking> bookings, List<Vehicle> vehicles) {
        super(context, resource, bookings);
        bookingList = bookings;
        vehicleList = vehicles;
        Log.i("BookingAdapter", "bookingList got info: " + bookingList);
        this.resource = resource;
        inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("BookingAdapter", "Starting the BookingAdapter");
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(resource, null);
            holder.tvStartDate = convertView.findViewById(R.id.tvStartDate);
            holder.tvStartTime = convertView.findViewById(R.id.tvStartTime);
            holder.tvEndDate = convertView.findViewById(R.id.tvEndDate);
            holder.tvEndTime = convertView.findViewById(R.id.tvEndTime);
            holder.tvErrand = convertView.findViewById(R.id.tvErrand);
            holder.tvDestination = convertView.findViewById(R.id.tvDestination);
            holder.tvPurpose = convertView.findViewById(R.id.tvPurpose);
            holder.tvModel = convertView.findViewById(R.id.tvModel);
            holder.tvSite = convertView.findViewById(R.id.tvSite);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Booking booking = bookingList.get(position);
        Vehicle vehicle = getVehicleById(booking.getVehicleId());

        holder.tvStartDate.setText(booking.getStartingDate());
        holder.tvStartTime.setText(booking.getStartingTime());
        holder.tvEndDate.setText(booking.getEndingDate());
        holder.tvEndTime.setText(booking.getEndingTime());
        holder.tvErrand.setText(booking.getErrand());
        holder.tvDestination.setText(booking.getDestination());
        holder.tvPurpose.setText(booking.getPurpose());

        if (vehicle != null) {
            holder.tvModel.setText(vehicle.getModel());
            holder.tvSite.setText(vehicle.getSite());
        }

        return convertView;
    }

    private Vehicle getVehicleById(String id) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getVehicleId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    class ViewHolder {
        private TextView tvModel;
        private TextView tvSite;
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