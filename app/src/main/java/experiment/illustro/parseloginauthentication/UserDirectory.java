package experiment.illustro.parseloginauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import experiment.illustro.parseloginauthentication.Utils.SharedData;
import experiment.illustro.parseloginauthentication.Utils.utilities;
import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 6/30/2015.
 */
public class UserDirectory extends CustomActivity
{
    public ArrayList<ParseUser> userList;
    public static ParseUser user;
    private String ONLINE = "ONLINE";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_directory);
        //getActionBar().setDisplayHomeAsUpEnabled(false);

        updateList_UserStatus(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //updateList_UserStatus(true);  //Watch out this is suppose to be not here
        loadEntireList_UserStatus();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        updateList_UserStatus(false);
    }

    private void updateList_UserStatus(boolean status)
    {
        user.put(ONLINE, status);
        user.saveEventually();
    }

    private void loadEntireList_UserStatus()
    {
        progressDialog = ProgressDialog.show(this,null, "UPLOADING");

        ParseUser.getQuery().whereNotEqualTo("username", user.getUsername()).findInBackground(new FindCallback<ParseUser>()
        {
            @Override
            public void done(List<ParseUser> list, ParseException exception)
            {
                progressDialog.dismiss();
                if(list != null)  // Success
                {
                    if(list.size() == 0)
                    {
                        utilities.showDialog(UserDirectory.this, "Empty List ... Add a new user");
                    }

                    UserDirectoryAdapterX newUserDirectoryAdapter = new UserDirectoryAdapterX(UserDirectory.this);

                    userList = new ArrayList<ParseUser>(list);
                    ListView newUserList = (ListView) findViewById(R.id.userListView);
                    newUserList.setAdapter(newUserDirectoryAdapter);
                    newUserList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Intent chatItent = new Intent(UserDirectory.this, ChatScreen.class);
                            chatItent.putExtra(SharedData.sharedData, userList.get(position).getUsername());
                            startActivity(chatItent);
                        }
                    });
                }
                else
                {
                    utilities.showDialog(UserDirectory.this, "Error ... please try again");
                    exception.printStackTrace();
                }

            }
        });

    }

    //==================================================================================================================================

} // End of class
