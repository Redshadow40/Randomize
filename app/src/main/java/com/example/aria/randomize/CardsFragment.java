package com.example.aria.randomize;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardsFragment extends Fragment {
    ImageView image;
    CheckBox box;
    String newimage;

    public CardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null){
            newimage = savedInstanceState.getString("newimage");
            image.setImageResource(getImageId(this.getContext(), newimage));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        try {
            outState.putString("newimage", newimage);
        } catch(NullPointerException e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        image = (ImageView)view.findViewById(R.id.CardImage);
        box = (CheckBox)view.findViewById(R.id.JokerView);

        if (newimage != null)
        {
            image.setImageResource(getImageId(this.getContext(), newimage));
        }
        else
            Generate(view);

        return view;
    }

    public void Generate(View view){
        int min = 1;
        int max = 52;
        if (box.isChecked())
            max = 54;
        int range = (max-min) + 1;
        int random = (int)(Math.random() * range) + min;
        newimage = "drawable/c" + random;
        image.setImageResource(getImageId(this.getContext(),newimage));
    }

    private static int getImageId(Context context, String imageName) {

        return context.getResources().getIdentifier(imageName, null, context.getPackageName());
    }

}
