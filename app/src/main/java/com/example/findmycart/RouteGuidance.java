//
package com.example.findmycart;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class RouteGuidance extends AppCompatActivity{

    FirebaseDatabase database;
    DatabaseReference reference;
    TextView currentItemName,currentItemLocation,nextItemName,nextItemLocation,aisleLocation;
    Button butNext;

    int key = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_guidance);

        nav_MainMenu();
        nav_SavedLists();
        nav_NewList();
        nav_Products();

        backButton();

        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        currentItemName = findViewById(R.id.currentItemName);
        currentItemLocation = findViewById(R.id.currentItemLocation);
        nextItemName = findViewById(R.id.nextItemName);
        nextItemLocation = findViewById(R.id.nextItemLocation);
        aisleLocation = findViewById(R.id.location);
        butNext = findViewById(R.id.nextItemButton);


        getInitRouteData();
        nextItemClick();

    }

    public void getInitRouteData() {

        key = key + 1;

        Query query = reference.child("Cart")
                .orderByChild("aisle")
                .limitToFirst(key);

        final Query query1 = reference.child("Cart")
                .orderByChild("aisle")
                .limitToFirst(1+key);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String cName = dataSnapshot.child("name").getValue().toString();
                    String cAis = dataSnapshot.child("aisle").getValue().toString();
                    String cLoc = dataSnapshot.child("location").getValue().toString();
                    //String cSec = dataSnapshot.child("section").getValue().toString();
                    //String cNum = dataSnapshot.child("itemNumber").getValue().toString();
                    //String cFLoc = (cAis + "-" + cSec + "-" + cNum);

                    currentItemName.setText(cName);
                    currentItemLocation.setText(cAis);
                    //currentItemLocation.setText(cFLoc);
                    aisleLocation.setText(cLoc);
                    //currentItemSection.setText(cSec);
                    //currentItemNumber.setText(cNum);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    //key2 = key2 + 1;
                    String cName = dataSnapshot.child("name").getValue().toString();
                    String cLoc = dataSnapshot.child("aisle").getValue().toString();

                    nextItemName.setText(cName);
                    nextItemLocation.setText(cLoc);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void nextItemClick() {

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInitRouteData();

            }
        });

    }

    public void backButton() {

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        //actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nav_MainMenu() {

        ImageButton mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(RouteGuidance.this, MainActivity.class);
                startActivity(j);

            }
        });

    }

    public void nav_Products() {

        ImageButton productsButton = findViewById(R.id.products);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(RouteGuidance.this, Products.class);
                startActivity(j);

            }
        });

    }

    public void nav_NewList() {

        ImageButton newList = findViewById(R.id.newList);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(RouteGuidance.this, Cart.class);
                startActivity(j);

            }
        });

    }

    public void nav_SavedLists() {

        ImageButton savedLists = findViewById(R.id.savedLists);
        savedLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(RouteGuidance.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }

}
