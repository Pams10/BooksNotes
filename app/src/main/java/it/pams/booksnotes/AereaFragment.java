package it.pams.booksnotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class AereaFragment extends Fragment {
    private FirebaseFirestore db;
    private  AdapterBookAerea adapter;
    private RecyclerView mrecyclerView;
    private Button mbtnModif;
    private FirebaseAuth mAuth;
    private TextView mName ,mEmail;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mName = (TextView)getActivity().findViewById(R.id.nameAerea);
        mEmail =(TextView)getActivity().findViewById(R.id.emailAerea);
        mName.setText(mAuth.getCurrentUser().getDisplayName());
        mEmail.setText(mAuth.getCurrentUser().getEmail());
        mbtnModif=(Button)getActivity().findViewById(R.id.btnModif);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_aerea, container, false);

        mrecyclerView = (RecyclerView)view.findViewById(R.id.recycleviewAerea)  ;
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mrecyclerView.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("Books");
        Query query = ref.whereEqualTo("owner",mAuth.getCurrentUser().getEmail()).orderBy("author",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter = new AdapterBookAerea(options);
        mrecyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}