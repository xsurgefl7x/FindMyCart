
package com.example.findmycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_MainMenu();
        nav_Cart();
        nav_SavedLists();
        nav_Cart();
        nav_Products();

        backButton();


    }



    //Button StartShoppingButton = findViewById(R.id.startButton);


    //code to switch between activities on button click

    /*
    View.OnClickListener handler = new View.OnClickListener(){
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.DownloadView:
                    // doStuff
                    startActivity(new Intent(ThisActivity.this, DownloadActivity.class));
                    break;
                case R.id.AppView:
                    // doStuff
                    startActivity(new Intent(ThisActivity.this, AppActivity.class));
                    break;
            }
        }
    };

    findViewById(R.id.DownloadView).setOnClickListener(handler);
    findViewById(R.id.AppView).setOnClickListener(handler);

     */


    /*public void mainSearch() {

        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button


            }
        });

    }

     */

    public void StartShopping(View x) {

                Intent i = new Intent(MainActivity.this, Products.class);
                startActivity(i);

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

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);

            }
        });

    }

    public void nav_Products() {

        ImageButton productsButton = findViewById(R.id.products);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String str = "";
                    //String st;
                    try {
                        InputStream inputStream = getAssets().open("TestDatabase.txt");
                        int size = inputStream.available();
                        byte[] buffer = new byte[size];
                        inputStream.read(buffer);
                        str = new String(buffer);
                    } catch (IOException e){
                        e.printStackTrace();
                    }


                    Intent i= new Intent(MainActivity.this,Products.class);
                    i.putExtra("Data",str);
                    startActivity(i);
                    finish();


                }

            }
        });

    }

    public void nav_Cart() {

        ImageButton newList = findViewById(R.id.newList);
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Cart.class);
                startActivity(i);

            }
        });

    }

    public void nav_SavedLists() {

        ImageButton savedLists = findViewById(R.id.savedLists);
        savedLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RouteGuidance.class);
                startActivity(i);

            }
        });

    }

}
