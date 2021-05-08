package it.pams.booksnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<Chat> messages;
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a,dd-MM-yy");
    String currentUser;
    ChatAdapter(String currentUser ){
        this.currentUser=currentUser;
        this.messages= new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_message,parent,false);
        return new ChatViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bindView(messages.get(position));
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView mIdSender, mMsg;
        //LinearLayout.LayoutParams params;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mIdSender= itemView.findViewById(R.id.idSender)  ;
            mMsg= itemView.findViewById(R.id.itemMmsg) ;
           // params =(LinearLayout.LayoutParams)mIdSender.getLayoutParams();
        }

        public void bindView(Chat message) {
            mIdSender.setText(message.getMittente());
            mMsg.setText(message.getMsg());
        }
    }
    public void setData(List<Chat> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
    /*public void bindView(Chat message){
        mIdSender.setText(message.getMittente());
        mMsg.setText(message.getMsg());
        if(message.getDateSend()!=null)
            mDatasend.setText(format.format(message.getDateSend()));
        else
            mDatasend.setText(format.format(new Date()));*/


}
