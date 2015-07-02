package experiment.illustro.parseloginauthentication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Spirit on 7/01/2015.
 */
public class UserDirectoryAdapter extends BaseAdapter
{

    private static ArrayList<ParseUser> userList;

    public UserDirectoryAdapter(ArrayList<ParseUser> list)
    {
        this.userList = list;
    }

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
            //convertView = getLayoutInflater().inflate(R.layout.user_item, null);
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