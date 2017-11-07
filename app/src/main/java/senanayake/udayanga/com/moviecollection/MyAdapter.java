package senanayake.udayanga.com.moviecollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

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
        TextView movieId = (TextView) convertView.findViewById(R.id.name);
        TextView name = (TextView) convertView.findViewById(R.id.cinema);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageBitmap(movie.bitmap);
        RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar);

        Button button = (Button) convertView.findViewById(R.id.more);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                context.startActivity(intent);
            }
        });
        movieId.setText(movie.mMovieName);
        name.setText(String.valueOf(movie.mImdbRate));
        ratingBar.setRating(Float.valueOf(movie.mImdbRate));
//        String showLink=movie.link;

        return convertView;
    }

}
