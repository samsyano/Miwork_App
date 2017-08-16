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

public class PhrasesActivity extends AppCompatActivity {
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

        numbers.add(new Word("where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        numbers.add(new Word("What is your name?", "tinne oyaase'ne", R.raw.phrase_what_is_your_name));
        numbers.add(new Word("My name is..", "oyaaset",  R.raw.phrase_my_name_is));
        numbers.add(new Word("How are you feeling?", "michekses?",  R.raw.phrase_how_are_you_feeling));
        numbers.add(new Word("I'm feeling good", "kuchi achit",  R.raw.phrase_im_feeling_good));
        numbers.add(new Word("Are you coming", "eenes'aa?", R.raw.phrase_are_you_coming));
        numbers.add(new Word("Yes, I'm coming", "hee'eenem", R.raw.phrase_yes_im_coming));
        numbers.add(new Word("I'm coming", "eenem", R.raw.phrase_im_coming));
        numbers.add(new Word("Let's go", "yoowutis",  R.raw.phrase_lets_go));
        numbers.add(new Word("Come here", "enni'nem",  R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, numbers, R.color.category_phrases);

        ListView list = (ListView) findViewById(R.id.number_activity);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseResource();
                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, numbers.get(position).getAudio_file());
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
