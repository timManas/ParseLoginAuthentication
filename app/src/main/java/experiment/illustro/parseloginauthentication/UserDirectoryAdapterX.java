package experiment.illustro.parseloginauthentication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Spirit on 7/02/2015.
 */
class UserDirectoryAdapterX extends BaseAdapter
{

    private UserDirectory userDirectory;

    public UserDirectoryAdapterX(UserDirectory userDirectory)
    {
        this.userDirectory = userDirectory;
    }

    @Override
    public int getCount()
    {
        return userDirectory.userList.size();
    }

    @Override
    public ParseUser getItem(int position)
    {
        return userDirectory.userList.get(position);
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
            convertView = userDirectory.getLayoutInflater().inflate(R.layout.user_item, null);
        }

        ParseUser user = getItem(position);
        TextView label = (TextView) convertView;
        label.setText(user.getUsername());

        int isOnline;
        if (user.getBoolean("ONLINE") == true)
        {
            isOnline = R.drawable.online;
        } else
        {
            isOnline = R.drawable.offline;
        }

        label.setCompoundDrawablesWithIntrinsicBounds(isOnline, 0, R.drawable.marker, 0);

        return convertView;

    }
} // End of Adapter
