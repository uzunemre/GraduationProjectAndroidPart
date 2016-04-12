package emreuzun.com.tabletmenu.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Emre on 12.4.2016.
 */
public class ShowMessage {

    Activity activity;

    public ShowMessage(Activity activity)
    {
        this.activity = activity;
    }

    public void Toast(String message)
    {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }
}
