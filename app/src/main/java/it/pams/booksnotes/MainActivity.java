package it.pams.booksnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //ricevere i dati dell'intent ed estrarli con il metodo getExtras()
        Bundle bundle= getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        setTitle(mAuth.getCurrentUser().getDisplayName());
        //String extra = bundle.getString("msg");
        //presentiamo all 'utente i dati recuperati
        // Toast.makeText(this,extra,Toast.LENGTH_LONG).show();
         //gestire e fare la trasaction del fragment
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction tx= fm.beginTransaction();
        //agguingere il fragmento
        HomeFragment homeFragment = new HomeFragment();
        tx.add(R.id.fragment_place,homeFragment);
        tx.commit();
    }

    public void selezionaFrag(View view) {
        Fragment fr;
        if(view == findViewById(R.id.ivInserisci)){
            Log.d("selezionaFrag","click su inserisci");
            //creare il fragment inserisci
            fr= new InserisciFragment();

            
        }else if(view == findViewById(R.id.ivAerea)){
            Log.d("selezionaFrag","click su aerea");
            fr = new AereaFragment();
            
        }else if(view == findViewById(R.id.ivMessagi)) {
            Log.d("selezionaFrag", "click su messagi");
            fr= new MessaggiFragment();
        }else{
            Log.d("selezionaFrag", "click su home");
            fr = new HomeFragment();
        }
        //GESTIRE E RIMPIAZZARE FRAGMENT
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction tx= fm.beginTransaction();
        tx.replace(R.id.fragment_place,fr);
        tx.commit();
    }


}