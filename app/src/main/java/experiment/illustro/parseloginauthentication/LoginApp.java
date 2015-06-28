package experiment.illustro.parseloginauthentication;

import android.app.Application;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseObject;


public class LoginApp extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "c9e3UP6NBSIX1uHHlX5rmm98O9YqQVZHWQ7flRbS", "8bA7AAQf92aArPvkIEZ2qiFUOIvoBto8HF1FkJeZ");

    }


}
