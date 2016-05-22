package emreuzun.com.tabletmenu.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

/**
 * Created by Emre on 12.4.2016.
 */
public class ShowMessage {

    Activity activity;
    ProgressDialog progress;

    public ShowMessage(Activity activity)
    {
        this.activity = activity;
    }

    public void Toast(String message)
    {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(String message)
    {

        progress = ProgressDialog.show(activity, "Bağlanılıyor",
                message, true);
    }


    public void stopProgressDialog()
    {

        progress.dismiss();
    }

}
