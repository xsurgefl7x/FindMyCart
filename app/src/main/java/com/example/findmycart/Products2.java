package com.example.findmycart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Products2 extends AppCompatActivity {

    private ListView myListView;
    private ListView IDs;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Cart");
    ArrayList<String> products;
    ArrayAdapter<String> arrayAdapter;
    Departments departments;

    long maxid = 0;
    int proView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products2);

        departments = new Departments();
        myListView = (ListView) findViewById(R.id.listView1);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("");
        products = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_pro_view, R.id.productName, products);

        nav_MainMenu();
        nav_SavedLists();
        nav_NewList();
        nav_Products();

        actionBackButton();
        backButtonClick();
        startButtonClick();

        final Intent pro = getIntent();
        proView = pro.getIntExtra("number", 1);

        if (proView == 1) {
            reference = database.getReference("Products/Adult Beverages");

        } else if (proView == 2) {
            reference = database.getReference("Products/Bread");

        } else if (proView == 3) {
            reference = database.getReference("Products/Dairy");

        } else if (proView == 4) {
            reference = database.getReference("Products/Fruits & Vegetables");

        } else if (proView == 5) {
            reference = database.getReference("Products/Meat & Seafood");

        } else if (proView == 6) {
            reference = database.getReference("Products/Pantry");

        }


        /*myListView = findViewById(R.id.listView1);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Products2.this, android.R.layout.simple_list_item_1,products);
        myListView.setAdapter(arrayAdapter);

         */

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    departments = ds.getValue(Departments.class);
                    products.add(departments.getName() + "               "+ departments.getPrice() + "         "+departments.getAisle() + "               "+departments.getLocation());

                }
                myListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid = (snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //String value = snapshot.getValue(String.class);
                //products.add(value);
                //String Name = snapshot.getKey();
                arrayAdapter.notifyDataSetChanged();
                //ad.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
                //ad.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Products2.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Do you want to add this product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String name = "";
                                String aisle = "";
                                String price = "";
                                String location = "";

                                String Product = products.get(position);
                                String[] separated = Product.split("\\s+");
                                name = separated[0];
                                price = separated[1];
                                aisle = separated[2];
                                location = separated[3];

                                HashMap<String, String> userMap = new HashMap<>();

                                userMap.put("name", name);
                                userMap.put("aisle", aisle);
                                userMap.put("price", price);
                                userMap.put("location", location);

                                root.push().setValue((userMap));
                                //child(String.valueOf(maxid + 1))

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });


    }

    public void actionBackButton() {

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        //actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void backButtonClick() {

        ImageButton backButton = findViewById(R.id.pro2_BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Products2.this, Products.class);
                startActivity(j);

            }
        });

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

    @Override
    public void onBackPressed() {
        Intent j = new Intent(Products2.this, MainActivity.class);
        startActivity(j);
    }

    public void startButtonClick() {

        ImageButton startButton = findViewById(R.id.pro2_StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Products2.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }


    public void nav_MainMenu() {

        ImageButton mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products2.this, MainActivity.class);
                startActivity(j);

            }
        });

    }

    public void nav_Products() {

        ImageButton productsButton = findViewById(R.id.products);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products2.this, Products.class);
                startActivity(j);

            }
        });

    }

    public void nav_NewList() {

        ImageButton newList = findViewById(R.id.newList);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products2.this, Cart.class);
                startActivity(j);

            }
        });

    }

    public void nav_SavedLists() {

        ImageButton savedLists = findViewById(R.id.savedLists);
        savedLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Products2.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }

}
