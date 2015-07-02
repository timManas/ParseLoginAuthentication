package experiment.illustro.parseloginauthentication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        String strUserName_Login = userName.getText().toString();
        String strPassword_Login = password.getText().toString();

        if(view.getId() == R.id.bRegister)
        {
            Intent registerIntent = new Intent(this, RegisterScreen.class);  // RegisterScreen
            startActivity(registerIntent);
        }
        else
        {
            System.out.println(strUserName_Login);
            System.out.println(strPassword_Login);

            if (view.getId() == R.id.bLogin)
            {
                if (strUserName_Login.length() == 0  || strPassword_Login.length() == 0)
                {
                    utilities.showDialog(this, "Enter all information");
                    return;
                } else
                {
                    progressDialog = ProgressDialog.show(this, null, "Loading ...");
                }

                ParseUser.logInInBackground(strUserName_Login, strPassword_Login, new LogInCallback()
                {
                    @Override
                    public void done(ParseUser parseUser, ParseException exception)
                    {
                        progressDialog.dismiss();

                        if (parseUser != null)
                        {
                            UserDirectory.user = parseUser;
                            Intent userListIntent = new Intent(LoginScreen.this, UserDirectory.class);
                            startActivity(userListIntent);
                            finish();
                        } else
                        {
                            utilities.showDialog(LoginScreen.this, "Error ... please try again");
                            exception.printStackTrace();
                        }
                    }
                });


            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == RESULT_OK)
        {
            finish();
        }
    }

    private void initialize()
    {
        setTouchNClick(R.id.bLogin);
        setTouchNClick(R.id.bRegister);

        userName = (EditText) findViewById(R.id.etUserNameLogin);
        password = (EditText) findViewById(R.id.etPasswordLogin);
    }
}
