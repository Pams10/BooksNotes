package it.pams.booksnotes;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Chat {
    @Exclude
    private Long id;
    private String msg;
    private String mittente;
    @ServerTimestamp
    private Date dateSend;


    public Date getDateSend() {
        return dateSend;
    }




    public Chat() {

    }

    public Chat(String msg, String mittente) {
        this.msg = msg;
        this.mittente = mittente;
    }

    public String getMsg() {
        return msg;
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
