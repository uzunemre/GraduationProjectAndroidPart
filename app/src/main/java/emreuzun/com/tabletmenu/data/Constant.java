package emreuzun.com.tabletmenu.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import emreuzun.com.tabletmenu.R;
import emreuzun.com.tabletmenu.model.ItemModel;
import emreuzun.com.tabletmenu.retrofit.CategoryRetrofit;

public class Constant {







    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }

    private static Random rnd = new Random();




    private static List<Integer> mixImg(TypedArray f_arr, TypedArray s_arr) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < f_arr.length(); i++) {
            data.add(f_arr.getResourceId(i, -1));
        }
        for (int i = 0; i < s_arr.length(); i++) {
            data.add(s_arr.getResourceId(i, -1));
        }
        return data;
    }
    private static List<String> mixStr(String[] f_str, String[] s_str) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < f_str.length; i++) {
            data.add(f_str[i]);
        }
        for (int i = 0; i < s_str.length; i++) {
            data.add(s_str[i]);
        }
        return data;
    }

    private static int getRandomIndex(Random r, int min, int max) {
        return r.nextInt(max - min) + min;
    }
    private static long getRandomLikes(){
        return getRandomIndex(rnd, 10, 250);
    }
    public static String getRandomSales(){
        return getRandomIndex(rnd, 2, 1000) +" Satış";
    }
    public static String getRandomReviews(){
        return getRandomIndex(rnd, 0, 100)+" Yorum";
    }
}
