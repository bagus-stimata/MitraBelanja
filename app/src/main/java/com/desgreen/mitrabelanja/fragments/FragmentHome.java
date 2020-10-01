package com.desgreen.mitrabelanja.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.desgreen.mitrabelanja.MainActivity;
import com.desgreen.mitrabelanja.MainViewModel;
import com.desgreen.mitrabelanja.R;
import com.desgreen.mitrabelanja.activities.ProductDetails;
import com.desgreen.mitrabelanja.adapter.CategoryAdapter;
import com.desgreen.mitrabelanja.adapter.ProductAdapter;
import com.desgreen.mitrabelanja.adapter.SliderImageAdapter;
import com.desgreen.mitrabelanja.util.Constants;
import com.erp.distribution.sfa.model.CategoryModel;
import com.erp.distribution.sfa.model.FMaterial;
import com.erp.distribution.sfa.model.FMaterialGroup1;
import com.erp.distribution.sfa.model.FMaterialGroup3;
import com.erp.distribution.sfa.security_config.ApiAuthenticationClient;
import com.erp.distribution.sfa.security_model.FUser;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Preeth on 1/3/2018
 */

public class FragmentHome extends Fragment {


    ApiAuthenticationClient apiAuthenticationClient;
    MainViewModel mainViewModel;
    FUser userActive = new FUser();

    RelativeLayout sort, filter;
    TextView sortByText;
    String[] sortByArray = {"Most Recent", "Most Orders", "Most Shares", "Most Viewed"};
    int sortById = 0, cat_id = 0;
    GridView productsGrid;
    List<String> sizeFilter = new ArrayList<>();
    List<String> colorFilter = new ArrayList<>();

//    ProductAdapter productListAdapter;
//    List<FMaterial> productList;


//    ToolbarTitle toolbarTitleCallback;
//    ShowBackButton showBackButtonCallback;

    Toolbar toolbar;
    RelativeLayout searchBarRelativeLayout;

    TextView greetText;
    SliderView sliderMyshop;

    private LinearLayout main_content_content;
    private NestedScrollView main_NestedSrollView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        toolbarTitleCallback = (ToolbarTitle) context;
//        showBackButtonCallback = (ShowBackButton) context;
        apiAuthenticationClient = ApiAuthenticationClient.getInstance();
        /**
         * Dummy Get User Password
         */
        apiAuthenticationClient = ApiAuthenticationClient.getInstance();

        apiAuthenticationClient.setBaseUrl("http://ssp-surabaya.ddns.net:8989/rest/");
//        apiAuthenticationClient.setBaseUrl("http://192.168.1.100:8989/rest/");
        apiAuthenticationClient.setUsername("user01");
        apiAuthenticationClient.setPassword("Welcome1");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

//        System.out.println(mainViewModel.getfUserServiceRest().getAllFUser());
//        userActive = mainViewModel.getfUserServiceRest().getFUserByUsername(apiAuthenticationClient.getUsername());
//        System.out.println(apiAuthenticationClient.getUsername() + " >>>>> " + userActive.getUsername());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);

        main_content_content = view.findViewById(R.id.main_content_layout);

        main_NestedSrollView = view.findViewById(R.id.main_NestedSrollView);

        setIds(view);
//        setSortListener();
//        setFilterListener();

        // get category id
        Bundle args = getArguments();
        assert args != null;
        cat_id = args.getInt(Constants.CAT_ID_KEY);

        if (cat_id > 0) {
            // Show Back Button and Set Title
//            showBackButtonCallback.showBackButton();
//            toolbarTitleCallback.setToolbarTitle(args.getString(Constants.TITLE));
        }

        // Get Data and Fill Grid
//        sortByText.setText(sortByArray[0]);
//        fillGridView();

        greetText = view.findViewById(R.id.greeting_text);
        greeting(userActive);


        /**
         * Slider Image
         */
        sliderMyshop = view.findViewById(R.id.imageSlider);

        final SliderImageAdapter sliderImageAdapter = new SliderImageAdapter(getContext());
        sliderImageAdapter.setCount(4);
        sliderMyshop.setSliderAdapter(sliderImageAdapter);
        sliderMyshop.setIndicatorAnimation(IndicatorAnimations.WORM.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderMyshop.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderMyshop.setIndicatorSelectedColor(Color.WHITE);
        sliderMyshop.setIndicatorUnselectedColor(Color.GRAY);
        sliderMyshop.startAutoCycle();
        sliderMyshop.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderMyshop.setCurrentPagePosition(position);
            }
        });

        userActive = mainViewModel.getfUserServiceRest().getFUserByUsername(apiAuthenticationClient.getUsername());
        System.out.println(mainViewModel.getfUserServiceRest().getAllFUser());
        System.out.println(apiAuthenticationClient.getUsername() + " >>>>> " + userActive.getUsername());

        loadCategs(main_content_content, "");
        loadFlashSales(main_content_content, "Flash Sales", 0, 25);

        loadProducts(main_content_content, "Semua Produk", 26, 0);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // Set Ids
    private void setIds(View view) {
//        sort = view.findViewById(R.id.sortLay);
//        filter = view.findViewById(R.id.filterLay);
//        sortByText = view.findViewById(R.id.sortBy);
//        productsGrid = view.findViewById(R.id.productsGrid);
    }



    private void loadCategs(LinearLayout root, String title) {
        try {

            View v = getLayoutInflater().inflate(R.layout.categ_list, null);
            RecyclerView rv = v.findViewById(R.id.categHorizontalRecyclerView1);

            List<CategoryModel> pdata = new ArrayList<CategoryModel>();

            int counter = 0;
//            for (FMaterialGroup3 fMaterialGroup3: mainViewModel.getfMaterialGroup3ServiceRest().getAllFMaterialGroup3()) {
//                CategoryModel categoryModel1 = new CategoryModel(fMaterialGroup3.getId(), 1, fMaterialGroup3.getDescription(),"img1",false);
//                pdata.add(categoryModel1);
//                counter++;
//                if (counter >7) break;
//            }
            for (FMaterialGroup1 fMaterialGroup1: mainViewModel.getfMaterialGroup1ServiceRest().getAllFMaterialGroup1() ) {
                CategoryModel categoryModel1 = new CategoryModel(fMaterialGroup1.getId(), 1, fMaterialGroup1.getDescription(),"img1",false);
                if (! fMaterialGroup1.getDescription().toUpperCase().contains("OTH") && fMaterialGroup1.isStatusActive()) {
                    pdata.add(categoryModel1);
                    counter++;
                    if (counter > 7) break;
                }
            }

            rv.setAdapter(new CategoryAdapter(pdata, getContext()));
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);

            rv.setLayoutManager(new GridLayoutManager(getContext(), 5)); //Item Barang Kebawah

            root.addView(v);

//            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                }
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    Toast.makeText(getActivity(), "Bawah"
//                            , Toast.LENGTH_SHORT).show();
//
//                }
//            });


        } catch (Exception e) {
        }
    }





    private void loadFlashSales(LinearLayout root, String title, int startpos, int endpos) {
        ProductAdapter productAdapter;
        try {
            View v = getLayoutInflater().inflate(R.layout.product_list, null);
            RecyclerView rv = v.findViewById(R.id.producthorizontalRecyclerView1);
            TextView tv = v.findViewById(R.id.producthorizontalTextView1);
            tv.setText(title);
            List<FMaterial> pdata = new ArrayList<FMaterial>();

            for (FMaterial p: mainViewModel.getfMaterialServiceRest().getAllFMaterialByDivision(userActive.getFdivisionBean(), 0, 3) ) {
                pdata.add(p);

            }

            rv.setAdapter(new ProductAdapter(pdata, getContext()));
//            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
            root.addView(v);

        } catch (Exception e) {
        }
    }



    private boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int currentPage = 0;

    private void loadProducts(LinearLayout root, String title, int startpos, int endpos) {
        ProductAdapter productAdapter;
        try {

            View v = getLayoutInflater().inflate(R.layout.product_list, null);
            RecyclerView rv = v.findViewById(R.id.producthorizontalRecyclerView1);
            TextView tv = v.findViewById(R.id.producthorizontalTextView1);
            tv.setText(title);
            List<FMaterial> pdata = new ArrayList<FMaterial>();

            for (FMaterial p: mainViewModel.getfMaterialServiceRest().getAllFMaterialByDivision(userActive.getFdivisionBean(), 0, 4) ) {
//            for (FMaterial p: mainViewModel.getfMaterialServiceRest().getAllFMaterial()) {
                System.out.println("Oke ada: " + p.getPname());
                pdata.add(p);
            }

            rv.setAdapter(new ProductAdapter(pdata, getContext()));
            productAdapter = new ProductAdapter(pdata, getContext());
            rv.setAdapter(productAdapter);

//            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//            rv.setNestedScrollingEnabled(false);
//            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
//            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);

//            rv.setLayoutManager(new GridLayoutManager(this, 2)); //Item Barang Kebawah
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            rv.setLayoutManager(layoutManager);

            root.addView(v);

            productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FMaterial domain) {
                    Toast.makeText(getContext(), domain.getPname(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getContext(), ProductDetails.class);
//                    intent.putExtra("ProductId", productList.get(position).getId() );
                    intent.putExtra("ProductId",  domain.getId() );
                    intent.putExtra(ProductDetails.EXTRA_OBJECT, domain);

                    getContext().startActivity(intent);
                    Activity activity = (Activity) getContext();
                    activity.overridePendingTransition(0,0);

                }
            });


            /**
             * Pagination
             */


            main_NestedSrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
//                        Log.i(getTag(), "Scroll DOWN");
                    }
                    if (scrollY < oldScrollY) {
//                        Log.i(getTag(), "Scroll UP");
                    }
                    if (scrollY == 0) {
//                        Log.i(getTag(), "TOP SCROLL");
                    }
//                    if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
//                        Log.i(getTag(), "BOTTOM SCROLL");
//                    }


                    if (scrollY >= (  v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                        pdata.addAll(mainViewModel.getfMaterialServiceRest().getAllFMaterialByDivision(userActive.getFdivisionBean(), currentPage++, 4));
                        productAdapter.notifyDataSetChanged();
                        Log.i(getTag(), "SCROLL: " + scrollY + ">>" + v.getMeasuredHeight() + ">>" + v.getChildAt(0).getMeasuredHeight() );
                    }


                }
            });


            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true;
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    currentItems = layoutManager.getChildCount();
                    totalItems = layoutManager.getItemCount();
                    scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollOutItems >= totalItems)) {
//                        pdata.addAll(mainViewModel.getfMaterialServiceRest().getAllFMaterialByDivision(userActive.getFdivisionBean(), currentPage, 4));
//                        productAdapter.notifyDataSetChanged();
//                        currentPage++;
//                        isScrolling = false;
                    }

                }
            });

        } catch (Exception e) {
        }

    }


    private void greeting(FUser fuserBean) {
        String pengguna = "";
        if (fuserBean !=null) {
            if (fuserBean.getFullName().equals("")) {
                pengguna = fuserBean.getFullName();
            }else {
                pengguna = fuserBean.getUsername();
            }
//            pengguna += "-" + fuserBean.getFdivisionBean();
        }
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetText.setText(getString(R.string.salam_pagi));
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText(getString(R.string.salam_siang));
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText(getString(R.string.salam_sore));
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText(getString(R.string.salam_malam));
        }
        greetText.setText(greetText.getText() + " " + pengguna);
    }



}
