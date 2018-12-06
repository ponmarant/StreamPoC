package com.ponmaran.a4s.streampoc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.nio.ByteBuffer;

import veg.mediaplayer.sdk.MediaPlayer;
import veg.mediaplayer.sdk.MediaPlayerConfig;

public class MainActivity extends AppCompatActivity implements MediaPlayer.MediaPlayerCallback {

//    private final String streamUrl = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
//    private final String streamUrl = "https://bitmovin-a.akamaihd.net/content/playhouse-vr/m3u8s/105560.m3u8";
//    private final String streamUrl = "rtsp://admin:balaji@192.168.1.2:554/cam/realmonitor?channel=0&subtype=1";
//    private final String streamUrl = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";
    private final String streamUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//      private final String streamUrl = "rtsp://admin:balaji@192.168.1.14:554/cam/realmonitor?channel=1&subtype=0";


    MediaPlayer mediaPlayer;
    Button mButton;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = (MediaPlayer) findViewById(R.id.playerView);
        mButton = (Button) findViewById(R.id.buttonView);

        mediaPlayer.getSurfaceView().setZOrderOnTop(true);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    mediaPlayer.getConfig().setConnectionUrl(streamUrl);

                    MediaPlayerConfig config = new MediaPlayerConfig();
                    config.setConnectionUrl(streamUrl);
                    config.setConnectionNetworkProtocol(-1);
                    config.setConnectionDetectionTime(2000);
                    config.setConnectionBufferingTime(500);
                    config.setDecodingType(1);
                    config.setRendererType(1);
                    config.setSynchroEnable(1);
                    config.setSynchroNeedDropVideoFrames(1);
                    config.setEnableColorVideo(1);
                    config.setDataReceiveTimeout(30000);
                    config.setNumberOfCPUCores(0);

                    if(mButton.getText().equals("Play")) {
                        mDialog = new ProgressDialog(MainActivity.this);
                        mDialog.setMessage("Please wait...");
                        mDialog.show();

                        mediaPlayer.Open(config, MainActivity.this);
                        mButton.setText("Stop");
                    }
                    else{
                        mediaPlayer.Close();
                        mButton.setText("Play");
                    }
                }
            }
        });
    }

    @Override
    public int Status(int i) {
        if(i == MediaPlayer.PlayerNotifyCodes.PLP_PLAY_SUCCESSFUL.ordinal())
            mDialog.dismiss();
        return 0;
    }

    @Override
    public int OnReceiveData(ByteBuffer byteBuffer, int i, long l) {
        return 0;
    }
}
