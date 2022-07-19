package com.example.myshop2.ActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop2.Adapter.BeforeSearchAdapter;
import com.example.myshop2.ObjectClass.BeforeSearchClass;
import com.example.myshop2.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText editSearchQuery;
    ImageView btnThoatSearch;
    RecyclerView recycleSearch;
    ArrayList<BeforeSearchClass> arrayListBefore;
    BeforeSearchAdapter beforeAdapter;
    SearchView search;
    TextView txtThoatSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        arrayListBefore = new ArrayList<>();

        AnhXa();
        BeforeSearchSetData();
        search.clearFocus();
        search.onActionViewExpanded();
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TimKiem(query);
                // Submit the search will hide the keyboard
                search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        txtThoatSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeScreenActivity.class);
                SearchActivity.this.startActivity(intent);
            }
        });
    }

    private void TimKiem(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this,ChiTietTimKiemActivity.class);
        intent.putExtra("query",query);
        SearchActivity.this.startActivity(intent);

    }

    private void BeforeSearchSetData() {
        recycleSearch.setHasFixedSize(true);
        recycleSearch.setLayoutManager(new GridLayoutManager(this,2));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Gang tay đi xe"));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Máy duỗi tóc"));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Máy ép tóc"));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Mũ bảo hiểm"));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Áo chống nắng"));
        arrayListBefore.add(new BeforeSearchClass(R.drawable.gangtay,"Dầu gội khô"));
        beforeAdapter = new BeforeSearchAdapter(arrayListBefore,SearchActivity.this);
        recycleSearch.setAdapter(beforeAdapter);
        beforeAdapter.notifyDataSetChanged();
    }

    private void AnhXa() {
        search = findViewById(R.id.search);
        recycleSearch = findViewById(R.id.recycleSearch);
        recycleSearch = findViewById(R.id.recycleSearch);
        txtThoatSearch = findViewById(R.id.txtThoatSearch);
    }
}