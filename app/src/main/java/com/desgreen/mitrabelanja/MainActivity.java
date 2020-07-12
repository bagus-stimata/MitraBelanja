package com.desgreen.mitrabelanja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desgreen.mitrabelanja.adapter.CategoryAdapter;
import com.desgreen.mitrabelanja.adapter.ProductAdapter;
import com.desgreen.mitrabelanja.fragments.FragmentHome;
import com.desgreen.mitrabelanja.util.Constants;
import com.erp.distribution.sfa.model.CategoryModel;
import com.erp.distribution.sfa.model.FMaterial;
import com.erp.distribution.sfa.model.FMaterialGroup3;
import com.erp.distribution.sfa.security_config.ApiAuthenticationClient;
import com.erp.distribution.sfa.security_model.FUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ApiAuthenticationClient apiAuthenticationClient;
    MainViewModel mainViewModel;
    FUser userActive = new FUser();

    CoordinatorLayout coordinatorLayout;
    BottomNavigationView navigation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchBarRelativeLayout)
    RelativeLayout searchBarRelativeLayout;


    private LinearLayout content_main1;
    ProductAdapter productAdapter;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        /**
         * R.id.toolbar -> search view
         * R.id.menu -> berisi Chart Shop
         */
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        searchBarRelativeLayout.setVisibility(View.VISIBLE);


        // initialize bottom navigation view
//        navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        LinearLayout navHome = findViewById(R.id.nav_home);
//        navHome.setOnClickListener( (View view) -> {
//            Toast.makeText(MainActivity.this, "Hello Home", Toast.LENGTH_LONG).show();
//        });


        /**
         * Dummy Get User Password
         */
        apiAuthenticationClient = ApiAuthenticationClient.getInstance();

        apiAuthenticationClient.setBaseUrl("http://ssp-surabaya.ddns.net:8989/rest/");
//        apiAuthenticationClient.setBaseUrl("http://192.168.1.100:8989/rest/");
        apiAuthenticationClient.setUsername("bagus");
        apiAuthenticationClient.setPassword("hacker");

        userActive = mainViewModel.fUserServiceRest.getFUserByUsername("bagus");

        callHomeFragment();







    }


    private void loadCategs(LinearLayout root, String title) {
        try {

            View v = getLayoutInflater().inflate(R.layout.categ_list, null);
            RecyclerView rv = v.findViewById(R.id.categHorizontalRecyclerView1);

            List<CategoryModel> pdata = new ArrayList<CategoryModel>();

            int counter = 0;
            for (FMaterialGroup3 fMaterialGroup3: mainViewModel.fMaterialGroup3ServiceRest.getAllFMaterialGroup3()) {
                CategoryModel categoryModel1 = new CategoryModel(fMaterialGroup3.getId(), 1, fMaterialGroup3.getDescription(),"img1",false);
                pdata.add(categoryModel1);
                counter++;
                if (counter >7) break;
            }

            rv.setAdapter(new CategoryAdapter(pdata, this));
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);

            rv.setLayoutManager(new GridLayoutManager(this, 5)); //Item Barang Kebawah

            root.addView(v);
        } catch (Exception e) {
        }
    }


    private void loadFlashSales(LinearLayout root, String title, int startpos, int endpos) {
        try {
            View v = getLayoutInflater().inflate(R.layout.product_list, null);
            RecyclerView rv = v.findViewById(R.id.producthorizontalRecyclerView1);
            TextView tv = v.findViewById(R.id.producthorizontalTextView1);
            tv.setText(title);
            List<FMaterial> pdata = new ArrayList<FMaterial>();

            int counter = 0;
            for (FMaterial p: mainViewModel.fMaterialServiceRest.getAllFMaterialByDivision(userActive.getFdivisionBean(), 0, 2) ) {
                pdata.add(p);
                counter++;
                if (counter>50) break;
            }

            rv.setAdapter(new ProductAdapter(pdata, this));
//            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
            root.addView(v);

        } catch (Exception e) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }



//    // Set Toolbar Icons Click Listeners
//    private void setToolbarIconsClickListeners() {
//        ImageView cart = findViewById(R.id.cart);
//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (cartCount > 0) {
//                    startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
//                    overridePendingTransition(0,0);
//                } else {
//                    Toast.makeText(getApplicationContext(), R.string.cart_empty, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    /**
     * BottomNavigationView Listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // Hide Back Button
//            backButton.setVisibility(View.INVISIBLE);

            // Prevent Reload Same Fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_home: // Home
                    // Prevent Reload
                    try {
                        if (!fm.findFragmentByTag("HOME").isVisible()) {
//                            callProductsFragment();
//                            titleToolbar.setText(R.string.TitleHome);
                        }
                    } catch (NullPointerException e) {
//                        callProductsFragment();
//                        titleToolbar.setText(R.string.TitleHome);
                    }
                    return true;

//                case R.id.nav_categories: // Categories
//                    ft.replace(R.id.content, new Categories());
//                    ft.commit();
//                    titleToolbar.setText(R.string.TitleCategories);
//                    return true;
//
//                case R.id.nav_shortlist: // Wish List
//                    ft.replace(R.id.content, new WishList());
//                    ft.commit();
//                    titleToolbar.setText(R.string.TitleShortlist);
//                    return true;
//
//                case R.id.nav_account: // User Account
//                    ft.replace(R.id.content, new Account());
//                    ft.commit();
//                    titleToolbar.setText(R.string.TitleAccount);
//                    return true;
            }
            return false;
        }
    };

    @OnClick(R.id.nav_home)
    void navBottomHome(){
        callHomeFragment();
    }
    @OnClick(R.id.nav_category)
    void navBottomCategory(){

    }
    @OnClick(R.id.nav_whish)
    void navBottomWhish(){

    }
    @OnClick(R.id.nav_account)
    void navBottomAccount(){

    }

    void callHomeFragment(){
        try {
            if (fm.findFragmentByTag("HOME") ==null) {
                Bundle args = new Bundle();
                args.putInt(Constants.CAT_ID_KEY, 0);

                FragmentHome products = new FragmentHome();
                products.setArguments(args);

                ft.replace(R.id.content, products, "HOME");
                ft.commit();

            }else if ( !fm.findFragmentByTag("HOME").isVisible()) {
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
//                        callProductsFragment();
//                        titleToolbar.setText(R.string.TitleHome);
        }

    }

}