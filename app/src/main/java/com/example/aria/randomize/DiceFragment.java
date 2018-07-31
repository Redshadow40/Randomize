package com.example.aria.randomize;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiceFragment extends Fragment {
    private ImageView diceView;
    AnimationDrawable frameA;
    public DiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dice, container, false);

        diceView = (ImageView) view.findViewById(R.id.dice);
        diceView.setBackgroundResource(R.drawable.spin);
        frameA = (AnimationDrawable) diceView.getBackground();
        return view;
    }

    public void Generate(View view) throws Exception{
        //frameA.stop();
        diceView.setImageResource(0);
        frameA.start();

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                frameA.stop();
            }
        };

        int min = 700;
        int max = 1800;
        int range = (max-min) + 1;
        int random = (int)(Math.random() * range) + min;

        timer.schedule(timerTask, random);

    }

    private static int getImageId(Context context, String imageName) {

        return context.getResources().getIdentifier(imageName, null, context.getPackageName());
    }
}
