package fr.enssat.deroovincelet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView myVideoView;
    private VideoWebViewModel mViewModel;
    private MediaController mediaControls;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity self = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*****************/
        /*   VIDEOVIEW   */
        /*****************/

        mViewModel = new VideoWebViewModel();
        if (mediaControls == null) {
            mediaControls = new MediaController(MainActivity.this);
        }
        myVideoView = findViewById(R.id.videoView);
        try {
            myVideoView.setMediaController(mediaControls);

            myVideoView.setVideoURI(Uri.parse("https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                myVideoView.start();

                mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        mViewModel.changeStatusByVideo(mp.getCurrentPosition());
                    }
                });
            }
        });


        /***************/
        /*   BUTTONS   */
        /***************/

        Button[] buttonList = new Button[6];
        buttonList[0] = findViewById(R.id.button1);
        buttonList[1] = findViewById(R.id.button2);
        buttonList[2] = findViewById(R.id.button3);
        buttonList[3] = findViewById(R.id.button4);
        buttonList[4] = findViewById(R.id.button5);
        buttonList[5] = findViewById(R.id.button6);
        for(int i = 0; i<buttonList.length; i++){
            final String text = (String) (buttonList[i]).getText();
            buttonList[i].setOnClickListener(new View.OnClickListener() {
                private final String button_text = text;
                @Override
                public void onClick(View v) {
                    mViewModel.changeStatusByButton(button_text);
                }
            });
        }


        /***************/
        /*   WEBVIEW   */
        /***************/

        web = findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        web.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        web.loadUrl("https://en.wikipedia.org/wiki/Introduction");



        /****************/
        /*   OBSERVER   */
        /****************/

        mViewModel.getVideoStatus().observe(self, new Observer<VideoStatus>() {
            @Override
            public void onChanged(@Nullable VideoStatus videoStatus) {
                myVideoView.seekTo(videoStatus.videoPosition);
                String url = "https://en.wikipedia.org/wiki/Introduction";
                if(videoStatus.videoPosition >= 25000) url = "https://en.wikipedia.org/wiki/Title";
                if(videoStatus.videoPosition >= 76000) url = "https://en.wikipedia.org/wiki/Butterfly";
                if(videoStatus.videoPosition >= 158000) url = "https://en.wikipedia.org/wiki/Assault";
                if(videoStatus.videoPosition >= 275000) url = "https://en.wikipedia.org/wiki/Payback";
                if(videoStatus.videoPosition >= 491000) url = "https://en.wikipedia.org/wiki/Cast";
                web.loadUrl(url);
            }
        });
    }

}
