package experiment.illustro.parseloginauthentication;

import android.os.Bundle;

import com.parse.ParseUser;

import java.util.ArrayList;

import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 6/30/2015.
 */
public class UserDirectory extends CustomActivity
{

    private ArrayList<ParseUser> userList;
    public static ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void userStatus(boolean status)
    {

    }

    private void getEntireUserDirectory()
    {

    }

}
