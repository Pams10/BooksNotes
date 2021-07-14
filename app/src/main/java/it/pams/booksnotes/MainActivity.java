package it.pams.booksnotes;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements Communication  {

    private FirebaseAuth mAuth;

    @Override
    public void contactOwner(String str) {
        recipient(str);
        Bundle bundle = new Bundle();
        bundle.putString("recipient",str);
        Fragment  fr= new MessaggiFragment();
        fr.setArguments(bundle);
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction tx= fm.beginTransaction();
        tx.replace(R.id.fragment_place,fr);
        tx.commit();
    }
    public String recipient (String str){
        Log.d("Mainactivity","click recipient main");
        return str;
    }

    private void updateUI() {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent intToLogin = new Intent(this, LoginActivity.class);
            finish();

            startActivity(intToLogin);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        //ricevere i dati dell'intent ed estrarli con il metodo getExtras()
        Bundle bundle= getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
       // setTitle(mAuth.getCurrentUser().getDisplayName());
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
            Bundle bundle = new Bundle();
            bundle.putString("recipient",null);
            fr= new MessaggiFragment();
            fr.setArguments(bundle);

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


    public void logoutClick(View view) {
        mAuth.signOut();
        updateUI();
    }
}