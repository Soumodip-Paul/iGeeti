package com.sp.igeeti;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sp.igeeti.utils.MusicMetaData;
import com.sp.igeeti.utils.TimeFormatter;

import java.io.File;
import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity {
    ArrayList<String> songs;
    ArrayList<String> songNames;
    int position;
    TextView songName,currentTime,duration,artist,album,genre;
    SeekBar seekBar;
    ConstraintLayout musicBackGround;
    ImageView playButton,previousButton,nextButton,rewindButton,forwardButton,logoImage;
    MediaPlayer mediaPlayer;
    Thread updateSeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        songName = findViewById(R.id.songName);
        playButton = findViewById(R.id.playButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        rewindButton = findViewById(R.id.fastRewind);
        forwardButton = findViewById(R.id.fastForward);
        seekBar = findViewById(R.id.seekBar);
        currentTime = findViewById(R.id.currentTime);
        duration = findViewById(R.id.duration);
        artist = findViewById(R.id.artistName);
        album = findViewById(R.id.albumName);
        musicBackGround = findViewById(R.id.playMusicBackground);
        logoImage = findViewById(R.id.logoImage);
        genre = findViewById(R.id.genreName);

        songs = getIntent().getStringArrayListExtra("songs");
        songNames = getIntent().getStringArrayListExtra("songNames");
        position = getIntent().getIntExtra("currentPosition",0);

        startMediaPlayer(position);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTime.setText(TimeFormatter.formatMillis(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        playButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()){
                playButton.setImageResource(R.drawable.play);
                mediaPlayer.pause();
            }
            else {
                playButton.setImageResource(R.drawable.pause);
                mediaPlayer.start();
            }
        });

        nextButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();

            if (position != songs.size() - 1) position += 1;
            else position = 0;

            startMediaPlayer(position);
        });

        previousButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();

            if (position != 0) position -= 1;
            else position = songs.size() - 1;

            startMediaPlayer(position);
        });

        forwardButton.setOnClickListener(v->{
            int currPosition = mediaPlayer.getCurrentPosition() + 10*1000;
            seekBar.setProgress(currPosition);
            mediaPlayer.seekTo(currPosition);


        });
        rewindButton.setOnClickListener(v->{
            int currPosition = mediaPlayer.getCurrentPosition() - 10*1000;
            if(currPosition  > 0) {
                seekBar.setProgress(currPosition);
                mediaPlayer.seekTo(currPosition);
            }
            else previousButton.performClick();

        });

        updateSeek =  new Thread(() -> {
            int currentPosition = 0;
            try {
                while (true){
                if(currentPosition >= mediaPlayer.getDuration()) {
                    currentPosition = 0;
                    runOnUiThread(()-> nextButton.performClick());
                }
                else currentPosition = mediaPlayer.getCurrentPosition();
                    int finalCurrentPosition = currentPosition;
                    runOnUiThread(() -> currentTime.setText(
                            TimeFormatter.formatMillis(finalCurrentPosition)
                    ));
                    seekBar.setProgress(currentPosition);

                    Thread.sleep(1000);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        updateSeek.start();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        updateSeek.interrupt();
        super.onDestroy();
    }

    public void startMediaPlayer(int position){
        songName.setText(songNames.get(position));
        songName.setSelected(true);
        Uri uri = Uri.fromFile(new File(songs.get(position)));
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();
        MusicMetaData metaData = new MusicMetaData(songs.get(position));
        artist.setText(metaData.getArtist());
        album.setText(metaData.getAlbum());
        genre.setText(metaData.getGenre());
        metaData.setImage(logoImage);
        metaData.setBackgroundDrawable(musicBackGround,this);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);
        duration.setText(TimeFormatter.formatMillis(mediaPlayer.getDuration()));
        playButton.setImageResource(R.drawable.pause);
    }
}
