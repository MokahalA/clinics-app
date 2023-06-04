package com.example.seg2105project;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.List;

public class ServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service>services;

    public ServiceList( Activity context,  List<Service> services) {
        super(context, R.layout.layout_service_list, services);
        this.context = context;
        this.services = services;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem =inflater.inflate(R.layout.layout_service_list, null, true);

        TextView textViewService= (TextView) listViewItem.findViewById(R.id.textViewService);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);
        TextView textViewRate =  (TextView) listViewItem.findViewById(R.id.tv_rate);

        Service service = services.get(position);
        textViewService.setText(" SERVICE: " + service.getName());
        textViewRole.setText(" ROLE: " + service.getRole());


        if (service.getRate() != 0.0){
            textViewRate.setText("$" + service.getRate());
        }


        return listViewItem;
    }
}
