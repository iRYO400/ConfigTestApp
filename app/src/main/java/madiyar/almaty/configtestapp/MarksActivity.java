package madiyar.almaty.configtestapp;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import madiyar.almaty.configtestapp.model.CarMarkModel;
import madiyar.almaty.configtestapp.model.MarkModel;

public class MarksActivity extends AppCompatActivity implements MarksAdapter.OnMarkClickListener, SearchView.OnQueryTextListener {

    private static final String TAG = "TAG";

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerV;
    private MarksAdapter mAdapter;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private List<MarkModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRV();
        initData();
    }

    private void initRV() {
        mRecyclerV.setHasFixedSize(true);
        mRecyclerV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MarksAdapter(this);
        mRecyclerV.setAdapter(mAdapter);
    }


    private void initData() {
        String s = loadJSONFromAsset();
        Type type = new TypeToken<CarMarkModel>() {
        }.getType();

        Gson gson = new Gson();

        CarMarkModel data = gson.fromJson(s, type);

        modelList = data.getList();
        mAdapter.addItems(modelList);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("marks.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onMarkClicked(MarkModel markModel) {
        Gson gson = new Gson();
        String obj = gson.toJson(markModel);
        Intent intent = new Intent();
        intent.putExtra("MarkModel", obj);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MarkModel> filteredModelList = filter(modelList, newText);
        mAdapter.addItems(filteredModelList);
        mRecyclerV.scrollToPosition(0);
        return true;
    }

    private static List<MarkModel> filter(List<MarkModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<MarkModel> filteredModelList = new ArrayList<>();
        for (MarkModel model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
