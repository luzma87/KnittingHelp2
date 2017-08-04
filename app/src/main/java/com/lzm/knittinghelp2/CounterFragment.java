package com.lzm.knittinghelp2;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CounterFragment extends Fragment {

    public CounterFragment() {
    }

    public static CounterFragment newInstance() {
        return new CounterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.counter_fragment, container, false);
    }

}
