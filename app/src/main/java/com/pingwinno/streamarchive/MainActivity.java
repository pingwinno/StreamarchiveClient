package com.pingwinno.streamarchive;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    List<StreamMeta> streamsList = new ArrayList<>();
    StreamsDataGetter streamsDataGetter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private StreamAdapter streamAdapter;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            StreamMeta streamMeta = streamsList.get(position);
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            intent.putExtra("url", "https://storage.streamarchive.net/streams/olyashaa/" + streamMeta.getUuid() + "/index-dvr.m3u8");
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        streamsDataGetter = new StreamsDataGetter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        try {
            new CityInfoRunner().execute(0).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        streamAdapter = new StreamAdapter(this, streamsList);
        streamAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(streamAdapter);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.e("OFFSET", String.valueOf(totalItemsCount));

                new CityInfoRunner().execute(totalItemsCount);

            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);


    }

    private class CityInfoRunner extends AsyncTask<Integer, Void, List<StreamMeta>> {

        @Override
        protected List<StreamMeta> doInBackground(Integer... values) {


            try {
                return streamsDataGetter.getStreams(values[0]);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(List<StreamMeta> strings) {
            streamsList.addAll(strings);
            streamAdapter.notifyDataSetChanged();
        }
    }
}
