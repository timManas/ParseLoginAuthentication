package experiment.illustro.parseloginauthentication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import experiment.illustro.parseloginauthentication.Helper.ChatHelper;
import experiment.illustro.parseloginauthentication.Utils.SharedData;
import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 7/02/2015.
 */
public class ChatScreen extends CustomActivity
{

    EditText userMessage;
    ArrayList<ChatHelper> conversationArrayList;
    Date LastSentMsgDate;

    //##############################

    ChatScreenAdapter chatAdptr;
    ChatHelper chConversation;
    String recipient;
    String message;
    ListView  chatList;
    boolean isAppRunning;
    private  static Handler messageQueue;
    ParseObject conversationObj;
    ParseQuery<ParseObject> parseQry;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        intialize();
    }

    private void intialize()
    {
        userMessage = (EditText) findViewById(R.id.etMessage_Input);
        userMessage.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        setTouchNClick(R.id.bMessageSend);

        conversationArrayList = new ArrayList<>();
        chatAdptr = new ChatScreenAdapter();
        chatList = (ListView) findViewById(R.id.chatListView);
        chatList.setAdapter(chatAdptr);
        chatList.setStackFromBottom(true);

        recipient = getIntent().getStringExtra(SharedData.sharedData);
        getActionBar().setTitle(recipient);

        messageQueue = new Handler();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        isAppRunning = true;
        uploadEntireConversation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isAppRunning = false;
    }

    @Override
    public void onClick(View view)
    {
        super.onClick(view);
        if(view.getId() == R.id.bMessageSend)
        {
            sendUserMessage();
        }
    }

    private void sendUserMessage()
    {
        if(userMessage.length() == 0)
        {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(userMessage.getWindowToken(),0);

        message = userMessage.getText().toString();
        chConversation = new ChatHelper(message, new Date(), UserDirectory.user.getUsername());
        chConversation.setCurrentStatus(ChatHelper.currentStatusSENDING);
        conversationArrayList.add(chConversation);
        chatAdptr.notifyDataSetChanged();
        userMessage.setText(null);

        conversationObj = new ParseObject("Chat");
        conversationObj.put("sender", UserDirectory.user.getUsername());
        conversationObj.put("recipient", recipient);
        conversationObj.put("message", message);
        conversationObj.saveEventually(new SaveCallback()
        {
            @Override
            public void done(ParseException exception)
            {
                if(exception == null)
                {
                    chConversation.setCurrentStatus(ChatHelper.currentStatusSENT);
                }
                else
                {
                    chConversation.setCurrentStatus(ChatHelper.currentStatusFAILED);
                    exception.printStackTrace();
                }
                chatAdptr.notifyDataSetChanged();
            }
        });


    }

    private void uploadEntireConversation()
    {
        parseQry = ParseQuery.getQuery("Chat");

        if(conversationArrayList.size() == 0)
        {
            ArrayList<String> messageArrayList = new ArrayList<>();
            messageArrayList.add(recipient);
            messageArrayList.add(UserDirectory.user.getUsername());

            parseQry.whereContainedIn("sender", messageArrayList);
            parseQry.whereContainedIn("recipient", messageArrayList);
        }
        else
        {
            if(LastSentMsgDate != null)
            {
                parseQry.whereGreaterThan("createdAt", LastSentMsgDate);
            }
            parseQry.whereEqualTo("sender", recipient);
            parseQry.whereEqualTo("recipient", UserDirectory.user.getUsername());
        }

        parseQry.orderByDescending("createdAt");
        parseQry.setLimit(50);
        parseQry.findInBackground(new FindCallback<ParseObject>()
        {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                if( list != null && list.size() > 0)
                {
                    for(int i = list.size() - 1; i >= 0 ; i--)
                    {
                        ParseObject msgParseObject = list.get(i);
                        ChatHelper newChatMessage = new ChatHelper(msgParseObject.getString("message"), msgParseObject.getCreatedAt(), msgParseObject.getString("sender"));
                        conversationArrayList.add(newChatMessage);

                        if(LastSentMsgDate == null || LastSentMsgDate.before(newChatMessage.getDateOfMsg()))
                        {
                            LastSentMsgDate = newChatMessage.getDateOfMsg();
                        }
                        chatAdptr.notifyDataSetChanged();

                    }
                }


                messageQueue.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(isAppRunning == true)
                        {
                            uploadEntireConversation();
                        }

                    }
                },1000);

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId() == R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    // =================================================================================================================================


    private class ChatScreenAdapter extends BaseAdapter
    {
        TextView dateTimeLabel;
        TextView messageLabel;
        TextView deliveryLabel;

        @Override
        public int getCount()
        {
            return conversationArrayList.size();
        }

        @Override
        public ChatHelper getItem(int position)
        {
            return conversationArrayList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ChatHelper chatItemStatus = getItem(position);
            if(chatItemStatus.isMsgSent() == true)
            {
                convertView = getLayoutInflater().inflate(R.layout.msg_sent, null);
            }
            else
            {
                convertView = getLayoutInflater().inflate(R.layout.msg_received, null);
            }

            dateTimeLabel = (TextView) convertView.findViewById(R.id.tvMsgRcv_DateTime);
            dateTimeLabel.setText(DateUtils.getRelativeDateTimeString(ChatScreen.this, chatItemStatus.getDateOfMsg().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0 ));

            messageLabel = (TextView) convertView.findViewById(R.id.tvMessageRcv);
            messageLabel.setText(chatItemStatus.getMessage());

            deliveryLabel = (TextView) convertView.findViewById(R.id.tvMsgDelivery_Success);

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





} // End of class
