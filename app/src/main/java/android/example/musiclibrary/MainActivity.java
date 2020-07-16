package android.example.musiclibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView videoList;
    List<Video> all_videos;
    videoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_videos = new ArrayList<>();

        videoList = findViewById(R.id.videolist);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new videoAdapter(this, all_videos);
        videoList.setAdapter(adapter);
        getJsonData();
    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray cateogries = response.getJSONArray("categories");
                    JSONObject cateogriesData = cateogries.getJSONObject(0);
                    JSONArray videos = cateogriesData.getJSONArray("videos");
                    for (int i = 0; i < videos.length(); i++) {
                        JSONObject video = videos.getJSONObject(i);
                        Video v = new Video();
                        v.setTitle(video.getString("title"));
                        v.setDescription(video.getString("description"));
                        v.setAuthor(video.getString("subtitle"));
                        v.setImageUrl(video.getString("thumb"));
                        JSONArray videoUrl = video.getJSONArray("sources");
                        v.setVideoUrl(videoUrl.getString(0));
                        all_videos.add(v);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError Error) {

            }
        });
        requestQueue.add(objectRequest);
    }
}