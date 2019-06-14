package com.vamsi.bakingappvamsi;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vamsi.bakingappvamsi.ModelClass.StepModel;

import java.util.List;


public class ItemDetailFragment extends Fragment
{
    TextView novideo;
    TextView videoDescription;
    ImageButton previousButton;
    ImageButton nextButton;
    private PlayerView myvideoplayer;
    private SimpleExoPlayer exoplayer;
    private boolean stopPlay;
    private long playerstartposition;
    private long playerStopPosition;
    private boolean playWhenReady = true;
    private List<StepModel> step_list;
    private String video_link;
    private int total_steps = 0;
    private int current_step;

    public ItemDetailFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.item_detail,container,false);

        if(savedInstanceState!=null)
        {
            current_step = savedInstanceState.getInt("cpi");
            playWhenReady = savedInstanceState.getBoolean("play");
            playerstartposition = savedInstanceState.getLong("ppi", C.TIME_UNSET);
        }
        if(getArguments()!=null)
        {
            step_list= (List<StepModel>) getArguments().getSerializable("step");
            current_step=getArguments().getInt("pos",0);
        }

        novideo=view.findViewById(R.id.no_video_textview);
        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);
        myvideoplayer = view.findViewById(R.id.video_view);
        videoDescription = view.findViewById(R.id.fulldescription);
        playerstartposition = C.TIME_UNSET;

        total_steps = step_list.size();
        video_link = step_list.get(current_step).getVideoUrl();
        videoDescription.setText(step_list.get(current_step).getDescription());
        VideoPlayerVisibility();
        if(current_step == total_steps-1)
        {
            nextButton.setVisibility(View.INVISIBLE);
        }
        if(current_step == 0)
        {
            previousButton.setVisibility(View.INVISIBLE);
        }


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                nextButton.setVisibility(View.VISIBLE);
                current_step--;
                videoDescription.setText(step_list.get(current_step).getDescription());
                if(current_step == 0)
                {
                    previousButton.setVisibility(View.INVISIBLE);
                }
                video_link = step_list.get(current_step).getVideoUrl();
                VideoPlayerVisibility();
                startExoPlayer();
                exoplayer.seekTo(0);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                previousButton.setVisibility(View.VISIBLE);
                current_step++;
                video_link = step_list.get(current_step).getVideoUrl();
                videoDescription.setText(step_list.get(current_step).getDescription());
                if(current_step==total_steps-1)
                {
                    nextButton.setVisibility(View.INVISIBLE);
                }
                VideoPlayerVisibility();
                startExoPlayer();
                exoplayer.seekTo(0);
            }
        });
        return view;
    }

    public void VideoPlayerVisibility()
    {
        if(TextUtils.isEmpty(video_link))
        {
            myvideoplayer.setVisibility(View.INVISIBLE);
            novideo.setText("Sorry! there is no Video for this Step.\nPlease prepare yourself");
            novideo.setVisibility(View.VISIBLE);
        }
        else
        {
            myvideoplayer.setVisibility(View.VISIBLE);
            novideo.setVisibility(View.INVISIBLE);
        }
    }

    private void startExoPlayer()
    {
        exoplayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());
        myvideoplayer.setPlayer(exoplayer);
        Uri uri = Uri.parse(video_link);
        MediaSource mediaSource = MakeVideo(uri);
        exoplayer.prepare(mediaSource, true, false);
        exoplayer.setPlayWhenReady(playWhenReady);

        if(playerstartposition!=0&&!stopPlay)
        {
            exoplayer.seekTo(playerstartposition);
        }
        else
            {
            exoplayer.seekTo(playerStopPosition);
            }
    }

    private void stopPlayer()
    {
        if (exoplayer != null)
        {
            exoplayer.stop();
            exoplayer.release();
            exoplayer = null;
        }
    }

    private MediaSource MakeVideo(Uri uri)
    {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("Baking App")).createMediaSource(uri);
    }


    @Override
    public void onStart()
    {
        super.onStart();
        if (Util.SDK_INT > 23)
        {
            startExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (exoplayer != null) {
                playerStopPosition = exoplayer.getCurrentPosition();
                stopPlay = true;
                stopPlayer();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoplayer == null))
        {
            startExoPlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }





    @Override
    public void onSaveInstanceState(@NonNull Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt("cpi",current_step);
        savedState.putLong("ppi",exoplayer.getCurrentPosition());
        savedState.putBoolean("play",exoplayer.getPlayWhenReady());
    }
}