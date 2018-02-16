package madiyar.almaty.configtestapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import madiyar.almaty.configtestapp.model.MarkModel;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksVH> {


    private List<MarkModel> modelList;

    private OnMarkClickListener mListener;
    private int mViewType;

    public MarksAdapter(OnMarkClickListener listener){
        modelList = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public MarksVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_select_mark, parent, false);
        return new MarksVH(view);
    }

    @Override
    public void onBindViewHolder(MarksVH holder, int position) {
        holder.bind(modelList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        if (modelList == null) {
            return 0;
        }
        return modelList.size();
    }

    public void addItems(List<MarkModel> list) {
        if (list != null){
            modelList.clear();
            modelList.addAll(list);
            notifyDataSetChanged();
        }
    }


    public interface OnMarkClickListener{
        void onMarkClicked(MarkModel markModel);
    }

    class MarksVH extends RecyclerView.ViewHolder {

        Context context;

        @BindView(R.id.tvName)
        protected TextView tvName;

        @BindView(R.id.imgLogo)
        ImageView mLogo;

        @BindView(R.id.llMark)
        LinearLayout linearLayout;

        public MarksVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bind(final MarkModel markModel, final OnMarkClickListener mListener) {
            tvName.setText(markModel.getTitle());

            Picasso.with(context)
                    .load(markModel.getIcon().getSmall())
                    .into(mLogo);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onMarkClicked(markModel);
                }
            });
        }
    }

}
