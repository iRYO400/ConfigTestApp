package madiyar.almaty.configtestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import madiyar.almaty.configtestapp.model.ServiceCategory;

/**
 * Created by imac on 16.02.18.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesVH> {

    List<ServiceCategory> mList;

    public CategoriesAdapter(){
        mList = new ArrayList<>();
    }

    @Override
    public CategoriesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_categories, parent, false);
        return new CategoriesVH(view);
    }

    @Override
    public void onBindViewHolder(CategoriesVH holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    class CategoriesVH extends RecyclerView.ViewHolder {
        public CategoriesVH(View itemView) {
            super(itemView);
        }

        public void bind(ServiceCategory serviceCategory) {

        }
    }

}
