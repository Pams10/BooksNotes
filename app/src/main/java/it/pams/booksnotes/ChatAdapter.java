package it.pams.booksnotes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<Chat> messages;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String currentUser;
    Context context;

    ChatAdapter(String currentUser ){
        this.currentUser=currentUser;
        this.messages= new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context=parent.getContext();
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
        TextView mIdSender, mMsg , mMailMsg;
        //LinearLayout.LayoutParams params;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mIdSender= itemView.findViewById(R.id.idSender)  ;
            mMsg= itemView.findViewById(R.id.itemMmsg) ;
            mMailMsg=itemView.findViewById(R.id.itemMailMsg) ;

           // params =(LinearLayout.LayoutParams)mIdSender.getLayoutParams();
        }

        public void bindView(Chat message) {
            mIdSender.setText(String.valueOf(message.getDateSend()));
            mMsg.setText(message.getMsg());
            mMailMsg.setText(message.getMittente());
            mMailMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + mMailMsg.getText().toString()));
                    intent.putExtra(Intent.EXTRA_SUBJECT,"BooksNotes");
                    context.startActivity(intent);
                }
            });

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
