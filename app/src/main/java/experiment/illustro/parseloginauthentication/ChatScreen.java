package experiment.illustro.parseloginauthentication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import experiment.illustro.parseloginauthentication.custom.CustomActivity;

/**
 * Created by Spirit on 7/02/2015.
 */
public class ChatScreen extends CustomActivity
{

    EditText userMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onClick(View view)
    {
        super.onClick(view);
    }

    private void sendUserMessage()
    {

    }

    private void uploadEntireConversation()
    {

    }







}
