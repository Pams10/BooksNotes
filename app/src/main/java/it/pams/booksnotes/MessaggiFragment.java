package it.pams.booksnotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class MessaggiFragment extends Fragment  {
    private FirebaseFirestore db;
    private ChatAdapter adapter;
    private RecyclerView mrecyclerView;
    private FirebaseAuth mAuth;
    private String str;
    Button mSendMsg;
    EditText mMessaggio;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser =mAuth.getCurrentUser();
        if(currentUser == null){
            Log.d("MessageFragment","Nessun loggato");
            Intent sign = new Intent(getContext(),LoginActivity.class);
            startActivity(sign);
        }else{
            String displayName = currentUser.getDisplayName();
             fetchMsg();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         str = this.getArguments().getString("recipient");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_messaggi, container, false);
        mrecyclerView = (RecyclerView)view.findViewById(R.id.recyclerviewMsg)  ;
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void fetchMsg() {
        Log.d("messageFragment","fetch");
         adapter = new ChatAdapter( mAuth.getCurrentUser().getEmail());
         mrecyclerView.setAdapter(adapter);
        db.collection("Messages").addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error !=null)
                            Log.w("Message", error.getMessage(), error);
                        else{
                            List<Chat> messages= value.toObjects(Chat.class);
                            adapter.setData(messages);

                        }

                    }
                });

    }

    private void initUI(){
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mMessaggio =(EditText)getActivity().findViewById(R.id.message);
        mSendMsg = (Button)getActivity().findViewById(R.id.sendmsg);
        mMessaggio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMsg();
                return true;
            }
        });
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendMsg();
            }
        });
    }

    private void sendMsg() {
        String messaggio = mMessaggio.getText().toString();
        if((!messaggio.equals(null)) && (!str.equals(null))){
            Chat chat = new Chat(messaggio, mAuth.getCurrentUser().getDisplayName(),str);
            CollectionReference ref = db.collection("Messages");
            ref.document().set(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
                        mMessaggio.setText("");
                    }
                    else
                        Toast.makeText(getActivity(),"Failed  ",Toast.LENGTH_SHORT).show();
                    Log.d("MessagiFragment", "reference:failure", task.getException());

                }
            });
        }
    }
    public void recip(String string){
        String messaggio = mMessaggio.getText().toString();
        if(!messaggio.equals(null)){
            Chat chat = new Chat(messaggio, mAuth.getCurrentUser().getDisplayName(),string);
            CollectionReference ref = db.collection("Messages");
            ref.document().set(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
                        mMessaggio.setText("");
                    }
                    else
                        Toast.makeText(getActivity(),"Failed  ",Toast.LENGTH_SHORT).show();
                    Log.d("MessagiFragment", "reference:failure", task.getException());

                }
            });
        }

    }

}