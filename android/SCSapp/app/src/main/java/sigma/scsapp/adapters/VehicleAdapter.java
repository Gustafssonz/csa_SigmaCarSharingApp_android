package sigma.scsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sigma.scsapp.R;
import sigma.scsapp.model.Vehicle;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by christopher on 2017-10-15.
 */
public class VehicleAdapter extends ArrayAdapter {
    private List<Vehicle> vehicleList;
    private int resource;
    private LayoutInflater inflater;

    public VehicleAdapter(Activity activity, Context context, int resource, List<Vehicle> objects) {
        super(context, resource, objects);
        vehicleList = objects;
        Log.i("VehicleAdapter", "vehicleList got info: " + vehicleList);
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
            // holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            //holder.vehicleId = (TextView) convertView.findViewById(R.id.tvVehicleId);
            //holder.reg = (TextView) convertView.findViewById(R.id.tvReg);
            //  holder.year = (TextView) convertView.findViewById(R.id.tv_item_name);
            // holder.mileage = (TextView) convertView.findViewById(R.id.tvMileage);
            // holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            // holder.equipment = (TextView) convertView.findViewById(R.id.tvEquipment);
            holder.model = (TextView) convertView.findViewById(R.id.tvModel);
            holder.site = (TextView) convertView.findViewById(R.id.tvSite);
            // holder.responsible = (TextView) convertView.findViewById(R.id.tvResponsible);
            // holder.vehicleImage = (ImageView) convertView.findViewById(R.id.tvPurpose);
            // holder.isAvalible = (TextView) convertView.findViewById(R.id.tvIsAvalible);
            // holder.vehicleImageLink = (TextView) convertView.findViewById(R.id.tvVehicleImageLink);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //   holder.tvId.setText("Id" + vehicleList.get(position).getId());
        //  holder.vehicleId.setText(vehicleList.get(position).getVehicleId());
        //  holder.reg.setText(vehicleList.get(position).getReg());
        //  holder.year.setText(vehicleList.get(position).getYear());
        // holder.mileage.setText(vehicleList.get(position).getMileage());
        // holder.body.setText(vehicleList.get(position).getBody());
        //  holder.equipment.setText(vehicleList.get(position).getEquipment());
        holder.model.setText(vehicleList.get(position).getModel());
        holder.site.setText(vehicleList.get(position).getSite());
        // holder.responsible.setText(vehicleList.get(position).getResponsible());
        // holder.vehicleImage.setimage(vehicleList.get(position).getVehicleImage());
        // holder.vehicleImageLink.setText(vehicleList.get(position).getVehicleImageLink());
        return convertView;

    }

    class ViewHolder {
        private TextView vehicleId;
        private TextView reg;
        private TextView year;
        private TextView mileage;
        private TextView body;
        private TextView equipment;
        private TextView model;
        private TextView fuel;
        private TextView site;
        private TextView responsible;
        private ImageView vehicleImage;
        private TextView isAvalible;
        private TextView vehicleImageLink;
    }
}