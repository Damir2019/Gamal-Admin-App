package com.example.gamaladmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Orders> {

    private Context mContext;
    private int mResource;
    private ArrayList<Orders> mOrderList;

    OrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Orders> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mOrderList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // if the convertView is null we need to inflate it
        // so we have a layout to show out data in
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        // get a single order from array
        Orders orders = mOrderList.get(mOrderList.size() - position - 1);
        // declare the variables the will contain the single object data to use
        String clientNumber, departure;

        // get a reference to layout views
        TextView clientNumberTV, departureTV;
        LinearLayout orderLayout;

        // initiate the variables with objects data
        clientNumber = orders.clientNumber;
        departure = orders.departure;

        // connect the TextViews to their views in the orderLayout
        clientNumberTV = convertView.findViewById(R.id.clientNumber);
        departureTV = convertView.findViewById(R.id.departure);
        orderLayout = convertView.findViewById(R.id.orderLayout);

        // set the data for each object in the views
        clientNumberTV.setText(clientNumber);
        departureTV.setText(departure);

        // make the list a little nicer to look at
        if (position % 2 == 0) {
            orderLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            orderLayout.setBackgroundColor(Color.parseColor("#aaaaaa"));
        }

//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//                alertDialog.setTitle("Delete selected order");
//                alertDialog.setMessage("Are you sure you want to delete?");
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog alert = alertDialog.create();
//                alert.show();
//
//
//
//                return true;
//            }
//        });



        // return the convertView that contains all of out data to show it on the screen
        return convertView;
    }
}
