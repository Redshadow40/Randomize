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
public class IntegerFragment extends Fragment {
    private ListView resultText;
    private EditText fromText;
    private EditText toText;
    private ArrayList<String> random;
    private Integer maxI, minI;
    private ArrayAdapter myAdapter;

    public IntegerFragment() {
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
        } catch(NullPointerException e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_integer, null, false);
        resultText = (ListView) view.findViewById(R.id.resultView);
        fromText = (EditText) view.findViewById(R.id.fromView);
        toText = (EditText) view.findViewById(R.id.toView);

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

        int range = (maxI - minI) + 1;

        random = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            random.add(Integer.toString((int) (Math.random() * range) + minI));
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
        for (int i = 0; i < 20; i++)
            tmp.add(Integer.toString((int) (Math.random() * (maxI - minI + 1)) + minI));
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
