package workshop.akbolatss.configtestapp;

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
import workshop.akbolatss.configtestapp.model.MarkModel;

import static workshop.akbolatss.configtestapp.Constants.CARD_VIEW;
import static workshop.akbolatss.configtestapp.Constants.LIST_VIEW;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarkVH> {

    private List<MarkModel> modelList;

    private OnMarkClickListener mListener;
    private int mViewType;

    public MarksAdapter(OnMarkClickListener listener, int viewType) {
        modelList = new ArrayList<>();
        mListener = listener;
        mViewType = viewType;
    }

    @Override
    public MarkVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == LIST_VIEW) {
            view = inflater.inflate(R.layout.rv_select_mark, parent, false);
            return new SelectMarkVH(view);
        } else {
            view = inflater.inflate(R.layout.rv_card_mark, parent, false);
            return new MarkCardVH(view);
        }
    }

    @Override
    public void onBindViewHolder(MarkVH holder, int position) {
        (holder).bind(modelList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        if (modelList == null) {
            return 0;
        }
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mViewType == LIST_VIEW)
            return LIST_VIEW;
        else
            return CARD_VIEW;
    }

    public void addItems(List<MarkModel> list) {
        if (list != null) {
            modelList.clear();
            modelList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addItem(MarkModel model) {
        modelList.add(model);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        modelList.remove(pos);
        notifyDataSetChanged();
    }

    public interface OnMarkClickListener {

        void onMarkClicked(MarkModel markModel);

        void onMarkRemoved(int pos);
    }

    abstract class MarkVH extends RecyclerView.ViewHolder {

        Context context;

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.imgLogo)
        ImageView mLogo;

        public MarkVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        abstract void bind(MarkModel markModel, OnMarkClickListener mListener);
    }

    class SelectMarkVH extends MarkVH {

        @BindView(R.id.llMark)
        LinearLayout linearLayout;

        public SelectMarkVH(View itemView) {
            super(itemView);
        }

        @Override
        void bind(final MarkModel markModel, final OnMarkClickListener mListener) {
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

    class MarkCardVH extends MarkVH {

        @BindView(R.id.imgClose)
        ImageView imgClose;

        public MarkCardVH(View itemView) {
            super(itemView);
        }

        @Override
        void bind(final MarkModel markModel, final OnMarkClickListener mListener) {
            tvName.setText(markModel.getTitle());

            Picasso.with(context)
                    .load(markModel.getIcon().getNormal())
                    .into(mLogo);

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onMarkRemoved(getAdapterPosition());
                }
            });
        }
    }

}
