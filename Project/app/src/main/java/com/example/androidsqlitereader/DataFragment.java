package com.example.androidsqlitereader;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import android.database.sqlite.SQLiteDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SQLiteDatabase database;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String NoneString = "------";

    private ArrayAdapter<String> adapter;
    private ArrayList<String> mItems;
    private Spinner dropdown;

    private TextView detailTextView;

    private View rootView;

    public DataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
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
        rootView = inflater.inflate(R.layout.fragment_data, container, false);
        this.dropdown = (Spinner)rootView.findViewById(R.id.table_dropdown);
        this.detailTextView = (TextView)rootView.findViewById(R.id.detail_text);

        this.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadDetails((String)dropdown.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] emtpy = new String[] {};

        mItems = new ArrayList<String>(Arrays.asList(emtpy));

        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mItems);

        this.dropdown.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return rootView;

    }



    public void AddItem(String name)
    {
        this.mItems.add(name);
    }
    public void Update()
    {
        adapter.notifyDataSetChanged();
    }

    public void Clear()
    {
        mItems.clear();
        this.AddItem(NoneString);
        this.Update();
        this.detailTextView.setText("");
    }

    public void LoadDetails(String table)
    {
        if(!table.equals(NoneString)) {
            String txt = "";
            //get Entry numbers
            Cursor c = this.database.rawQuery("SELECT COUNT(*) FROM "+table, null);

            if(c.moveToFirst())
            {
                int count = c.getInt(0);
                txt += "Entries: "+String.valueOf(count)+"\n\n";
            }

            //get column names
            c = this.database.query(table, null, null, null, null, null, null);
            String[] columns = c.getColumnNames();

            txt += "Columns:\n";

            for(String s: columns)

            {
                txt += " " + s + "\n";
            }

            this.detailTextView.setText(txt);


        }
        else
            this.detailTextView.setText("");
    }

    public void SetDatabase(SQLiteDatabase db)
    {
        this.database = db;
    }

    public  void CloseDatabase()
    {
        this.database.close();
    }
}