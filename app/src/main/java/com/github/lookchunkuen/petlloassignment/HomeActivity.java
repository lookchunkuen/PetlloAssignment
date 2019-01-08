package com.github.lookchunkuen.petlloassignment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewUsername;
    private BottomNavigationView navigationView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewUsername = findViewById(R.id.textViewUsername);
        navigationView = findViewById(R.id.navigationView);
        sessionManager = new SessionManager(this);
        sessionManager.CheckLogin();

        HashMap<String, String> user = sessionManager.GetUserDetail();
        String username = user.get((sessionManager.NAME));
        textViewUsername.setText(username);


        //handle the bottom
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(HomeActivity.this, "Action Home Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_adoption:
                        Toast.makeText(HomeActivity.this, "Action Adoption Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_training:
                        Toast.makeText(HomeActivity.this, "Action Training Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(HomeActivity.this, "Action Profile Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void Logout(View v){
        sessionManager.Logout();
    }
}
