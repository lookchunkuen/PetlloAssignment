package com.github.lookchunkuen.petlloassignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.RegionIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername,editTextEmail, editTextPassword;
    private Button buttonRegister, buttonGoToLogin;
    private static String URL_REGISTER = "https://petllo.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonGoToLogin = findViewById(R.id.buttonGoToLogin);
    }

    public void GoToLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void Register(View v){
        if(editTextUsername.getText().toString().isEmpty()){
            editTextUsername.setError("Please enter username");
        }
        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("Please enter email");
        }
        if(editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("Please enter password");
        }else{
            Users users = new Users();

            users.setUsername(editTextUsername.getText().toString());
            users.setEmail(editTextEmail.getText().toString());
            users.setPassword(editTextPassword.getText().toString());

            try {
                makeServiceCall(this, URL_REGISTER, users);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void makeServiceCall(Context context, String url, final Users users) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        String message = jsonObject.getString("message");
                        if (success==1) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Error" + response, Toast.LENGTH_LONG).show();
                    }
                }
                },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", users.getUsername());
                    params.put("email", users.getEmail());
                    params.put("password", users.getPassword());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
