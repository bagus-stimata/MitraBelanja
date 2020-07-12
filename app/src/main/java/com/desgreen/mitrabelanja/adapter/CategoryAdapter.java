package com.desgreen.mitrabelanja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.desgreen.mitrabelanja.R;
import com.erp.distribution.sfa.model.CategoryModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azhar Rivaldi on 01/12/2019.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holdr> {
    List<CategoryModel> data = new ArrayList<CategoryModel>();

    private float mWidth, mHeight;

    public CategoryAdapter(List<CategoryModel> data, Context ctx) {
        this.data = data;
//        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
//        mWidth = dm.widthPixels / ctx.getResources().getDimension(R.dimen.grid_width);
//        mHeight = dm.heightPixels / ctx.getResources().getDimension(R.dimen.grid_height);
    }

    @Override
    public Holdr onCreateViewHolder(ViewGroup p1, int p2) {
        return new Holdr(LayoutInflater.from(p1.getContext()).inflate(R.layout.item_category_card, null));
    }

    @Override
    public void onBindViewHolder(Holdr holdr, int pos) {
        CategoryModel cat = data.get(pos);
        holdr.name.setText(cat.title);
        holdr.img1.setBackgroundResource(R.drawable.ic_account);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holdr extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img1;

        public Holdr(final View view) {
            super(view);
            /**
             * Untuk membuat minimal Height
             */
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//                    ViewGroup.LayoutParams lp = view.getLayoutParams();
//                    lp.height = (int) mHeight;
//                    lp.width = (int) mWidth;
//                    view.setLayoutParams(lp);
//                }
//            });
            img1 = view.findViewById(R.id.itemCateg_ImageView1);
            name = view.findViewById(R.id.itemCateg_TextViewTitle);
        }
    }

    private static String _priceFormat(String s) {
        double parsed = Double.parseDouble(s);
        String formatted = NumberFormat.getCurrencyInstance().format(parsed);
        return formatted;
    }
}
