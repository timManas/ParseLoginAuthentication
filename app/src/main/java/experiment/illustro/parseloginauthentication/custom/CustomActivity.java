package experiment.illustro.parseloginauthentication.custom;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import experiment.illustro.parseloginauthentication.R;
import experiment.illustro.parseloginauthentication.Utils.UserTouchFX;

/**
 * Created by Spirit on 6/28/2015.
 */
public class CustomActivity extends FragmentActivity implements OnClickListener
{

    public static final UserTouchFX TOUCH = new UserTouchFX();

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
        setupActionBar();
    }

    protected void setupActionBar()
    {
        ActionBar actionBar = this.getActionBar();  // could also be just getActionBar instead of this.getActionBar

        if(actionBar == null)
        {
            return;
        }

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blu_btn,null));
        actionBar.setLogo(R.drawable.chatler_logo);


    }

    public View setTouchNClick(int id)
    {

    }

    public View setClick(int id)
    {

    }

    @Override
    public void onClick(View view)
    {

    }
}
