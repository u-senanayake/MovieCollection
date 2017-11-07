package senanayake.udayanga.com.moviecollection;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Udayanga Senanayake on 11/6/2017.
 */
public class MyAdapter extends ArrayAdapter {
    Context context;

    public MyAdapter(Context context, ArrayList<Movie> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.name_list, parent, false);
        }
        TextView movieName = (TextView) convertView.findViewById(R.id.name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageBitmap(movie.bitmap);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
        Button buttonBook = (Button) convertView.findViewById(R.id.book);
//        TextView theater=(TextView)convertView.findViewById(R.id.theaterName);
        Button buttonMore = (Button) convertView.findViewById(R.id.more);

        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        movieName.setText(movie.mMovieName);
        ratingBar.setRating(Float.valueOf(movie.mImdbRate));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String bookingStartDate = movie.mBookingStartDate;
        String releaseDate = movie.mReleaseDate;
        String endDate = movie.mEndDate;
        Date today = new Date();
        Date sDate = null, rDate = null, eDate = null;
        try {
            sDate = format.parse(bookingStartDate);
            rDate = format.parse(releaseDate);
            eDate = format.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (today.before(sDate)) {
            buttonBook.setText(String.valueOf(sDate));
            buttonBook.setBackgroundColor(Color.CYAN);
        } else {
            buttonBook.setText("Book");
            buttonBook.setBackgroundColor(Color.RED);

        }

        return convertView;
    }

}
