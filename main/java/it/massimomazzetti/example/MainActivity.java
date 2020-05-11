package it.massimomazzetti.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.annotation.NonNull;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton signInButton;
    private FirebaseAuth mAuth;
    private Holder holder;
    String email, password;
    final String TAG = "455";
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        holder = new Holder();
        mAuth = FirebaseAuth.getInstance();
    }

    class Holder implements View.OnClickListener{
        Button btnRegister, btnLogin, btnReimposta;
        EditText etEmail, etPassword;

        public Holder(){
            btnLogin = findViewById(R.id.btnLogin);
            btnRegister = findViewById(R.id.btnRegister);
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            signInButton=findViewById(R.id.btnSignIn);
            btnReimposta=findViewById(R.id.btnReimposta);
            btnReimposta.setOnClickListener(this);
            btnRegister.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
            signInButton.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnLogin){
                email = holder.etEmail.getText().toString();
                password = holder.etPassword.getText().toString();
                try {
                    login(email, password);
                }
                catch(Exception e ){

                }
            }
            if(v.getId()==R.id.btnRegister){
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
                //***********Togliere per far funzionare il torna indietro******************
                finish();
            }
            if(v.getId()== R.id.btnSignIn){
                signIn();
            }
            if(v.getId()==R.id.btnReimposta){
                Intent intent=new Intent(MainActivity.this, Reimposta.class);
                startActivity(intent);
                 //***********Togliere per far funzionare il torna indietro******************
                finish();
            }
        }

    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void signIn(){
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG,"handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()){
            GoogleSignInAccount acct=result.getSignInAccount();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "OnConnectionFailed" + connectionResult);
    }

}
