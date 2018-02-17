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
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workshop.akbolatss.configtestapp.model.ServiceCategory;
import workshop.akbolatss.configtestapp.model.ServiceModel;

import static workshop.akbolatss.configtestapp.Constants.SELECTED_SERVICES_KEY;

public class CategoriesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, CategoriesAdapter.CategoryClickListener {

    private static final String TAG = MarksActivity.class.getName();

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.flSelectAll)
    protected FrameLayout flSelectAll;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerV;
    private CategoriesAdapter mAdapter;

    @BindView(R.id.tvDone)
    protected TextView tvDone;

    private List<ServiceCategory> modelList;
    private List<ServiceCategory> selectedList;

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
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
        mAdapter = new CategoriesAdapter(this);
        mRecyclerV.setAdapter(mAdapter);
    }


    private void initData() {
        mGson = new Gson();
        String json = loadJSONFromAsset();

        ServiceModel data = mGson.fromJson(json, ServiceModel.class);

        modelList = data.getServiceCategories();
        mAdapter.addItems(modelList);

        selectedList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_marks, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getResources().getString(R.string.hint_search_service));
        item.expandActionView();
        searchView.onActionViewExpanded();
        searchView.clearFocus();

        ImageView closeBtn = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeBtn.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @OnClick(R.id.flSelectAll)
    protected void selectAll() {
        for (int i = 0; i < mAdapter.getItemCount() - 1; i++) {
            mAdapter.updateItemState(i, true);
        }

        changeBtnState();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ServiceCategory> filteredModelList = filter(modelList, newText);
        mAdapter.addItems(filteredModelList);
        mRecyclerV.scrollToPosition(0);
        return true;
    }

    @OnClick(R.id.tvDone)
    protected void doneSelected(){
        String obj = mGson.toJson(selectedList);
        Intent intent = new Intent();
        intent.putExtra(SELECTED_SERVICES_KEY, obj);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemChecked(ServiceCategory serviceCategory, int pos, boolean isChecked) {
        if (isChecked) {
            selectedList.add(serviceCategory);
        } else {
            selectedList.remove(serviceCategory);
        }
        changeBtnState();
    }

    private void changeBtnState(){
        if (selectedList.size() > 0) {
            tvDone.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            tvDone.setTextColor(getResources().getColor(R.color.colorTextInactive));
        }
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("categories.json");
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

    private static List<ServiceCategory> filter(List<ServiceCategory> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<ServiceCategory> filteredList = new ArrayList<>();
        for (ServiceCategory model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }
}
