package com.example.owner.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;


public class MainActivity extends ActionBarActivity {
    private MediaPlayer player;
    private TextView title;

    private Timer timer;
    private Handler handler = new Handler();
    private TextView currentTimeText,wholeTimeText;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.xxx);
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrance();
        } catch (IOException e) {
            e.printStackTrance();

            title = (TextView) findViewById(R.id.title);
            title.setText("かえるのうた");

            seekBar = (SeekBar)findViewById(R.id.seekBar);
            currentTimeText = (TextView)findViewById(R.id.current_time);
            wholeTimeText =(TextView)findViewById(R.id.whole_time);
            int duration = player.getDuration();
            seekBar.setMax(duration);
        }

      public void start(View v) {
        player.start();
      }
      public void pause(View v){
         player.pause();
      }
    public void stop(View V){
        player.stop();
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
    }}
