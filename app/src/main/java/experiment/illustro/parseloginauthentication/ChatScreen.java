package experiment.illustro.parseloginauthentication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

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
        initialize();
    }

    private void initialize()
    {
        userMessage = (EditText) findViewById(R.id.etMessage_Input);
        userMessage.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        setTouchNClick(R.id.bMessageSend);

        conversationArrayList = new ArrayList<ChatHelper>();
        chatAdptr = new ChatScreenAdapter(this);

        chatList = (ListView) findViewById(R.id.chatListView);
        chatList.setAdapter(chatAdptr);
        chatList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatList.setStackFromBottom(true);

        recipient = getIntent().getStringExtra(SharedData.sharedData);
        System.out.println("The recipient is " + recipient);
        //getActionBar().setTitle(recipient);

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


} // End of class
