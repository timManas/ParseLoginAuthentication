package experiment.illustro.parseloginauthentication.Utils;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by Spirit on 6/28/2015.
 */
public class UserTouchFX implements OnTouchListener
{

    private int ALPHA = 150;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            Drawable drawable = view.getBackground();
            drawable.mutate();
            drawable.setAlpha(ALPHA);
            view.setBackground(drawable);
        }



        return false;
    }
}
