package workshop.akbolatss.configtestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workshop.akbolatss.configtestapp.model.MarkModel;
import workshop.akbolatss.configtestapp.model.ServiceCategory;

import static workshop.akbolatss.configtestapp.Constants.CARD_VIEW;
import static workshop.akbolatss.configtestapp.Constants.MARK_MODEL_KEY;
import static workshop.akbolatss.configtestapp.Constants.SELECTED_SERVICES_KEY;

public class MainActivity extends AppCompatActivity implements MarksAdapter.OnMarkClickListener {

    private static final int REQUEST_MARK = 100;
    private static final int REQUEST_PARTS = 200;

    private static final String TAG = MarksActivity.class.getName();

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerV;
    private MarksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRV();
    }

    private void initRV() {
        mRecyclerV.setHasFixedSize(true);
        mRecyclerV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new MarksAdapter(this, CARD_VIEW);
        mRecyclerV.setAdapter(mAdapter);
    }

    @OnClick(R.id.flSelectMarks)
    protected void openSelectMarks(){
        Intent intent = new Intent(this, MarksActivity.class);
        startActivityForResult(intent, REQUEST_MARK);
    }

    @OnClick({R.id.flPartsCategory, R.id.flServiceCategory})
    protected void openPartsServices(){
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_PARTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MARK){
            if (resultCode == RESULT_OK){

                Gson gson = new Gson();

                MarkModel model = gson.fromJson(data.getStringExtra(MARK_MODEL_KEY), MarkModel.class);

                mAdapter.addItem(model);
            }
        }
        if (requestCode == REQUEST_PARTS){
            if (resultCode == RESULT_OK){
                Gson gson = new Gson();
                List<ServiceCategory> serviceCategories = gson.fromJson(data.getStringExtra(SELECTED_SERVICES_KEY), new TypeToken<List<ServiceCategory>>(){}.getType());
                String s = "";
                for (ServiceCategory serviceCategory: serviceCategories){
                    s = s + " " + serviceCategory.getTitle();
                }
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMarkClicked(MarkModel markModel) {
    }

    @Override
    public void onMarkRemoved(int pos) {
        mAdapter.removeItem(pos);
    }
}
