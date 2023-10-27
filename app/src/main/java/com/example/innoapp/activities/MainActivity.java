package com.example.innoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import com.example.innoapp.R;
import com.example.innoapp.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

   Fragment homefragment;

   FirebaseAuth auth;

   Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       getActionBar().show();
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);



        homefragment = new HomeFragment();
        loadFragment(homefragment);

    }

    private void loadFragment(Fragment homefragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homefragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id =  item.getItemId();

        if(id == R.id.menu_logout)
        {
              auth.signOut();
              startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
               finish();
        }
        else if(id == R.id.menu_my_cart)
        {
          startActivity(new Intent(MainActivity.this, CartActivity.class));
        }

      return  true;
    }

}