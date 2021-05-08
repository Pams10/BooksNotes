 package it.pams.booksnotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


 public class HomeFragment extends Fragment {

     private FirebaseFirestore db;
     private  AdapterBook adapter;
     private RecyclerView mrecyclerView;

     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);

//         db.collection("Books")
//                 .get()
//                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                     @Override
//                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                         if (task.isSuccessful()) {
//                             for (QueryDocumentSnapshot document : task.getResult()) {
//                                 Log.d("Home", document.getId() + " => " + document.getData());
//
//                             }
//                         } else {
//                             Log.w("Home", "Error getting documents.", task.getException());
//                         }
//                     }
//                 });

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