package dragoncode.unipark;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ParkingAdapter extends ArrayAdapter<parking_name_item> {

    public ParkingAdapter(Context context, ArrayList<parking_name_item> parkinglist){
        super(context, 0,parkinglist);
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return InitView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return InitView(position, convertView, parent);
    }

    private View InitView (int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.parking_name_spinner, parent, false);
        }

        TextView parkingname = convertView.findViewById(R.id.textView3);

        parking_name_item currentItem = getItem(position);

        parkingname.setText("AL001");

        return convertView;
    }
}
