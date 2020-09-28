package com.desgreen.mitrabelanja.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.erp.distribution.sfa.model.FMaterial;
import com.desgreen.mitrabelanja.R;
import com.loopj.android.image.SmartImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Azhar Rivaldi on 01/12/2019.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holdr> {
    List<FMaterial> data = new ArrayList<FMaterial>();
    private OnItemClickListener listener;

    private float mWidth, mHeight;

    public ProductAdapter(List<FMaterial> data, Context ctx) {
        this.data = data;
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        mWidth = dm.widthPixels / ctx.getResources().getDimension(R.dimen.grid_width);
        mHeight = dm.heightPixels / ctx.getResources().getDimension(R.dimen.grid_height);
    }

    @Override
    public Holdr onCreateViewHolder(ViewGroup p1, int p2) {
        return new Holdr(LayoutInflater.from(p1.getContext()).inflate(R.layout.item_product_card, null));
    }

    @Override
    public void onBindViewHolder(Holdr holdr, int pos) {
        FMaterial domain = data.get(pos);
        holdr.name.setText(domain.getPname());

//        if (isImageUrlExist(domain.getPname())) {
//            holdr.img.setImageUrl(domain.getPname());
//        }

//        holdr.store.setText(domain.getFdivisionBean());
//        holdr.store.setText("MB-" + domain.getId() );
        holdr.store.setText("MB-" + domain.getPcode() );
        holdr.price.setText(_priceFormat("" + domain.getSprice2AfterPpn()));
//        holdr.priceold.setText(_priceFormat("" + domain.getSprice2AfterPpn()));
        holdr.priceold.setText("");
        holdr.priceold.setPaintFlags(holdr.priceold.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        holdr.ratingbar.setRating(domain.getSourceID());
    }

    public FMaterial getDataAt(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holdr extends RecyclerView.ViewHolder {
        TextView name, price, priceold, discount, store;
        SmartImageView img;
        RatingBar ratingbar;

        public Holdr(final View view) {
            super(view);
            view.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    lp.height = (int) mHeight;
                    lp.width = (int) mWidth;
                    view.setLayoutParams(lp);
                }
            });
            name = view.findViewById(R.id.itemproductTextViewName);
            price = view.findViewById(R.id.itemproductTextViewPrice);
            priceold = view.findViewById(R.id.itemproductTextViewPold);
            discount = view.findViewById(R.id.itemproductTextViewDisc);
            store = view.findViewById(R.id.itemproductTextViewStore);
            img = view.findViewById(R.id.itemproductImageView1);
            ratingbar = view.findViewById(R.id.itemproductRatingBar1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(data.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(FMaterial domain);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private static String _priceFormat(String s) {
        double parsed = Double.parseDouble(s);

        Locale localeID = new Locale("in", "ID");
        String formatted = NumberFormat.getInstance(localeID).format(parsed);

//        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//
//        formatRp.setCurrencySymbol("Rp. ");
//        formatRp.setMonetaryDecimalSeparator(',');
//        formatRp.setGroupingSeparator('.');
//
//        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        String formatted = kursIndonesia.format(parsed);

        return formatted;
    }


    public Boolean isImageUrlExist(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            responseCode = huc.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responseCode==HttpURLConnection.HTTP_OK) {
            return  true;
        }
        return false;
    }
}
