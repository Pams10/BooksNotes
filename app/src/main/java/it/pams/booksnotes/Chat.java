package it.pams.booksnotes;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Chat {
    @Exclude
    private Long id;
    private String msg;
    private String mittente;
    private String recipient;
    @ServerTimestamp
    private Date dateSend;


    public Date getDateSend() {
        return dateSend;
    }




    public Chat() {

    }

    public Chat(String msg, String mittente,String recipient) {
        this.msg = msg;
        this.mittente = mittente;
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMsg() {
        return msg;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
}
