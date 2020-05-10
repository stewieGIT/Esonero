package it.massimomazzetti.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reimposta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Holder holder;
    final String TAG="555";
    String email, password;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_reimposta);

        mAuth = FirebaseAuth.getInstance();
        holder = new Holder();
    }

    class Holder implements View.OnClickListener{

        EditText etEmail;
        Button btnReimposta, btnLogin;

        public Holder(){

            etEmail = findViewById(R.id.etEmail);

            btnReimposta = findViewById(R.id.btnRegister);
            btnReimposta.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

                try {
                    reimposta(etEmail.getText().toString());
                }catch (Exception e){}

        }
    }


    public void reimposta(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task <Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
