package com.example.samson.miwork_app;

/**
 * Created by SAMSON on 6/21/2017.
 */

public class Word {

    private String english_word;
    private String miwork_word;
    private int image_profile = NO_IMAGE_PROVIDED;
    private int audio_file;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String english_word, String miwork_word, int image_profile, int audio_file){
        this.miwork_word = miwork_word;
        this.audio_file = audio_file;
        this.image_profile = image_profile;
        this.english_word = english_word;
    }
    public Word(String english_word, String miwork_word, int audio_file){

        this.miwork_word = miwork_word;
        this.audio_file = audio_file;
        this.english_word = english_word;
    }

    public String getEnglish_word() {
        return english_word;
    }

    public String getMiwork_word() {
        return miwork_word;
    }

    public int getImage_profile() {
        return image_profile;
    }

    public int getAudio_file() {
        return audio_file;
    }
    public boolean hasImage(){

        return image_profile != NO_IMAGE_PROVIDED;
    }
}
