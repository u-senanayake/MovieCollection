package senanayake.udayanga.com.moviecollection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMovies();
    }

    public void viewMovies() {
        String url = "http://dev.appslanka.com/android-test/movies.php";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                processFinish(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
    }


    @Override
    public void processFinish(JSONObject object) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        ArrayList<Theater> theaterArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dataObject = jsonArray.getJSONObject(i);
                JSONArray theaterArray = dataObject.getJSONArray("theater");


                if (checkDate(dataObject.getString("end_date"))) {
                    LoadImage loadImage = new LoadImage();
                    Bitmap bitmap = loadImage.execute(dataObject.getString("portrait_image")).get();
                    arrayList.add(new Movie(
                            dataObject.getString("movie_name"),
                            dataObject.getString("booking_start_date"),
                            dataObject.getString("date_release"),
                            dataObject.getString("end_date"),
                            dataObject.getInt("imdb_rate"),
                            bitmap
                    ));

                    for (int j = 0; j < theaterArray.length(); j++) {
                        JSONObject theaterObject = theaterArray.getJSONObject(j);
                        theaterArrayList.add(new Theater(
                                theaterObject.getString("name"),
                                theaterObject.getString("image"),
                                theaterObject.getString("cinema_name"),
                                theaterObject.getString("cinema_address"),
                                theaterObject.getString("url_key")

                        ));
                    }

                }
            }
            MyAdapter myAdapter = new MyAdapter(MainActivity.this, arrayList);
            ListView listView = (ListView) findViewById(R.id.list_jason);
//            ListView listView1=(ListView)findViewById(R.id.list_theater);
//            listView1.setAdapter(myAdapter);
            listView.setAdapter(myAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDate(String endDate) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date eDate = null;
        try {
            eDate = format.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (today.before(eDate)) {
            return true;
        }
        return false;

    }

    public class LoadImage extends AsyncTask<String, String, Bitmap> {
        Bitmap bitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }
}
