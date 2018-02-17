package workshop.akbolatss.configtestapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import workshop.akbolatss.configtestapp.model.ServiceCategory;

/**
 * Created by imac on 16.02.18.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesVH> {

    private List<ServiceCategory> mList;
    private CategoryClickListener mListener;

    public CategoriesAdapter(CategoryClickListener listener) {
        mList = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public CategoriesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_categories, parent, false);
        return new CategoriesVH(view);
    }

    @Override
    public void onBindViewHolder(CategoriesVH holder, int position) {
        holder.bind(mList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public void addItems(List<ServiceCategory> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void updateItemState(int pos, boolean isChecked) {
        mList.get(pos).setSelected(isChecked);
        notifyItemChanged(pos);
    }

    public interface CategoryClickListener {
        void onItemChecked(ServiceCategory serviceCategory, int pos, boolean isChecked);
    }

    class CategoriesVH extends RecyclerView.ViewHolder {
        Context context;

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.imgLogo)
        ImageView mLogo;

        @BindView(R.id.cbSelect)
        CheckBox cbSelect;

        public CategoriesVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bind(final ServiceCategory serviceCategory, final CategoryClickListener mListener) {
            tvName.setText(serviceCategory.getTitle());

            Picasso.with(context)
                    .load(serviceCategory.getIcon().getSmall())
                    .into(mLogo);

            cbSelect.setChecked(serviceCategory.isSelected());

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    serviceCategory.setSelected(isChecked);
                    mListener.onItemChecked(serviceCategory, getAdapterPosition(), isChecked);
                }
            });
        }
    }
}
