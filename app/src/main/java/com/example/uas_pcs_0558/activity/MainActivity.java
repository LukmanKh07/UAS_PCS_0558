package com.example.uas_pcs_0558.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.uas_pcs_0558.R;
import com.example.uas_pcs_0558.adapter.AdapterAllLeague;
import com.example.uas_pcs_0558.httpclient.ApiInterface;
import com.example.uas_pcs_0558.httpclient.retrofitclient;
import com.example.uas_pcs_0558.model.CountrysItem;
import com.example.uas_pcs_0558.model.ResponseAllLeague;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvLeague)
    RecyclerView rvLeague;

    Context context;
    AdapterAllLeague adapter;
    List<CountrysItem> items=new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        context=this;

        adapter= new AdapterAllLeague(context,items);

        rvLeague.setLayoutManager(new GridLayoutManager(context,2));
        rvLeague.setAdapter(adapter);

        apiInterface = retrofitclient.getRetrofitClient().create(ApiInterface.class);
        getAllLeague();
    }

    public void getAllLeague(){
        Call<ResponseAllLeague> api=apiInterface.getAllLeague();
        api.enqueue(new Callback<ResponseAllLeague>() {
            @Override
            public void onResponse(Call<ResponseAllLeague> call, Response<ResponseAllLeague> response) {
                if(response.isSuccessful()){
                    adapter.setItems(response.body().getCountrys());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ResponseAllLeague> call, Throwable t) {

            }
        });
    }
}