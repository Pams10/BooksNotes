package it.pams.booksnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
      //(istanziare )definisco a livello globale le variabili e costanti
    EditText mEmail;
    EditText mPassword;
    private FirebaseAuth mAuth;

    // alla creazione dell'activity viene inserito il layout activity_login e inizializzare
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //Se l'utente è loggato open activity main
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String email = user.getEmail();
            Intent intent3 = new Intent(this, MainActivity.class);

            // passare i dati con chiave msg da login a main con putExtra
            intent3.putExtra("msg", email);
            startActivity(intent3);
        }
    }
    //Se la TEXTVIEW tvReg viene cliccato allora con Intent vengo indirizzato nell'activity register
    public void tvRegistratiClick(View view) {
        Log.d("LoginActivity","Registrati click");
        Intent intent1 = new Intent(this,RegisterActivity.class);
        startActivity(intent1);
    }
    //Se il buttone di login viene cliccato allora con Intent vengo indirizzato nell'activity
    public void loginBtnClick(View view) {
        Log.d("LoginActivity","Login button clicked");
        //per collegare variabili ai widgets
        mEmail= (EditText)findViewById(R.id.loginEmail);
        mPassword= (EditText)findViewById(R.id.loginPassword);
        String email=  mEmail.getText().toString();
        String password=  mPassword.getText().toString();
        Log.d("LoginActivity",email);
        Log.d("LoginActivity",password);

         loginUser(email,password);
            Intent intent3 = new Intent(this, MainActivity.class);

            // passare i dati da login a main con putExtra
            intent3.putExtra("msg", "Hello TESORO!");
            startActivity(intent3);

    }
    // dalla documentazione di firebase per verificare  che un utente è stato registrato e è presente nel database
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "signInWithEmailAndPassword:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, user.getDisplayName(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}