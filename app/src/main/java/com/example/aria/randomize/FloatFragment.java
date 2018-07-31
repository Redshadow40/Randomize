package com.example.aria.randomize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FloatFragment extends Fragment {
    private ListView resultText;
    private EditText fromText;
    private EditText toText;
    private EditText textCount;
    private ArrayList<String> random;
    private Integer minI, maxI, num;
    private String pattern;
    private ArrayAdapter myAdapter;

    public FloatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null){
            random = (savedInstanceState.getStringArrayList("random"));
            if (random != null) {
                myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, random);
                resultText.setAdapter(myAdapter);

                minI = savedInstanceState.getInt("minI");
                maxI = savedInstanceState.getInt("maxI");
                num = savedInstanceState.getInt("num");
                pattern = "%." + num + "f";
                resultText.setOnScrollListener(new Endless() {
                    @Override
                    public boolean onLoadMore(int page, int totalItemCount) {
                        addMore();
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        try {
            if (!random.isEmpty() && random != null)
                outState.putStringArrayList("random", random);
            if (minI != null)
                outState.putInt("minI", minI);
            if (maxI != null)
                outState.putInt("maxI", maxI);
            if (num != null)
                outState.putInt("num", num);
        } catch(NullPointerException e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_float, null, false);
        resultText = (ListView) view.findViewById(R.id.resultView);
        fromText = (EditText) view.findViewById(R.id.fromView);
        toText = (EditText) view.findViewById(R.id.toView);
        textCount = (EditText) view.findViewById(R.id.decimalCount);

        return view;
    }

    public void Generate(View view){
        if (fromText.getText().length() != 0)
            minI = Integer.parseInt(fromText.getText().toString());
        else
            minI = 0;
        if (toText.getText().length() != 0)
            maxI = Integer.parseInt(toText.getText().toString());
        else
            maxI = 0;

        if (minI > maxI){
            Toast.makeText(getContext(), "Invalid min/max", Toast.LENGTH_SHORT).show();
            return;
        }

        int range = (maxI - minI);

        num = 2;
        if (textCount.length() != 0){
            num = Integer.parseInt(textCount.getText().toString());
        }
        else {
            textCount.setText("2");
        }

        pattern = "%." + num + "f";

        if (num == 0)
            range +=1;

        random =  new ArrayList<>();

        if (maxI - minI == 0){
            random.add("0");
            myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, random);
            resultText.setAdapter(myAdapter);
            return;
        }


        Double tmp;
        for (int i = 0; i < 20; i++){
            tmp = (Math.random() * range) + minI;
            if (num == 0)
                tmp = Math.floor(tmp);
            random.add(String.format(pattern, tmp));
        }


        myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, random);
        resultText.setAdapter(myAdapter);

        resultText.setOnScrollListener(new Endless() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                addMore();
                return true;
            }
        });
    }

    public void addMore(){
        ArrayList<String> tmp = new ArrayList<>();
        Double tmp1;
        for (int i = 0; i < 20; i++){
            tmp1 = (Math.random() * (maxI - minI + 1)) + minI;
            if (num == 0)
                tmp1 = Math.floor(tmp1);
            tmp.add(String.format(pattern, tmp1));
        }

        random.addAll(tmp);
        myAdapter.addAll(tmp);
        myAdapter.notifyDataSetChanged();
    }

    private abstract class Endless implements AbsListView.OnScrollListener {
        private int visibleThreshold = 5;
        private int currentPage = 0;
        private int previousTotalItemCount = 0;
        private boolean loading = true;
        private int startingPageIndex = 0;

        public Endless(){}
        public Endless(int visibleThreshold){
            this.visibleThreshold = visibleThreshold;
        }

        public Endless(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) this.loading = true;
            }

            if (loading && (totalItemCount > previousTotalItemCount)){
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }
            if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount){
                loading = onLoadMore(currentPage + 1, totalItemCount);
            }
        }

        public abstract boolean onLoadMore(int page, int totalItemCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState){

        }
    }
}
