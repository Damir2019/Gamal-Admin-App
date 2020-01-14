package com.example.gamaladmin;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Orders implements Serializable {

    public String clientNumber, departure, date, time, referenceID;

    public Orders() {
    }

    @NonNull
    @Override
    public String toString() {
        return "Full order description: \n " +
                "Client Number: " + clientNumber + "\n" +
                "Departure from: " + departure + "\n" +
                "Order was made in: " + time + " " + date + " .";
    }
}
