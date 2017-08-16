package com.example.samson.miwork_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SAMSON on 6/21/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    int color;


    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects , int color) {
        super(context, 0, objects);
        this.color = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);

        }
        Word word = getItem(position);
        TextView english;
        TextView miwork;
        ImageView imageView;

        english = (TextView)view.findViewById(R.id.english_word);
        miwork = (TextView)view.findViewById(R.id.miwork_word);

        imageView = (ImageView)view.findViewById(R.id.image_viewer);


        if(word.hasImage()) {

            //get the imageResource get and set it as source of the image view
            imageView.setImageResource(word.getImage_profile());

            //make the image view visible
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        //seach for the view with the give id
        View textContainer = view.findViewById(R.id.text_container);

        // set its backgroung resource to the mColorResourceId
        textContainer.setBackgroundResource(color);


        english.setText(word.getEnglish_word());
        miwork.setText(word.getMiwork_word());

        return view;
    }
}
