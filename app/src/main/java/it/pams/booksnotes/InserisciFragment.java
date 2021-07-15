 package it.pams.booksnotes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

 public class  InserisciFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    LinearLayout layLibro,layAppunti;
    final String mCategoria = "Appunti";
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth mAuth;
    Uri imageUri;
    String photo;

    StorageReference storageReference;
    EditText mAutore,mPrezzo,mNomeLibro, mDescrizione,mEdzione;
    ImageView mfotoLibro;
    Button mLibroFotoBtn,mLibroAddBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inserisci, container, false);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals(mCategoria)) {
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
            layLibro.setVisibility(View.GONE);
            layAppunti.setVisibility(View.VISIBLE);
            //getActivity().getActionBar().setTitle("Inserisci libro");

        }
    }

     @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         storage= FirebaseStorage.getInstance();
         storageReference = storage.getReference();
         mAuth = FirebaseAuth.getInstance();

        mAutore = (EditText)getActivity().findViewById(R.id.libroAutore)  ;
        mNomeLibro= (EditText)getActivity().findViewById(R.id.libroNome) ;
        mDescrizione = (EditText)getActivity().findViewById(R.id.libroDescrizione) ;
        mPrezzo = (EditText)getActivity().findViewById(R.id.libroPrezzo) ;
        mLibroAddBtn=(Button)getActivity().findViewById(R.id.libroAddBtn);
        mEdzione = (EditText)getActivity().findViewById(R.id.libroEdizione);
        mLibroFotoBtn= (Button)getActivity().findViewById(R.id.libroFotoBtn);
         mfotoLibro =(ImageView)getActivity().findViewById(R.id.fotoLibro);
;        mLibroFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select picture"),1);
            }
        });


            mLibroAddBtn.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {

                 final String author=mAutore.getText().toString();
                 final String name= mNomeLibro.getText().toString() ;
                 final String description=mDescrizione.getText().toString();
                 final String price=mPrezzo.getText().toString();
                 final String edition=mEdzione.getText().toString();
                 final String owner = mAuth.getCurrentUser().getEmail();
                 if((name.equals(""))||(author.equals(""))||(description.equals(""))||(edition.equals(" "))||(price.equals(""))||(owner.equals(""))) {

                     Toast.makeText(getActivity(), "Compilare tutti i campi!", Toast.LENGTH_SHORT).show();
                 }else {
                     db = FirebaseFirestore.getInstance();

                     CollectionReference ref = db.collection("Books");
                     String id = ref.document().getId();

                     Book book = new Book(name, author, description, edition, price, photo, owner, id);
                     ref.document().set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {

                             if (task.isSuccessful())

                                 Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
                             else
                                 Toast.makeText(getActivity(), "Failed  ", Toast.LENGTH_SHORT).show();
                             Log.d("LoginActivity", "reference:failure", task.getException());
                         }
                     });
                 }
             }
           });

        layLibro=(LinearLayout)getActivity().findViewById(R.id.layLibro);
        layAppunti=(LinearLayout)getActivity().findViewById(R.id.layAppunti);
        layLibro.setVisibility(View.VISIBLE);
        layAppunti.setVisibility(View.GONE);
       // getActivity().getActionBar().setTitle("Inserisci libro");
        spinner = (Spinner)getActivity().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

     @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==1&& resultCode==Activity.RESULT_OK && data !=null && data.getData()!=null){
            imageUri = data.getData();
             if( null !=imageUri)
                 mfotoLibro.setImageURI(imageUri);
             uploadPicture();
         }
     }
     private void uploadPicture() {
         final ProgressDialog pd = new ProgressDialog(getContext());
         pd.setTitle("Uploading Image..");
         pd.show();

        String randomKey = UUID.randomUUID().toString();
         StorageReference storageRef = storageReference.child("images/"+  randomKey );
       storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 pd.dismiss();
                 storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         photo = uri.toString();
                         Snackbar.make(getActivity().findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                     }
                 });

             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 pd.dismiss();
                 Toast.makeText(getActivity(),"Image Uploaded failed",Toast.LENGTH_LONG).show();
             }
         }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                 double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                 pd.setMessage("Percentage "+(int) progressPercent + "%");
             }
         });

     }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}