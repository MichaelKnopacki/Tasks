package com.example.tasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasks.R;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        // Mapeando os elementos
        this.mViewHolder.editEmail = findViewById( R.id.edit_email );
        this.mViewHolder.editPassword = findViewById( R.id.edit_password );
        this.mViewHolder.buttonLogin = findViewById( R.id.button_login );
        this.mViewHolder.textRegister = findViewById( R.id.text_register );

        // Evento de clique
        this.setListeners();

        // Incializa as variáveis
        this.mLoginViewModel = new ViewModelProvider( this ).get( LoginViewModel.class );

        // Cria observadores
        this.loadObservers();

        //Verifica se o usuário está logado
        this.verifyUSerLogged();
    }

    private void verifyUSerLogged() {
        this.mLoginViewModel.verifyUserLogged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_login) {

            String email = this.mViewHolder.editEmail.getText().toString();
            String password = this.mViewHolder.editPassword.getText().toString();

            this.mLoginViewModel.login( email, password );
        } else if (id == R.id.text_register) {
            startActivity( new Intent( getApplicationContext(), RegisterActivity.class ) );
        }
    }

    private void setListeners() {
        this.mViewHolder.buttonLogin.setOnClickListener( this );
        this.mViewHolder.textRegister.setOnClickListener( this );
    }

    //Responde para viewModel
    private void loadObservers() {
        this.mLoginViewModel.login.observe( this, new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {
                if (feedback.isSuccess()) {
                    startMain();
                } else {
                    Toast.makeText( getApplicationContext(), feedback.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        // Ouvindo a resposta da verificação se está logado
        this.mLoginViewModel.userLogged.observe( this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean logged) {
                if (logged) { //Se logado vai para MainActivity
                    startMain();
                }
            }
        } );
    }

    private void startMain() {
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        startActivity( intent );
        finish();
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        EditText editEmail;
        EditText editPassword;
        Button buttonLogin;
        TextView textRegister;

    }

}