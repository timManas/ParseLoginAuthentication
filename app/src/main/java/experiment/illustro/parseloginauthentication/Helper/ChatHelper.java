package experiment.illustro.parseloginauthentication.Helper;

import java.util.Date;

import experiment.illustro.parseloginauthentication.UserDirectory;

/**
 * Created by Spirit on 7/03/2015.
 */
public class ChatHelper
{
    public static int currentStatusSENDING = 0;
    public static int currentStatusSENT = 1;
    public static int currentStatusFAILED = 2;

    private String message;
    private int currentStatus = currentStatusSENT;
    private Date date;
    private String Sender;



    public  ChatHelper(String  userMessage, Date currentDate, String orginalSender)
    {
        this.message = userMessage;
        this.date = currentDate;
        this.Sender = orginalSender;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String newMessage)
    {
        this.message = newMessage;
    }

    public boolean isMsgSent()
    {

        return UserDirectory.user.getUsername().equals(Sender);
    }

    public Date getDateOfMsg()
    {
        return this.date;
    }

    public void setDateOfMsg(Date newDate)
    {
        this.date = newDate;
    }

    public String getSender()
    {
        return this.Sender;
    }

    public void setSender(String orginialSender)
    {
        this.Sender = orginialSender;
    }

    public int getCurrentStatus()
    {
        return this.currentStatus;
    }

    public void setCurrentStatus(int newStatus)
    {
        this.currentStatus = newStatus;
    }

}
