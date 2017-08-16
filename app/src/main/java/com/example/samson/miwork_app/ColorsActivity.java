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

public class ColorsActivity extends AppCompatActivity {

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

        numbers.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        numbers.add(new Word("mustard yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        numbers.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        numbers.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        numbers.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        numbers.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        numbers.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        numbers.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));

        WordAdapter adapter = new WordAdapter(this, numbers, R.color.category_colors);

        ListView list = (ListView) findViewById(R.id.number_activity);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseResource();
                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, numbers.get(position).getAudio_file());
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
