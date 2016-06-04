package emreuzun.com.tabletmenu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import emreuzun.com.tabletmenu.ActivityItemDetails;
import emreuzun.com.tabletmenu.ActivityMain;
import emreuzun.com.tabletmenu.R;
import emreuzun.com.tabletmenu.adapter.ItemGridAdapter;
import emreuzun.com.tabletmenu.data.Constant;
import emreuzun.com.tabletmenu.data.ServerData;
import emreuzun.com.tabletmenu.data.Tools;
import emreuzun.com.tabletmenu.model.ItemModel;
import emreuzun.com.tabletmenu.retrofit.CategoryRetrofit;
import emreuzun.com.tabletmenu.retrofit.Product;
import emreuzun.com.tabletmenu.utils.ShowMessage;

public class CategoryFragment extends Fragment {

    public static String TAG_CATEGORY = "com.app.sample.shop.tagCategory";

    private View view;
    private RecyclerView recyclerView;
    private ItemGridAdapter mAdapter;
    private LinearLayout lyt_notfound;
    private String category_name = "";
    ArrayList<CategoryRetrofit> category_list;
    ShowMessage message;
    CategoryRetrofit cat_object=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, null);
        category_name = getArguments().getString(TAG_CATEGORY);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        lyt_notfound = (LinearLayout) view.findViewById(R.id.lyt_notfound);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Tools.getGridSpanCount(getActivity()));
        recyclerView.setLayoutManager(mLayoutManager);

        //set data and list adapter
        List<Product> items = new ArrayList<>();
        getDataFromMainActivity();
        search_category_object();

        if(cat_object!=null)
        {
            //message.Toast(cat_object.getName());
            items = ServerData.getProductsFromServer(cat_object);
            // En başta new items açılıyor. Ona uygun kategory object yok serverda null gelicek
        }


     /*   if(category_name.equals("Clothing")){
            items = Constant.getItemClothes(getActivity());
        }else if(category_name.equals("Shoes")){
            items = Constant.getItemShoes(getActivity());

        }else if(category_name.equals(getString(R.string.menu_watches))){
            items = Constant.getItemWatches(getActivity());
        }else if(category_name.equals(getString(R.string.menu_accessories))){
            items = Constant.getItemAccessories(getActivity());
        }else if(category_name.equals(getString(R.string.menu_bags))){
            items = Constant.getItemBags(getActivity());
        }

        else if(category_name.equals("New Items")){
            items = Constant.getItemBags(getActivity());
        }*/




        mAdapter = new ItemGridAdapter(getActivity(), items);  // gelen itemleri göster(kategori içerisinde)
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Product obj, int position) {
                ActivityItemDetails.navigate((ActivityMain)getActivity(), v.findViewById(R.id.lyt_parent), obj);
            }
        });

        if(mAdapter.getItemCount()==0){
            lyt_notfound.setVisibility(View.VISIBLE);
        }else{
            lyt_notfound.setVisibility(View.GONE);
        }
        return view;
    }


    private void getDataFromMainActivity()  // Bu method fragmenta kategorileri çekti
    {
        message = new ShowMessage(getActivity());
        Intent intent = getActivity().getIntent();
        Bundle bundle = getActivity().getIntent().getExtras();
        category_list = bundle.getParcelableArrayList("data");
       // message.Toast(category_list.get(0).getProducts().get(0).getName());

    }

    private void search_category_object()
    {
        for(int i=0; i<category_list.size();i++)
        {
            if(category_name.equals(category_list.get(i).getName()))
            {
                cat_object = category_list.get(i);
                break;
            }
        }
    }


}
