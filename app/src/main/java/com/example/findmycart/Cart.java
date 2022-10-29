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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    String[] name = {};

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> products;
    ArrayAdapter<String> arrayAdapter;
    Departments departments;

    ListView cartView;
    ArrayList<String> cart = new ArrayList<>();
    private static final String TAG = "Cart";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        departments = new Departments();
        cartView = findViewById(R.id.cartListView);

        nav_MainMenu();
        nav_Cart();
        nav_SavedLists();
        nav_Cart();
        nav_Products();

        backButton();
        startButtonClick();
        deleteButtonClick();

        database = FirebaseDatabase.getInstance();

        products = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_pro_view, R.id.productName, products);


        reference = FirebaseDatabase.getInstance().getReference("Cart");

        Query query = FirebaseDatabase.getInstance().getReference("Cart")
                .orderByChild("aisle");

        query.addListenerForSingleValueEvent(valueEventListener);

        cartView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                final int which_item = position;

                new AlertDialog.Builder(Cart.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Do you want to delete this product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String item = arrayAdapter.getItem(position);
                                arrayAdapter.remove(item);
                                //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Cart").child()
                                arrayAdapter.notifyDataSetChanged();

                                String name = "";

                                //String Product = arrayAdapter.getItem(position);
                                String[] separated = item.split("\\s+");
                                name = separated[0];


                Query deleteQuery = reference.orderByChild("name").equalTo(name);

                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot berrySnapshot: dataSnapshot.getChildren()) {
                            berrySnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });


                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });




    }

    ValueEventListener valueEventListener = (new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            for(DataSnapshot ds: snapshot.getChildren()){

                departments = ds.getValue(Departments.class);
                products.add(departments.getName() + "        "+departments.getPrice() + "        "+departments.getAisle());

            }
            cartView.setAdapter(arrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    public void startButtonClick() {

        ImageButton startButton = findViewById(R.id.cart_StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Cart.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }

    public void deleteButtonClick() {
        ImageButton deleteButton = findViewById(R.id.emptyCartButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue();
                arrayAdapter.clear();
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

    @Override
    public void onBackPressed() {
        Intent j = new Intent(Cart.this, MainActivity.class);
        startActivity(j);
    }


    public void nav_MainMenu() {

        ImageButton mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Cart.this, MainActivity.class);
                startActivity(j);

            }
        });

    }

    public void nav_Products() {

        ImageButton productsButton = findViewById(R.id.products);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Cart.this, Products.class);
                startActivity(j);

            }
        });

    }

    public void nav_Cart() {

        ImageButton newList = findViewById(R.id.newList);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(Cart.this, Cart.class);
                startActivity(j);

            }
        });

    }

    public void nav_SavedLists() {

        ImageButton savedLists = findViewById(R.id.savedLists);
        savedLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Cart.this, RouteGuidance.class);
                startActivity(j);

            }
        });

    }



}
