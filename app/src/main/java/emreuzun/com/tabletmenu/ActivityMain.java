package emreuzun.com.tabletmenu;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import emreuzun.com.tabletmenu.data.GlobalVariable;
import emreuzun.com.tabletmenu.data.Tools;
import emreuzun.com.tabletmenu.fragment.CartFragment;
import emreuzun.com.tabletmenu.fragment.CategoryFragment;
import emreuzun.com.tabletmenu.retrofit.CategoryRetrofit;
import emreuzun.com.tabletmenu.utils.ShowMessage;


public class ActivityMain extends AppCompatActivity {


    ArrayList<CategoryRetrofit> category_list; //Yeni Eklenen Kodlar
    ShowMessage message; // Yeni Eklenen Kodlar

    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private Menu menu;
    private View parent_view;
    private GlobalVariable global;
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromDownload();
        parent_view = findViewById(R.id.main_content);
        global = (GlobalVariable) getApplication();
        initToolbar();
        setupDrawerLayout();

        // display first page
        displayView(R.id.nav_new, getString(R.string.menu_new));
         actionBar.setTitle(R.string.menu_new);

        // for system bar in lollipop
        Tools.systemBarLolipop(this);
    }


    private void getDataFromDownload()
    {
        Bundle bundle = getIntent().getExtras();
        category_list = bundle.getParcelableArrayList("data");
        message = new ShowMessage(this);
        message.Toast(category_list.get(0).getProducts().get(0).getName());
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    protected void onResume() {
        updateChartCounter(nav_view, R.id.nav_cart, global.getCartItem());
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerLayout() {



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.navigation_view);

        final Menu menu = nav_view.getMenu(); //Menüyü elde edip kategorileri ekliyoruz
        for(int i=0;i<category_list.size();i++)
        {
            menu.add(category_list.get(i).getName()); //Serverdan kategorileri çektik ekledik

        }
        menu.add("Clothing");
        menu.add("Shoes");




        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                updateChartCounter(nav_view, R.id.nav_cart, global.getCartItem());
                super.onDrawerOpened(drawerView);
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);
        updateChartCounter(nav_view, R.id.nav_cart, global.getCartItem());

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                drawerLayout.closeDrawers();
                actionBar.setTitle(menuItem.getTitle());
                int a= menuItem.getItemId();
                Toast.makeText(getApplicationContext(),""+a,Toast.LENGTH_LONG).show();
                if(a==0)
                {
                    displayView(a, menuItem.getTitle().toString());
                }
                else
                {
                    menuItem.setChecked(true);
                    displayView(menuItem.getItemId(), menuItem.getTitle().toString());
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_cart:
                displayView(R.id.nav_cart, getString(R.string.menu_cart));
                actionBar.setTitle(R.string.menu_cart);
                break;
            case R.id.action_credit:
                Snackbar.make(parent_view, "Credit Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Snackbar.make(parent_view, "Setting Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_about: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About");
                builder.setMessage(getString(R.string.about_text));
                builder.setNeutralButton("OK", null);
                builder.show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }



    private void displayView(int id, String title) {
        Log.d("Test",title);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        Fragment fragment = null;
        Bundle bundle = new Bundle();


        switch (id) {

            case 0:
                fragment = new CategoryFragment();
                bundle.putString(CategoryFragment.TAG_CATEGORY, title);
                break;

            case R.id.nav_cart:
                fragment = new CartFragment();
                break;
            case R.id.nav_new:
                fragment = new CategoryFragment();
                bundle.putString(CategoryFragment.TAG_CATEGORY, title);
                break;
            case R.id.nav_promo:
                fragment = new CategoryFragment();
                bundle.putString(CategoryFragment.TAG_CATEGORY, title);
                break;

            default:
                break;
        }

        fragment.setArguments(bundle);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
            //initToolbar();
        }
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private void updateChartCounter(NavigationView nav, @IdRes int itemId, int count) {
        TextView view = (TextView) nav.getMenu().findItem(itemId).getActionView().findViewById(R.id.counter);
        view.setText(String.valueOf(count));
    }




}

