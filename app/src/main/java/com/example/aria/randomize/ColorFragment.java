package com.example.aria.randomize;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {
    private TextView resultView;
    private final int max = 16777215;
    private final int min = 0;
    private String hexRandom;

    public ColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null){
            hexRandom = savedInstanceState.getString("resultV", "#ffffff");
            String textColor = "#" + this.inversingHex(hexRandom.substring(1));

            resultView.setTextColor(Color.parseColor(textColor));
            resultView.setText(hexRandom);
            resultView.setBackgroundColor(Color.parseColor(hexRandom));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        try {
            outState.putString("resultV", ((TextView) getView().findViewById(R.id.resultView)).getText().toString());
        } catch(NullPointerException e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        resultView = (TextView) view.findViewById(R.id.resultView);

        if (hexRandom != null)
        {
            String textColor = "#" + this.inversingHex(hexRandom.substring(1));

            resultView.setTextColor(Color.parseColor(textColor));
            resultView.setText(hexRandom);
            resultView.setBackgroundColor(Color.parseColor(hexRandom));
        }

        return view;
    }

    public void Generate(View view){
        int range = (max - min) + 1;
        int random =  (int) (Math.random() * range) + min;
        hexRandom = Integer.toHexString(random);
        String textColor;

        while (hexRandom.length() < 6)
            hexRandom = "0" + hexRandom;

        textColor = this.inversingHex(hexRandom);
        hexRandom = "#" + hexRandom;
        textColor = "#" + textColor;

        resultView.setTextColor(Color.parseColor(textColor));
        resultView.setText(hexRandom);
        resultView.setBackgroundColor(Color.parseColor(hexRandom));
    }

    public String inversingHex(String hexRandom){
        String textColor;

        textColor = Integer.toHexString(255 - Integer.parseInt(hexRandom.substring(0,2),16));
        if (textColor.length() < 2)
            textColor = "0" + textColor;

        textColor = textColor + Integer.toHexString(255 - Integer.parseInt(hexRandom.substring(2,4),16));
        if (textColor.length() < 4)
            textColor = textColor.substring(0,2) + "0" + textColor.substring(2);

        textColor = textColor + Integer.toHexString(255 - Integer.parseInt(hexRandom.substring(4),16));
        if (textColor.length() < 6)
            textColor = textColor.substring(0,4) + "0" + textColor.substring(4);

        return textColor;
    }
}
