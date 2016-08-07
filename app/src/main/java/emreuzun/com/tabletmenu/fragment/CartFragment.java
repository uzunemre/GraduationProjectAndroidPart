package emreuzun.com.tabletmenu.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import emreuzun.com.tabletmenu.R;
import emreuzun.com.tabletmenu.adapter.CartListAdapter;


import emreuzun.com.tabletmenu.data.GlobalVariable;

import emreuzun.com.tabletmenu.retrofit.Product;
import emreuzun.com.tabletmenu.utils.PhoneInfo;
import emreuzun.com.tabletmenu.utils.UtilConstant;
import emreuzun.com.tabletmenu.widget.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private GlobalVariable global;
    private CartListAdapter mAdapter;
    private TextView item_total, price_total;
    private LinearLayout lyt_notfound;
    private String mac_address;
    PostOrder post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, null);


        mac_address = PhoneInfo.getWifiMacAddress(); // büyük harf ve :'lı şekilde geliyor
        mac_address = mac_address.replaceAll(":","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilConstant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        post = retrofit.create(PostOrder.class);



        global = (GlobalVariable) getActivity().getApplication();
        item_total = (TextView) view.findViewById(R.id.item_total);
        price_total = (TextView) view.findViewById(R.id.price_total);
        lyt_notfound = (LinearLayout) view.findViewById(R.id.lyt_notfound);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //set data and list adapter
        mAdapter = new CartListAdapter(getActivity(), global.getCart());
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Product obj) {
                dialogCartAction(obj, position);
            }
        });

        ((Button) view.findViewById(R.id.bt_checkout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getItemCount() != 0) {
                    //checkoutConfirmation();
                    openDialog();
                }
            }
        });

        setTotalPrice();

        if (mAdapter.getItemCount() == 0) {
            lyt_notfound.setVisibility(View.VISIBLE);
        } else {
            lyt_notfound.setVisibility(View.GONE);
        }
        return view;
    }

    private void setTotalPrice() {
       // item_total.setText(" - " + global.getCartItemTotal() + " ");
        price_total.setText(" \u20BA " + global.getCartPriceTotal());
    }

    private void dialogCartAction(final Product model, final int position) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_cart_option);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView) dialog.findViewById(R.id.title)).setText(model.getName());
        final TextView qty = (TextView) dialog.findViewById(R.id.quantity);
        qty.setText(model.getTotal() + "");
        ((ImageView) dialog.findViewById(R.id.img_decrease)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getTotal() > 1) {
                    model.setTotal(model.getTotal() - 1);
                    qty.setText(model.getTotal() + "");
                }
            }
        });
        ((ImageView) dialog.findViewById(R.id.img_increase)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setTotal(model.getTotal() + 1);
                qty.setText(model.getTotal() + "");
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.updateItemTotal(model);
                mAdapter.notifyDataSetChanged();
                setTotalPrice();
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.removeCart(model);
                mAdapter.notifyDataSetChanged();
                setTotalPrice();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

   /* private void checkoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checkout Confirmation");
        builder.setMessage("Are you sure continue to checkout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(getActivity(),global.getCart().get(0).getName()+":"+global.getCart().get(0).getTotal(),Toast.LENGTH_LONG).show();


                PosttoServer();
                global.clearCart();
                mAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }*/


    private void openDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.order_form_dialog, null);
        final EditText table_number_edit_text = (EditText)subView.findViewById(R.id.dialog_table_number);
        final EditText product_info_edit_text = (EditText)subView.findViewById(R.id.dialog_product_info);
        final EditText product_note_edit_text = (EditText)subView.findViewById(R.id.dialog_note);
        product_info_edit_text.setText(OrderDetailsforCustomer()+"\n"+"Toplam ="+OrderPrice());



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());


        builder.setView(subView);
        android.app.AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String test_table;
                test_table = table_number_edit_text.getText().toString();




               if(test_table.equals(""))
                    Snackbar.make(view, "Lütfen Masa Numarası Giriniz", Snackbar.LENGTH_SHORT).show();
                else
               {
                   int table_number;
                   table_number = Integer.parseInt(table_number_edit_text.getText().toString());
                   String customer_notes = product_note_edit_text.getText().toString();
                   PosttoServer(table_number, customer_notes);
                   global.clearCart();
                   mAdapter.notifyDataSetChanged();
               }












            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(view, "Cancel", Snackbar.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }


    // Sipariş Detayları hangi üründen kaçar tane olduğu gidecek
    private String OrderDetailsforCustomer()
    {

        String order_details="";

        for(int i =0;i<global.getCart().size();i++)
        {
            // sipariş verirken detaylar yazdır mobil tarafında
            //order_details = order_details + global.getCart().get(i).getName()+"x =  "+global.getCart().get(i).getTotal()+"\u20BA\n";
            Product product = global.getCart().get(i);

            order_details = order_details + product.getName()+" x "+product.getTotal()+" ="+product.getSumPrice()+" \u20BA\n";
        }

        return order_details;
    }

    private String OrderDetailsforServer()
    {
        String order_details="";

        for(int i =0;i<global.getCart().size();i++)
        {
            // sipariş verirken detaylar yazdır mobil tarafında
            //order_details = order_details + global.getCart().get(i).getName()+"x =  "+global.getCart().get(i).getTotal()+"\u20BA\n";
            Product product = global.getCart().get(i);

            order_details = order_details + product.getName()+" x "+product.getTotal();
        }

        return order_details;
    }


    private float OrderPrice()
    {
        float order_price=0;

        for(int i =0;i<global.getCart().size();i++)
        {
            order_price = order_price + global.getCart().get(i).getSumPrice();

        }

        return  order_price;
    }


    private void PosttoServer(int table_number, String customer_note)
    {
        Order order = new Order();

        order.setProducts(OrderDetailsforServer());
        order.setTable_number(table_number);
        order.setMac_address(mac_address);
        order.setDescription(customer_note);
        order.setPrice(Double.parseDouble(""+OrderPrice()));
        order.setDelivery(false);


        Call<String> call = post.createOrder(order);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               // Toast.makeText(getActivity(),response.body(),Toast.LENGTH_LONG).show();
                Snackbar.make(view, response.body(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                //Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                Snackbar.make(view,"Sipariş Verilemedi", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
