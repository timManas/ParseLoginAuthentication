package experiment.illustro.parseloginauthentication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 6/28/2015.
 */
public class LoginScreen extends CustomActivity
{

    private EditText userName;
    private EditText password;

    private Button Login;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        initialize();
    }


    @Override
    public void onClick(View view)
    {
        super.onClick(view);


    }

    private void initialize()
    {
        setClick(R.id.bLogin);
        setClick(R.id.bRegister);

        userName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
    }
}
