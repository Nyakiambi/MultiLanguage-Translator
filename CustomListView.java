package com.example.multiTranslator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiTranslator.R;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {
//    ArrayAdapter-- * You can use this adapter to provide views for an {@link AdapterView},
//            * Returns a view for each object in a collection of data objects you
// * provide, and can be used with list-based user interface widgets such as
// * {@link ListView} or {@link Spinner}.
    private String [] emailAddress;
    private String[] ratings;
    private String [] reviews;
    private Activity context;

    public CustomListView(@NonNull Context context, ArrayList<String> emailAddress, ArrayList<String> ratings, ArrayList<String>  reviews) {
        super(context, R.layout.list_view_layout,emailAddress);
        this.emailAddress = emailAddress.toArray(new String[0]);
        this.ratings= ratings.toArray(new String[0]);
        this.reviews= reviews.toArray(new String[0]);
        this.context = (Activity) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r= convertView;
        ViewHolder viewHolder=null;
        if (r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.list_view_layout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) r.getTag();
        }
        viewHolder.email.setText(emailAddress[position]);
        viewHolder.rating.setRating(ratings.length);
        viewHolder.review.setText(reviews[position]);
        final ViewHolder finalViewHolder = viewHolder;
        return r;
    }
    class ViewHolder{
        TextView email;
        RatingBar rating;
        TextView review;
        private final Context context;
        public ViewHolder(View views){
            context=views.getContext();
            email=views.findViewById(R.id.email);
            rating=views.findViewById(R.id.ratinba);
            review=views.findViewById(R.id.reviews);
        }

    }
}
