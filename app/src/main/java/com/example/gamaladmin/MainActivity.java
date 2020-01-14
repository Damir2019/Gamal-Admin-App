package com.example.gamaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private OrderAdapter orderAdapter;
    private ArrayList<Orders> orderList;
    private ListView orderListView;
    private boolean changed = false;
    private int dataRemain = 0;
    private boolean appStartFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate the array list
        orderList = new ArrayList<>();

        // get the instance on the fire base database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Gamal-Taxi-Orders");

        // get the database from fire base
        getDatabaseFromFireBase();

        // initializing the list into the list view
        setListView();
    }

    public void getDatabaseFromFireBase(){
        // event listener for data changes
        // a listener that listening to changes in the data base and if there were
        // this function onDataChange will execute, and give us all the data
        // that the database contains
        mDatabase.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // data has changed so this function is executed
                // clear all data from array and rebuild it
                // so all the data was there before will not be reused
                // allows us to get the current data on the fire base database
                orderAdapter.clear();

                // the getChildren func gives me an array of data
                // we iterate this array to get each object in it
                // and add it to our orders list
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    Orders order = child.getValue(Orders.class);
                    if (order == null) { return; }
                    order.referenceID = child.getKey();
                    orderAdapter.add(order);

                    setListView();
                }

                // change the boolean to true only when there is a change in the data base
                changed = true;

                // play a ringtone to to notify about data changes
                // also check if the app was started for the first time
                // if it does i don't want music to play
                // after that i do
                if ( appStartFirstTime ) { playMusic(); } else { appStartFirstTime = true; }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "תקלה במשיכת נתונים, אנא וודא שיש חיבור אינטרנט.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setListView(){
        // connect to the list view on the layout
        orderListView = findViewById(R.id.orderList);
        orderListView.setClickable(true);

        // initiate the order adapter
        orderAdapter = new OrderAdapter(getApplicationContext(), R.layout.order_item, orderList);

        orderListView.setAdapter(orderAdapter);
        setOnLongClickListener();
        setOnClickEventListener();
    }

    public void setOnClickEventListener() {
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), OrderDetail.class);
                intent.putExtra("order", orderAdapter.getItem(orderList.size() - position - 1));
                startActivity(intent);
            }
        });
    }

    public void setOnLongClickListener() {
        orderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                System.out.println("long click");

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("מחיקת הזמנה")
                        .setMessage("האם למחוק את ההזמנה?")
                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child("Order").child(orderList.get(orderList.size() - position - 1).referenceID).removeValue();
                                dataRemain = orderList.size();
                            }
                        })
                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).show();
                return true;
            }
        });

    }

    public void playMusic() {

        if (dataRemain >= orderList.size()) {
            dataRemain = orderList.size();
            return;
        }

        if (changed) {
            MediaPlayer player = MediaPlayer.create(this, R.raw.notification);
            player.start();

            // make sure to play only once on a data changed
            changed = false;
        }
        dataRemain = orderList.size();
    }

}
