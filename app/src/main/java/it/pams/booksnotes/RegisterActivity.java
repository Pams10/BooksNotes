package it.pams.booksnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mEmail;
    EditText mPassword;
    EditText mNome;
    Exception  exception;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this,"già loggato",Toast.LENGTH_SHORT).show();
        //updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register );
        initUI();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initUI() {
        mNome = (EditText)findViewById(R.id.regNome);
        mEmail = (EditText)findViewById(R.id.regEmail);
        mPassword= (EditText)findViewById(R.id.regPassword);
    }


    public void registratiBtnClick(View view) {
        Log.d("RegisterActivity","Button Registrati clicked");
        String nome= mNome.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if((validEmail(email))&&(validPassword(password))&&(validName(nome)))
            createFirebaseUser(nome,email,password);
        else if(!validEmail(email))
            Toast.makeText(this,"Email non valida!" ,Toast.LENGTH_LONG).show();
        else if(!validName(nome))
            Toast.makeText(this,"Nome deve avere almeno 4 caratteri!" ,Toast.LENGTH_LONG).show();
        else if(!validPassword(password))
            Toast.makeText(this,"Password deve avere almeno 7 caratteri!" ,Toast.LENGTH_LONG).show();
    }

    private void createFirebaseUser(String nome, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Toast.makeText(RegisterActivity.this, "succes!", Toast.LENGTH_SHORT).show();

                     setNome(nome);
                    showDialog("Registrazione avvenuta con successo","Successo",android.R.drawable.ic_popup_reminder);
                }
                else
                     exception = task.getException();
                    Log.d("RegisterActivity",exception.toString());
                    //chiamare l'alert dialog
                showDialog("Un errore durante la registrazione","Errore",android.R.drawable.ic_dialog_alert);
                   // Toast.makeText(RegisterActivity.this, "failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setNome(String nome){
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();
        user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                   Log.i("SetNome" ,"success") ;
                else
                    Log.i("SetNome" ,"fail") ;
            }
        });

    }
    public void tvLoginClick(View view) {
        Log.d("RegisterActivity","Login clicked");
        Intent intent2 = new Intent(this,LoginActivity.class);
        startActivity(intent2);

    }
    //Verification dati user
    private boolean validEmail(String email){
            return (email.contains("@"));
    }
    private boolean validPassword(String password){
        return (password.length()>6);
    }
    private boolean validName(String nome) {
        return (nome.length() > 3);
    }
    //creare un alert dialog da mostrare in caso di registration failed
    private void showDialog(String message ,String title,int icon){
        new AlertDialog.Builder(this)
                .setTitle(title)
                 .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(icon)
                .show();
    }
}