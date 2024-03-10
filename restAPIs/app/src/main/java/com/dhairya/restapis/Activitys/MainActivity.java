package com.dhairya.restapis.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dhairya.restapis.apis.Apimodel;
import com.dhairya.restapis.apis.apiInterface;
import com.dhairya.restapis.apis.postApi;
import com.dhairya.restapis.apis.postModel;
import com.dhairya.restapis.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    String name,email,password;
    private ActivityMainBinding binding;
    String baseurl = "http://localhost/Rest%20APIs/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  getmethod(); //get and dislay data in text view
        name = binding.edtName.getText().toString();
        email = binding.edtEmail.getText().toString();
        password = binding.edtPassword.getText().toString();

        binding.edtBtnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
                Toast.makeText(MainActivity.this, "workes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void postdata() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();

        postApi postApi = retrofit.create(com.dhairya.restapis.apis.postApi.class);

        Call<postModel> call = postApi.addmodel(name,email,password);
        call.enqueue(new Callback<postModel>() {
            @Override
            public void onResponse(Call<postModel> call, Response<postModel> response) {
                Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<postModel> call, Throwable t) {
                Log.d("error", t.toString());
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void getmethod() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface apiInterface = retrofit.create(com.dhairya.restapis.apis.apiInterface.class);//create an interface class
        Call<List<Apimodel>> call = apiInterface.getdata();

        call.enqueue(new Callback<List<Apimodel>>() {
            @Override
            public void onResponse(Call<List<Apimodel>> call, Response<List<Apimodel>> response) {
                List<Apimodel> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    binding.txt.append("UserID: " + data.get(i).getUserId() + "\n" + "Id: " + data.get(i).getId() + "\n" + "Title: " + data.get(i).getTitle() + "\n" + "Body" + data.get(i).getBody() + "\n\n");
                }
            }

            @Override
            public void onFailure(Call<List<Apimodel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
