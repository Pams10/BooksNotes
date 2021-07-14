package it.pams.booksnotes;
import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.textfield.TextInputEditText;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    EditText mNome,mEmail,mPassword, mConfirmPassword;
    private static String ERR1 = "I campi sono obbligatori";
    private static String ERR2 = "Credenziali errati";
    private FirebaseAuth mAuth;

    public void onStart() {
        super.onStart();
        //verifica se l 'utente corrente è gia logato and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initUI();

    }

    @SuppressLint("ResourceType")
    private void initUI() {
        mNome = (EditText)findViewById(R.id.regNome);
        mEmail = (EditText)findViewById(R.id.regEmail);
        mPassword= (EditText)findViewById(R.id.regPassword);
        mConfirmPassword = (EditText)findViewById(R.id.confPassword);;
    }

    // creazione nuovo utente
    public void createFirebaseUser(String email, final String password, final String nome){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("BookNotes", "createUserWithEmail: SUCCESS");
                            //Carico il nome in firebase
                            saveUserInfo(nome);
                            showSuccessDialog("Registrazione effetuata con successo , premi 'OK' per proseguire ");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("BookNotes", "createUserWithEmail: FAILURE", task.getException());
                            showErrDialog(" Registrazione andata male ");
                        }
                    }

                });

    }

    //  salvo il nome inserito nella registrazione nelle sharepreferences

    // faccio l'update del nome utente

    private void saveUserInfo(String nome) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest setProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();
        user.updateProfile(setProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("setNome", "success");
                } else {
                    Log.i("setNome", "error");
                }
            }
        });
    }


    public void tvLoginClick(View view){
        Intent sendtologinActivity = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(sendtologinActivity);

    }
    public void  registratiBtnClick(View view){

        String nome = mNome.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString().trim();
        String confermaPass = mConfirmPassword.getText().toString().trim();


        // Validità dell'input

        if((nome.length()<1) && (email.length()<2) &&(password.length()<2) && (confermaPass.length()<2)){
            Toast.makeText(RegisterActivity.this,ERR1,Toast.LENGTH_SHORT).show();
        }else if(!email.contains("@")){
            Toast.makeText(RegisterActivity.this,"mail errata",Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confermaPass) && password.length()<8){
            Toast.makeText(RegisterActivity.this," password non coincidenti",Toast.LENGTH_SHORT).show();
        }else{
            // Registrazione con successo mostro la finestra di dialogo e invio nella loginActivity
            createFirebaseUser(email,password,nome);
        }
    }


    private void showSuccessDialog(String successMessage) {

        // costruzione della finestra di dialogo
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("SUCCESS");
        dialog.setMessage(successMessage);
        dialog.setIcon(R.drawable.success);
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent sendtoLoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(sendtoLoginActivity);
            }
        });
        dialog.show();
       // finish();

    }
    private void showErrDialog(String messageError){
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Error");
        dialog.setMessage(messageError);
        dialog.setIcon(R.drawable.ic_wrong);
        dialog.setNegativeButton("retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent sendtoRegistter = new Intent(RegisterActivity.this,RegisterActivity.class);
                startActivity(sendtoRegistter);
            }
        });
        dialog.show();
    }
}