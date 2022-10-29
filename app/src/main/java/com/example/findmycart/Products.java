//
package com.example.findmycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.MenuItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.ActionBar;

public class Products extends AppCompatActivity {

    private ListView myListView;
    private ListView IDs;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Cart");
    private final ArrayList<String> groups = new ArrayList<>();
    ArrayAdapter<Departments> adapter;

    long maxid=0;



   /* ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ArrayAdapter<String> arrayAdapter;


    private ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        nav_MainMenu();
        nav_NewList();
        nav_SavedLists();
        nav_Products();

        actionBackButton();
        startButtonClick();


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Display");


        myListView = findViewById(R.id.listView1);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Products.this, android.R.layout.simple_list_item_1, groups);
        myListView.setAdapter(arrayAdapter);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                groups.add(value);
                String Name = snapshot.getKey();
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

                int num = 0;

                //String Product = groups.get(position);
                if (position == 0) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 1;
                    pro.putExtra("number",num);
                    startActivity(pro);
                }
                else if (position == 1) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 2;
                    pro.putExtra("number",num);
                    startActivity(pro);
                }
                else if (position == 2) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 3;
                    pro.putExtra("number",num);
                    startActivity(pro);
                }
                else if (position == 3) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 4;
                    pro.putExtra("number",num);
                    startActivity(pro);
                }
                else if (position == 4) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 5;
                    pro.putExtra("number",num);
                    startActivity(pro);
                }
                else if (position == 5) {
                    Intent pro = new Intent(Products.this, Products2.class);
                    num = 6;
                    pro.putExtra("number",6);
                    startActivity(pro);
                }


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

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent j = new Intent(Products.this, MainActivity.class);
        startActivity(j);
    }

    public void startButtonClick() {

        ImageButton startButton = findViewById(R.id.pro_StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Products.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }


    public void nav_MainMenu() {

        ImageButton mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products.this, MainActivity.class);
                startActivity(j);

            }
        });

    }

    public void nav_Products() {

        ImageButton productsButton = findViewById(R.id.products);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products.this, Products.class);
                startActivity(j);

            }
        });

    }

    public void nav_NewList() {

        ImageButton newList = findViewById(R.id.newList);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Products.this, Cart.class);
                startActivity(j);

            }
        });

    }

    public void nav_SavedLists() {

        ImageButton savedLists = findViewById(R.id.savedLists);
        savedLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Products.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }

}
