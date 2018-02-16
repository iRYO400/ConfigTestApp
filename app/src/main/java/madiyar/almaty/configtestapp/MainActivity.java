package madiyar.almaty.configtestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import madiyar.almaty.configtestapp.model.CarMarkModel;
import madiyar.almaty.configtestapp.model.MarkModel;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_MARK = 100;
    private static final String TAG = "TAG";

    @BindView(R.id.flSelectMarks)
    protected FrameLayout mFlSelectMarks;

    @BindView(R.id.flSelectYear)
    protected FrameLayout mFlSelectYear;

    @BindView(R.id.flPartsCategory)
    protected FrameLayout mFlPartsCategory;

    @BindView(R.id.flServiceCategory)
    protected FrameLayout mFlServiceCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.flSelectMarks)
    protected void openSelectMarks(){
        Intent intent = new Intent(this, MarksActivity.class);
        startActivityForResult(intent, REQUEST_MARK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MARK){
            if (resultCode == RESULT_OK){

                Gson gson = new Gson();

                MarkModel model = gson.fromJson(data.getStringExtra("MarkModel"), MarkModel.class);

                Toast.makeText(this, "Model is " + model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
