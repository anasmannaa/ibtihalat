package com.anasmana.ibtihalat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class TobarActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private int mAudioFileNumber;
    private boolean mStopped = false;
    private int mLength = 0;

    private MediaPlayer.OnCompletionListener mCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("غرور أنت يا دنيا", R.raw.gharoro));
        words.add(new Word("حين يهدي الصبح", R.raw.hinyahdisobh));
        words.add(new Word("جل المنادي", R.raw.jalalmonadi));
        words.add(new Word("سبحت لله في العش الطيور", R.raw.sabahat));
        words.add(new Word("يا مؤنسي في وحدتي", R.raw.yamonisi));

        WordAdapter adapter = new WordAdapter(this, words, R.color.white);

        System.out.println(adapter);


        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView listItemPlayerImage = (ImageView) view.findViewById(R.id.playerImage);

                if(mMediaPlayer != null && mAudioFileNumber == i){
                    if(mStopped) {
                        mMediaPlayer.seekTo(mLength);
                        mMediaPlayer.start();
                        mStopped = false;
                        listItemPlayerImage.setImageResource(R.mipmap.ic_stop);
                        return;
                    } else {
                        mMediaPlayer.pause();
                        mLength=mMediaPlayer.getCurrentPosition();
                        mStopped = true;
                        listItemPlayerImage.setImageResource(R.mipmap.ic_player);
                        return;
                    }
                } else {
                    mAudioFileNumber = i;
                }

                releaseMediaPlayer();
                mAudioFileNumber = i;
                Word word = words.get(i);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(TobarActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    listItemPlayerImage.setImageResource(R.mipmap.ic_stop);
                    mMediaPlayer.setOnCompletionListener(mCompleteListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.pause();
        mLength=mMediaPlayer.getCurrentPosition();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!mStopped) {
            mMediaPlayer.seekTo(mLength);
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}