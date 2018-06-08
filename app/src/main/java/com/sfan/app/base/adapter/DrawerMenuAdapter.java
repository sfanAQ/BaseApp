package com.sfan.app.base.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfan.app.base.OnItemClickListener;
import com.sfan.app.base.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzy on 2018/6/11.
 * 左抽屉菜单
 */

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.ViewHolder> {

    private String[] menus;
    private TypedArray icons;
    private OnItemClickListener onItemClickListener;

    public DrawerMenuAdapter(Context context) {
        Resources resources = context.getResources();
        menus = resources.getStringArray(R.array.drawer_menus);
        icons = resources.obtainTypedArray(R.array.icons_drawer_menus);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drawer_menu, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imgItem.setImageDrawable(icons.getDrawable(position));
        holder.txtItem.setText(menus[position]);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return menus == null ? 0 : menus.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imgItem)
        ImageView imgItem;
        @BindView(R.id.txtItem)
        TextView txtItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, this);
            }
        }
    }

}
