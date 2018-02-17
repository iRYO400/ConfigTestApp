package workshop.akbolatss.configtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import workshop.akbolatss.configtestapp.model.CarMarkModel;
import workshop.akbolatss.configtestapp.model.MarkModel;

import static workshop.akbolatss.configtestapp.Constants.LIST_VIEW;
import static workshop.akbolatss.configtestapp.Constants.MARK_MODEL_KEY;

public class MarksActivity extends AppCompatActivity implements MarksAdapter.OnMarkClickListener, SearchView.OnQueryTextListener {

    private static final String TAG = MarksActivity.class.getName();

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerV;
    private MarksAdapter mAdapter;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private List<MarkModel> modelList;

    private Gson mGson;

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
        mAdapter = new MarksAdapter(this, LIST_VIEW);
        mRecyclerV.setAdapter(mAdapter);
    }


    private void initData() {
        mGson = new Gson();
        String json = loadJSONFromAsset();

        CarMarkModel data = mGson.fromJson(json, CarMarkModel.class);

        modelList = data.getList();
        mAdapter.addItems(modelList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_marks, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getResources().getString(R.string.hint_search_mark));
        item.expandActionView();
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMarkClicked(MarkModel markModel) {
        String obj = mGson.toJson(markModel);
        Intent intent = new Intent();
        intent.putExtra(MARK_MODEL_KEY, obj);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onMarkRemoved(int pos) {

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

    private static List<MarkModel> filter(List<MarkModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<MarkModel> filteredList = new ArrayList<>();
        for (MarkModel model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }
}
