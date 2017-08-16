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

public class FamilyActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    // creating a oncompletion of MediaPlayer instance
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

//        getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Word> numbers = new ArrayList<>();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        numbers.add(new Word("father", "epe", R.drawable.family_father, R.raw.family_father));
        numbers.add(new Word("mother", "eta", R.drawable.family_mother, R.raw.family_mother));
        numbers.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        numbers.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        numbers.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        numbers.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        numbers.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        numbers.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        numbers.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        numbers.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, numbers, R.color.category_family);

        ListView list = (ListView) findViewById(R.id.number_activity);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseResource();

                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, numbers.get(position).getAudio_file());
                    mediaPlayer.start();

                    //setting oncompleteListener for the mediaPlayer
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                Intent upIntent = getParentActivityIntent();
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                }else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

//        int upButton = item.getItemId();
//
//        if(upButton == R.id.home){
////            Intent intent = new Intent(FamilyActivity.this, MainActivity.class);
////            startActivity(intent);
////            return true;
//
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
//        }
      return super.onOptionsItemSelected(item);
    }

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
}
