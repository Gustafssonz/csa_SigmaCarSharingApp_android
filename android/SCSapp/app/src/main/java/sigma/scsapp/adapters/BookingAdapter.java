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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by christopher on 2017-10-15.
 */

public class BookingAdapter extends ArrayAdapter {
    private List<Booking> bookingList;
    private int resource;
    private LayoutInflater inflater;

    public BookingAdapter(Activity activity, Context context, int resource, List<Booking> objects) {
        super(context, resource, objects);
        bookingList = objects;
        Log.i("VehicleAdapter", "bookingList got info: " + bookingList);
        this.resource = resource;
        inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("VehicleAdapter", "Starting the VehicleAdapter");
        ViewHolder holder = null;
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
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvStartDate.setText(bookingList.get(position).getStartingDate());
        holder.tvStartTime.setText(bookingList.get(position).getStartingTime());
        holder.tvEndDate.setText(bookingList.get(position).getEndingDate());
        holder.tvEndTime.setText(bookingList.get(position).getEndingTime());
        holder.tvErrand.setText(bookingList.get(position).getErrand());
        holder.tvDestination.setText(bookingList.get(position).getDestination());
        holder.tvPurpose.setText(bookingList.get(position).getPurpose());

        return convertView;
    }

    class ViewHolder {
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