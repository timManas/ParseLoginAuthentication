package experiment.illustro.parseloginauthentication.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Spirit on 6/30/2015.
 */
public class utilities
{

    public static AlertDialog showDialog(Context ctx, String msg, String btn1, String btn2, DialogInterface.OnClickListener listener1, DialogInterface.OnClickListener listener2)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        // builder.setTitle(R.string.app_name);
        builder.setMessage(msg).setCancelable(false).setPositiveButton(btn1, listener1);
        if (btn2 != null && listener2 != null)
            builder.setNegativeButton(btn2, listener2);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;

    }


    public static AlertDialog showDialog(Context ctx, String msg, DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, msg, ctx.getString(android.R.string.ok), null, listener, null);
    }


    public static AlertDialog showDialog(Context ctx, String msg)
    {

        return showDialog(ctx, msg, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                dialog.dismiss();
            }
        });

    }

    public static AlertDialog showDialog(Context ctx, int msg)
    {

        return showDialog(ctx, ctx.getString(msg));

    }


}
