package com.example.owner.musicplayer;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;





public class MainActivity extends ActionBarActivity {
    private MediaPlayer player;
    private TextView title;
    private Timer timer;
    private Handler handler = new Handler();
    private TextView currentTimeText,wholeTimeText;
    private SeekBar seekBar;
    private String filePath;
    private static ImageView imageView;
    private boolean nowPlaying;
    private boolean selectMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        currentTimeText = (TextView) findViewById(R.id.current_time);
        wholeTimeText = (TextView) findViewById(R.id.whole_time);
        imageView = (ImageView)findViewById(R.id.imageView);

        nowPlaying = false;
        selectMusic = false;
    }


    public void Sunflower(View v) {
        player = MediaPlayer.create(this,R.raw.sample);
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        seekBar();

        title.setText("ひまわりの約束");

        timeText();

       /* filePath = "android.resource://" + getPackageName() + "/" + R.raw.sample;

        setImage();   */

        selectMusic = true;
    }




    public void Happily(View v) {
        player = MediaPlayer.create(this, R.raw.sample);
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        seekBar();

        title.setText("Happily");

        timeText();

       /* filePath = "android.resource://" + getPackageName() + "/" + R.raw.sample;

        setImage(); */

        selectMusic = true;
    }


    public void seekBar(){
      seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {
              player.pause();
          }
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser ) {
              //progressをTextViewにセット
              int Progress = seekBar.getProgress();
              player.seekTo(Progress);

              }
          @Override
          public void onStopTrackingTouch(SeekBar seekBar){
              int progress =seekBar.getProgress();
              player.seekTo(progress);
              if(nowPlaying == true) {
                  player.start();
              }

          }
      });
    }

    public void setImage() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(filePath);
        byte[] data = mmr.getEmbeddedPicture();
        if (null != data) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
        }
    }

   public void timeText(){
       int duration = player.getDuration();
       seekBar.setMax(duration);

       duration = duration / 1000;

       int minutes = duration / 60;
       int seconds = duration % 60;

       String m = String.format(Locale.JAPAN, "%02d", minutes);
       String s = String.format(Locale.JAPAN, "%02d", seconds);

       wholeTimeText.setText(m + ":" + s);
    }

    public void  start(View v){
       if(selectMusic == true) {

           if (nowPlaying == true) {
               player.stop();
           }

           player.start();
           nowPlaying = true;

           if (timer != null) {
               timer.cancel();
           }

           if (timer == null) {
               timer = new Timer();
               timer.schedule(new TimerTask() {
                   @Override
                   public void run() {

                       int duration = player.getCurrentPosition() / 1000;

                       int minutes = duration / 60;
                       int seconds = duration % 60;

                       final String m = String.format(Locale.JAPAN, "%02d", minutes);
                       final String s = String.format(Locale.JAPAN, "%02d", seconds);

                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               currentTimeText.setText(m + ":" + s);
                               seekBar.setProgress(player.getCurrentPosition());
                           }
                       });
                   }
               }, 0, 1000);
           }
       }
   }

    public void pause(View v) {
       if(selectMusic == true) {
           player.pause();
           if (timer != null) {
               timer.cancel();
               timer = null;
           }
           nowPlaying = false;
       }
    }

    public void stop(View v) {
       if(selectMusic == true) {
           player.pause();
           if (timer != null) {
               timer.cancel();
               timer = null;
               seekBar.setProgress(player.getCurrentPosition());
           }
           nowPlaying = false;
       }
    }





    @Override
    protected void onDestroy(){
        super.onDestroy();
        player.release();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
 }
