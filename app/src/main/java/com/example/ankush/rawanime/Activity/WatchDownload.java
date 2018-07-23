package com.example.ankush.rawanime.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ankush.rawanime.models.AnimeModel;
import com.example.ankush.rawanime.models.EpisodeDataModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchDownload extends AppCompatActivity {
    String url;
    String videoUrl;
    ArrayList <EpisodeDataModel> servers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        servers=new ArrayList<>();
        url = intent.getStringExtra("url");
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        MyAsyncTask task=new MyAsyncTask();
        task.execute(url);
    }



    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String,List<AnimeModel>,Void> {
        @Override
        protected Void doInBackground(String... url) {

            try {
                Document doc = Jsoup.connect(url[0]).get();

                Elements container= doc.select("div.anime_muti_link");
                Elements container1= container.select("ul");
                Elements link = container1.select("li"); // a with href
                for(Element li:link) {
                    String embeded= li.select("a").attr("data-video");
                    String serverName= li.select("a").text();
                    servers.add(new EpisodeDataModel(serverName,embeded));
                    Log.d("sjdnckkd", li.select("a").text());
                    Log.d("sjdnckkd", "aj");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // Stuff that updates the UI
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    //   i.setData(Uri.parse(videoUrl));
                    // startActivity(i);
                    //finish();
                }
            });

        }

    }

}
