package com.example.firebasereglogapp.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasereglogapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity {
    TextView open_menu;
    RecyclerView rec_first_cat,rec_second_cat,rec_third_cat,rec_fourth_cat;
    ExpandableListView expandableListView;
    LinearLayout ll_cat;
  private boolean visEpx = false;
    boolean subcat = false;
    ArrayList<String> mainArrayList = new ArrayList<>();
    HashMap<String, ArrayList<String>> childList = new HashMap<>();
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        open_menu = findViewById(R.id.open_menu);
       // expandableListView = findViewById(R.id.exp_list_view);
        ll_cat=findViewById(R.id.ll_cat);
        open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visEpx == false) {
                    ll_cat.setVisibility(View.VISIBLE);
                    visEpx = true;
                } else if (visEpx == true) {
                    ll_cat.setVisibility(View.GONE);
                    visEpx = false;
                }
            }
        });

        rec_first_cat=findViewById(R.id.rec_first_cat);
        rec_second_cat=findViewById(R.id.rec_second_cat);
        rec_third_cat=findViewById(R.id.rec_third_cat);
        rec_fourth_cat=findViewById(R.id.rec_fourth_cat);
       findViewById(R.id.cat_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subcat == false) {
                    rec_first_cat.setVisibility(View.VISIBLE);
                    Toast.makeText(UserActivity.this, "Visible", Toast.LENGTH_SHORT).show();
                    subcat = true;
                } else if (subcat == true) {
                    rec_first_cat.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "Gone", Toast.LENGTH_SHORT).show();
                    subcat = false;
                }
          }
          });
        findViewById(R.id.cat_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subcat == false) {
                    rec_second_cat.setVisibility(View.VISIBLE);
                    Toast.makeText(UserActivity.this, "Visible", Toast.LENGTH_SHORT).show();
                    subcat = true;
                } else if (subcat == true) {
                    rec_second_cat.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "Gone", Toast.LENGTH_SHORT).show();
                    subcat = false;
                }
            }
        });
        findViewById(R.id.cat_freezer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subcat == false) {
                    rec_third_cat.setVisibility(View.VISIBLE);
                    Toast.makeText(UserActivity.this, "Visible", Toast.LENGTH_SHORT).show();
                    subcat = true;
                } else if (visEpx == true) {
                    rec_third_cat.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "Gone", Toast.LENGTH_SHORT).show();
                    subcat = false;
                }
            }
        });
        findViewById(R.id.cat_electric).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subcat == false) {
                    rec_fourth_cat.setVisibility(View.VISIBLE);
                    Toast.makeText(UserActivity.this, "Visible", Toast.LENGTH_SHORT).show();
                    subcat = true;
                } else if (visEpx == true) {
                    rec_fourth_cat.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "Gone", Toast.LENGTH_SHORT).show();
                    subcat = false;
                }
            }
        });




/*
        //initialize adapter.......
        for (int g = 0; g < 5; g++) {
            mainArrayList.add("Group " + g);
            ArrayList<String> arrayList = new ArrayList<>();
            for (int c = 0; c <= 5; c++) {
                arrayList.add("Items " + c);
            }
            childList.put(mainArrayList.get(g), arrayList);
        }

        mainAdapter = new MainAdapter(mainArrayList, childList);
        expandableListView.setAdapter(mainAdapter);
 */
    }


}