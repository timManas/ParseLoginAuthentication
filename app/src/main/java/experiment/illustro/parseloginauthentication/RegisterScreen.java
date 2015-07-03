package experiment.illustro.parseloginauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import experiment.illustro.parseloginauthentication.Utils.utilities;
import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 6/30/2015.
 */
public class RegisterScreen extends CustomActivity
{

    private EditText userName_Register;
    private EditText password_Register;
    private EditText userEmail_Register;

    private ProgressDialog progressDialog;
    private ParseUser newParseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerscreen);
        initialize();
    }

    private void initialize()
    {
        setTouchNClick(R.id.bRegisterUser);
        userEmail_Register = (EditText) findViewById(R.id.etUserEmailRegister);
        password_Register = (EditText) findViewById(R.id.etPasswordRegister);
        userName_Register = (EditText) findViewById(R.id.etUserNameRegister);
    }

    @Override
    public void onClick(View view)
    {
        super.onClick(view);

        String strUserEmail_Register = userEmail_Register.getText().toString();
        String strUserName_Register = userName_Register.getText().toString();
        String strUserPassword_Register = password_Register.getText().toString();

        if (strUserEmail_Register.length() == 0 || strUserName_Register.length() == 0 || strUserPassword_Register.length() == 0)
        {
            utilities.showDialog(this, "Enter all information");
            return;
        } else
        {
            progressDialog = ProgressDialog.show(this, null, "Please wait...");
        }

        // This is how you create a new User...
        // No need to create a table. It is automatically done for you

        newParseUser = new ParseUser();
        newParseUser.setEmail(strUserEmail_Register);
        newParseUser.setUsername(strUserName_Register);
        newParseUser.setPassword(strUserPassword_Register);

        newParseUser.signUpInBackground(new SignUpCallback()
                {
                    @Override
                    public void done(ParseException exception)
                    {
                        progressDialog.dismiss();
                        if(exception == null)
                        {
                            UserDirectory.user = newParseUser;
                            Intent userListIntent = new Intent(RegisterScreen.this, UserDirectory.class);
                            startActivity(userListIntent);
                            setResult(RESULT_OK);
                            finish();
                        }
                        else
                        {
                            utilities.showDialog(RegisterScreen.this, "Error ... please try again");
                            exception.printStackTrace();
                        }
                    }
                }


        );


    }


}
