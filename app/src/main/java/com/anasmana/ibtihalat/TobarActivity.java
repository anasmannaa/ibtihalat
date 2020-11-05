package com.anasmana.ibtihalat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TobarActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private int mAudioFileNumber;
    private boolean mStopped = false;
    private int mLength = 0;
    private ImageView listItemPlayerImage;

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
        forceRTLIfSupported();
        setContentView(R.layout.word_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

                if (mMediaPlayer != null) {
                    if (mAudioFileNumber == i) {
                        if (mStopped) {
                            mMediaPlayer.seekTo(mLength);
                            mMediaPlayer.start();
                            mStopped = false;
                            listItemPlayerImage.setImageResource(R.mipmap.ic_stop);
                            return;
                        } else {
                            mMediaPlayer.pause();
                            mLength = mMediaPlayer.getCurrentPosition();
                            mStopped = true;
                            listItemPlayerImage.setImageResource(R.mipmap.ic_player);
                            return;
                        }
                    } else {
                        listItemPlayerImage.setImageResource(R.mipmap.ic_player);
                        playAudio(words.get(i), view, i);

                    }
                }
                playAudio(words.get(i), view, i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        onGoAway();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onGoAway();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onReturnBack();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onReturnBack();
    }

    private void playAudio(Word word, View view, int i) {
        releaseMediaPlayer();
        mAudioFileNumber = i;
        listItemPlayerImage = (ImageView) view.findViewById(R.id.playerImage);
        listItemPlayerImage.setImageResource(R.mipmap.ic_stop);
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            mMediaPlayer = MediaPlayer.create(TobarActivity.this, word.getmAudioResourceId());
            mMediaPlayer.start();
            listItemPlayerImage.setImageResource(R.mipmap.ic_stop);
            mMediaPlayer.setOnCompletionListener(mCompleteListener);
        }
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(TobarActivity.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }

    private void onGoAway() {
        if(mMediaPlayer != null) {
            if (!mStopped) {
                mMediaPlayer.pause();
            }
            mLength = mMediaPlayer.getCurrentPosition();
        }
    }

    private void onReturnBack() {
        if(mMediaPlayer != null) {
            if (!mStopped) {
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
            }
            mLength = mMediaPlayer.getCurrentPosition();
        }
    }
}