package com.example.androidsqlitereader;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overview extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listview;
    private  ArrayList<String> mItems;
    private ArrayAdapter<String> adapter;

    private View rootView;

    public Overview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Overview.
     */
    // TODO: Rename and change types and number of parameters
    public static Overview newInstance(String param1, String param2) {
        Overview fragment = new Overview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        listview = (ListView)rootView.findViewById(R.id.overview_listview);

        String[] emtpy = new String[] {};

        mItems = new ArrayList<String>(Arrays.asList(emtpy));

        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mItems);

        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //Toast.makeText(this.getContext(), "Overview loaded", Toast.LENGTH_LONG).show();

        return rootView;
    }


    public void AddItem(String s)
    {
        this.mItems.add(s);
    }



    public void Update()
    {
        adapter.notifyDataSetChanged();
    }

    public void ClearItems()
    {
        mItems.clear();
        this.Update();
    }


}