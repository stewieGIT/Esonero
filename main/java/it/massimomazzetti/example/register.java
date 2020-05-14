package it.massimomazzetti.example;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {


    private FirebaseAuth mAuth;
    Holder holder;
    final String TAG="555";
    String email, password;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();
        holder = new Holder();
    }

    class Holder implements View.OnClickListener{

        EditText etEmail, etPassword;
        Button  btnRegister;

        public Holder(){
            etPassword = findViewById(R.id.etPassword);
            etEmail = findViewById(R.id.etEmail);
            btnRegister = findViewById(R.id.btnRegister);
            btnRegister.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
                email = holder.etEmail.getText().toString();
                password = holder.etPassword.getText().toString();

                try {

                    createAccount(email, password);
              
                }
                catch (Exception e){
                    e.printStackTrace();
                }


        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Authentication Successful.", Toast.LENGTH_SHORT).show();

                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        }

    }

}
