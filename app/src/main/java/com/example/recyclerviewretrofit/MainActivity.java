package com.example.recyclerviewretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApPost;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPosts();

    }

    private void getPosts() {

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApPost = retrofit.create(JsonPlaceHolderApi.class);


        Map<String, String> parmeters = new HashMap<>();
        parmeters.put("_sort", "id");
        parmeters.put("_order", "desc");

        Call<ArrayList<Post>> call = jsonPlaceHolderApPost.getPosts(parmeters);

        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code : "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Post>  postsList = response.body();


                buildRecyclerView(postsList);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buildRecyclerView(final ArrayList<Post>  postsList) {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PostAdapter(postsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickItem(position,postsList);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position,postsList);
            }


        });
    }

    private void removeItem(int position,ArrayList<Post>  postsList) {
        postsList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void clickItem(int position,ArrayList<Post>  postsList) {

        Toast.makeText(this, "Clicked : "+ postsList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
