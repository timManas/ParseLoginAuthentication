package experiment.illustro.parseloginauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    private ArrayList<ParseUser> userList;
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
        updateList_UserStatus(true);  //Watch out this is suppose to be not here
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

                    UserDirectoryAdapter newUserDirectoryAdapter = new UserDirectoryAdapter(userList);
                    //UserDirectoryAdapterX newUserDirectoryAdapter = new UserDirectoryAdapterX();

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

    public class UserDirectoryAdapterX extends BaseAdapter
    {


        @Override
        public int getCount()
        {
            return userList.size();
        }

        @Override
        public ParseUser getItem(int position)
        {
            return userList.get(position);
        }

        @Override
        public long getItemId(int item)
        {
            return item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.user_item, null);
            }

            ParseUser user = getItem(position);
            TextView label = (TextView) convertView;
            label.setText(user.getUsername());

            int isOnline;
            if( user.getBoolean("ONLINE") == true)
            {
                isOnline = R.drawable.online;
            }
            else
            {
                isOnline = R.drawable.offline;
            }

            label.setCompoundDrawablesWithIntrinsicBounds(isOnline, 0 , R.drawable.marker,0);

            return convertView;

        }
    } // End of Adapter

}
