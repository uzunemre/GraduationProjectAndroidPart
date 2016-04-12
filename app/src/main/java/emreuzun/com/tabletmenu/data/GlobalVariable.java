package emreuzun.com.tabletmenu.data;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

import emreuzun.com.tabletmenu.model.ItemModel;
import emreuzun.com.tabletmenu.retrofit.Product;

public class GlobalVariable extends Application {


    private List<Product> cart = new ArrayList<>();

    public void addCart(Product model) {
        cart.add(model);
    }
    public void removeCart(Product model) {
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getId()==model.getId()){
                cart.remove(i);
                break;
            }
        }
    }
    public void clearCart() {
        cart.clear();
    }
    public List<Product> getCart() {
        return cart;
    }
    public float getCartPriceTotal() {
        float total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total = total + cart.get(i).getSumPrice();
        }
        return total;
    }
    public int getCartItemTotal() {
        int total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total = total + cart.get(i).getTotal();
        }
        return total;
    }
    public int getCartItem() {
        return cart.size();
    }
    public void updateItemTotal(Product model) {
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getId()==model.getId()){
                cart.remove(i);
                cart.add(i, model);
                break;
            }
        }
    }

    public boolean isCartExist(Product model){
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getId()==model.getId()){
                return true;
            }
        }
        return false;
    }
}
