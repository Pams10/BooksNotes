 package it.pams.booksnotes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



 public class HomeFragment extends Fragment {

     private FirebaseFirestore db;
     private  AdapterBook adapter;
     private RecyclerView mrecyclerView;
     private EditText msearchView;


     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);

         msearchView = (EditText)getActivity().findViewById(R.id.searchView);
         msearchView.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {

                 Query query;
                 CollectionReference ref = db.collection("Books");
                 if(s.toString().isEmpty()) {
                     query= ref.orderBy("author",Query.Direction.DESCENDING);
                 }
                 else {
                     query = ref.whereEqualTo("author", s.toString()).orderBy("author", Query.Direction.DESCENDING);
                 }
                 FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                         .setQuery(query, Book.class)
                         .build();
                 adapter.updateOptions(options);
             }
         });


     }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        mrecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview)  ;
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mrecyclerView.setHasFixedSize(true);

        db=FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("Books");
        Query query = ref.orderBy("author",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter = new AdapterBook(options);
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