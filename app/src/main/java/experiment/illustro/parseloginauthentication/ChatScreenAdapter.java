package experiment.illustro.parseloginauthentication;

import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import experiment.illustro.parseloginauthentication.Helper.ChatHelper;

/**
* Created by timmanas on 2015-07-07.
*/
class ChatScreenAdapter extends BaseAdapter
{


    private ChatScreen chatScreen;

    public ChatScreenAdapter(ChatScreen chatScreen)
    {
        this.chatScreen = chatScreen;
    }

    @Override
    public int getCount()
    {
        return chatScreen.conversationArrayList.size();
    }

    @Override
    public ChatHelper getItem(int position)
    {
        return chatScreen.conversationArrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        TextView dateTimeLabel;
        TextView messageLabel;
        TextView deliveryLabel;


        ChatHelper chatItemStatus = getItem(position);
        if(chatItemStatus.isMsgSent() == true)
        {
            convertView = chatScreen.getLayoutInflater().inflate(R.layout.msg_sent, null);

            dateTimeLabel = (TextView) convertView.findViewById(R.id.tvMsgSent_DateTime);
            dateTimeLabel.setText(DateUtils.getRelativeDateTimeString(chatScreen, chatItemStatus.getDateOfMsg().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0));

            messageLabel = (TextView) convertView.findViewById(R.id.tvMessageSent);
            messageLabel.setText(chatItemStatus.getMessage());

            deliveryLabel = (TextView) convertView.findViewById(R.id.tvMsgDelivery_Success);


        }
        else
        {
            convertView = chatScreen.getLayoutInflater().inflate(R.layout.msg_received, null);

            dateTimeLabel = (TextView) convertView.findViewById(R.id.tvMsgRcv_DateTime);
            dateTimeLabel.setText(DateUtils.getRelativeDateTimeString(chatScreen, chatItemStatus.getDateOfMsg().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0));

            messageLabel = (TextView) convertView.findViewById(R.id.tvMessageRcv);
            messageLabel.setText(chatItemStatus.getMessage());

            deliveryLabel = (TextView) convertView.findViewById(R.id.tvMsgReceived_Success);
        }


        if(chatItemStatus.isMsgSent() == true)
        {

            if(chatItemStatus.getCurrentStatus() == ChatHelper.currentStatusSENT)
            {
                deliveryLabel.setText("Message Delievered");
            }
            else if(chatItemStatus.getCurrentStatus() == ChatHelper.currentStatusSENDING)
            {
                deliveryLabel.setText("Message Sending..");
            }
            else
            {
                deliveryLabel.setText("Failed");
            }

        }
        else
        {
            deliveryLabel.setText("");
        }


        return convertView;
    }


}// End of ChatScreenAdapter
