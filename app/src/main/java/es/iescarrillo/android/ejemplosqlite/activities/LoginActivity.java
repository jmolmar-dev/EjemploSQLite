package es.iescarrillo.android.ejemplosqlite.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;

import es.iescarrillo.android.ejemplosqlite.R;
import es.iescarrillo.android.ejemplosqlite.models.UserAccount;
import es.iescarrillo.android.ejemplosqlite.services.PersonService;
import es.iescarrillo.android.ejemplosqlite.services.UserAccountService;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignUp;
    private UserAccountService userAccountService;
    private PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cargar componentes
        loadComponents();
        // Cargar services
        loadServices();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount userAccount = userAccountService
                        .getUserAccountByUsername(etUsername.getText().toString());

                if(userAccount!=null && BCrypt.checkpw(etPassword.getText().toString(), userAccount.getPassword())){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    // Creamos el contenedor de las variables de sesión
                    SharedPreferences sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);

                    // Modificamos el contenedor
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Añadimos las variables de sesión (clave, valor)
                    editor.putString("username", userAccount.getUsername());
                    editor.putLong("id", userAccount.getId());

                    // Aplicamos los cambios
                    editor.apply();

                    startActivity(intent);
                }
            }
        });

    }

    // Método para cargar los componentes de la vista
    private void loadComponents(){
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPasswordLogin);
            btnLogin = findViewById(R.id.btnLogin);
            btnSignUp = findViewById(R.id.btnSignUp);
    }

    // Método para inicializar los servicios
    private void loadServices(){
        userAccountService = new UserAccountService(getApplication());
        personService = new PersonService(getApplication());
    }
}