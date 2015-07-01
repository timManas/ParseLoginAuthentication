package experiment.illustro.parseloginauthentication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import experiment.illustro.parseloginauthentication.Utils.utilities;
import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 6/28/2015.
 */
public class LoginScreen extends CustomActivity
{

    private EditText userName;
    private EditText password;

    private ProgressDialog progressDialog;


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
            Intent registerIntent = new Intent(this, RegisterScreen.class);  // RegisterScreen
            startActivity(registerIntent);
        }
        else
        {
            if (view.getId() == R.id.bLogin)
            {

                String strUserName = userName.getText().toString();
                String strPassword = password.getText().toString();

                if (strUserName.equals(0) || strPassword.equals(0))
                {
                    utilities.showDialog(this, "Enter all information");
                    return;
                } else
                {
                    progressDialog = ProgressDialog.show(this, null, "Loading ...");
                }

                ParseUser.logInInBackground(strUserName, strPassword, new LogInCallback()
                {
                    @Override
                    public void done(ParseUser parseUser, ParseException e)
                    {
                        progressDialog.dismiss();

                        if (parseUser != null)
                        {
                            UserDirectory.user = parseUser;
                            Intent userListIntent = new Intent(LoginScreen.this, TestClass.class);
                            startActivity(userListIntent);
                            finish();
                        } else
                        {
                            utilities.showDialog(LoginScreen.this, "Error ... please try again");
                            e.printStackTrace();
                        }
                    }
                });


            }
        }

    }

    private void initialize()
    {
        setClick(R.id.bLogin);
        setClick(R.id.bRegister);

        userName = (EditText) findViewById(R.id.etUserNameLogin);
        password = (EditText) findViewById(R.id.etPasswordLogin);
    }
}
