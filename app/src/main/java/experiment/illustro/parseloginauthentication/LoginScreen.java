package experiment.illustro.parseloginauthentication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import experiment.illustro.parseloginauthentication.Utils.utilities;
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

        if(view.getId() == R.id.bRegister)
        {
            Intent registerIntent = new Intent(this, RegisterScreen.class);
            startActivity(registerIntent);
        }
        else if(view.getId() == R.id.bLogin)
        {

            String strUserName = userName.getText().toString();
            String strPassword = password.getText().toString();

            if(strUserName.equals(null) || strPassword.equals(null))
            {
                utilities.showDialog(this, "Enter all information");
                return;
            }



        }

    }

    private void initialize()
    {
        setClick(R.id.bLogin);
        setClick(R.id.bRegister);

        userName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
    }
}
