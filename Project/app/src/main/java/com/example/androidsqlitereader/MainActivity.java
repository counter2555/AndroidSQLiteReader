package com.example.androidsqlitereader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsqlitereader.ui.main.SectionsPagerAdapter;
import com.example.androidsqlitereader.databinding.ActivityMainBinding;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.ListView;

import android.database.sqlite.SQLiteDatabase;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView database_name_label;
    private  ListView table_list_view;
    private FloatingActionButton add_button;

    private Overview OverviewFragment;
    private DataFragment DetailFragment;


    private SQLiteDatabase current_db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Select .db File


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);



        //initialize UI elemens
        OverviewFragment = (Overview)sectionsPagerAdapter.GetFragment(0);
        DetailFragment = (DataFragment)sectionsPagerAdapter.GetFragment(1);

        add_button = (FloatingActionButton)findViewById(R.id.opendb);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileDialog();
            }
        });
        database_name_label = (TextView)findViewById(R.id.db_name_label);
    }

    //Select .db File
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123 && resultCode == RESULT_OK)
        {
            Uri selectedfile = data.getData();
            String filename = GetFileNameFromUri(selectedfile);
            this.database_name_label.setText(GetPath(selectedfile));
            this.OpenDB(selectedfile);
            this.UpdateTables();
        }
    }

    //Gets File Name from Uri as String
    private static String GetFileNameFromUri(Uri url)
    {
        File file = new File(url.getPath());
        return  file.getName();
    }

    private static String GetPath(Uri url)
    {
        File file = new File(url.getPath());
        return  file.getAbsolutePath().replaceAll("/document/raw:", "");
    }

    private void OpenFileDialog()
    {
        Intent intent = new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
    }

    private void OpenDB(Uri dbfile)
    {
        this.current_db = SQLiteDatabase.openDatabase(GetPath(dbfile), null, SQLiteDatabase.OPEN_READONLY);
        this.DetailFragment.SetDatabase(this.current_db);
    }

    private void UpdateTables()
    {
        if(!Objects.isNull(current_db)) {
            this.OverviewFragment.ClearItems();
            this.DetailFragment.Clear();

            Cursor c = current_db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            int i = 0;
            if(c.moveToFirst())
            {
                while (!c.isAfterLast())
                {
                    String name = c.getString(0);
                    this.OverviewFragment.AddItem(name);
                    this.DetailFragment.AddItem(name);
                    c.moveToNext();
                }

            }

            this.OverviewFragment.Update();
            this.DetailFragment.Update();
        }

    }

    private  void CloseDatabase()
    {
        this.DetailFragment.CloseDatabase();
        this.current_db.close();
    }

    @Override
    public  void onDestroy()
    {
        super.onDestroy();
        this.CloseDatabase();
    }


}