package com.example.samson.miwork_app;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SAMSON on 6/21/2017.
 */

public class NumbersActivity extends AppCompatActivity {

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseResource();
        }
    };
    /*
    instantiating audio manager for onfocus
    * */
    AudioManager audioManager;
    //audioFocusChangerListener
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                releaseResource();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //setting the upbutton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Word> numbers = new ArrayList<>();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        numbers.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "oyisa", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, numbers, R.color.category_numbers);

        ListView list = (ListView) findViewById(R.id.number_activity);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseResource();
                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, numbers.get(position).getAudio_file());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(onCompletionListener);

                }
            }
        });
    }

    MediaPlayer mediaPlayer;

    public void releaseResource() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseResource();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent upIntent = getParentActivityIntent();

                //This activity is NOT part of this app's task, so create a new task
                //when navigating up, with a synthesized back stack.
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)

                            //Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            //Navigate up to the closest parent
                            .startActivities();
                }else {
                    //This activity is part of this app's task, so simply
                    //  navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
