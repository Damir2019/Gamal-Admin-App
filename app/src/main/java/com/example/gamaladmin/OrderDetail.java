package com.example.gamaladmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        Orders order = (Orders) intent.getSerializableExtra("order");

        TextView clientNumberTV, departureTV, orderTimeTV;
        Button doneBtn;

        clientNumberTV = findViewById(R.id.clientNumber);
        departureTV = findViewById(R.id.departure);
        orderTimeTV = findViewById(R.id.orderTime);
        doneBtn = findViewById(R.id.doneBtn);

        assert order != null;

        clientNumberTV.setText(order.clientNumber);
        departureTV.setText(order.departure);
        orderTimeTV.setText(order.time + " " + order.date);

        setBackButton(doneBtn);
    }

    private void setBackButton(Button doneBtn) {
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
