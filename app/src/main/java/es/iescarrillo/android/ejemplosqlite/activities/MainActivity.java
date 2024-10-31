package es.iescarrillo.android.ejemplosqlite.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Random;

import es.iescarrillo.android.ejemplosqlite.R;
import es.iescarrillo.android.ejemplosqlite.adapters.PersonAdapter;
import es.iescarrillo.android.ejemplosqlite.models.Address;
import es.iescarrillo.android.ejemplosqlite.models.Person;
import es.iescarrillo.android.ejemplosqlite.models.UserAccount;
import es.iescarrillo.android.ejemplosqlite.services.CarService;
import es.iescarrillo.android.ejemplosqlite.services.PersonService;
import es.iescarrillo.android.ejemplosqlite.services.UserAccountService;

public class MainActivity extends AppCompatActivity {

    // Services
    private PersonService personService;
    private UserAccountService userAccountService;
    private CarService carService;

    // Compoments
    private ListView lvPeople;
    private Button btnInsert, btnCheck;
    private PersonAdapter personAdapter;
    private List<Person> personList;
    private EditText etPassword;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializamos el servicio
        personService = new PersonService(getApplication());
        userAccountService = new UserAccountService(getApplication());
        carService = new CarService(getApplication());

        // Cargamos los compoentes
        lvPeople = findViewById(R.id.lvPerson);
        btnInsert = findViewById(R.id.btnInsertPerson);
        etPassword = findViewById(R.id.etPassword);
        btnCheck = findViewById(R.id.btnCheckPassword);
        tvUsername = findViewById(R.id.tvUsername);

        // Obtener el contenedor de las variables de sesión
        SharedPreferences sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username","No te has logueado");
        long id = sharedPreferences.getLong("id", 0);
        tvUsername.setText(username + " - " + id);

        refresh();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean findOne = false;
                List<UserAccount> userAccountList = userAccountService.getAll();
                for(UserAccount u:userAccountList){
                    if(BCrypt.checkpw(etPassword.getText().toString(), u.getPassword())){
                        Toast.makeText(getApplicationContext(), "El usuario es " + u.getUsername(), Toast.LENGTH_SHORT).show();
                        findOne = true;
                        break;
                    }
                }

                if(!findOne){
                    Toast.makeText(getApplicationContext(), "No ha encontrado ninguno", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Integer randomValue = random.nextInt(100);

                // Creamos el UserAccount
                UserAccount userAccount = new UserAccount();
                userAccount.setUsername("pablo"+randomValue);

                // Encriptar la password
                // BCrypt.hashpw(parametr1 => la contraseña a encriptar, parametro2 => coste de la encriptación)
                String password = BCrypt.hashpw("Usuario" + randomValue + "?", BCrypt.gensalt(5));
                Log.i("Password encrypt", password);

                userAccount.setPassword(password);
                long userAccountId = userAccountService.insert(userAccount);


                Person p = new Person();
                p.setName("Pablo " + randomValue);
                p.setSurname("Carrillo");
                p.setAge(randomValue);
                p.setEmail("pablo"+randomValue+"@iescarrillo.es");

                Address address = new Address();
                address.setCity("Morón");
                address.setStreet("Calle Cervantes");
                address.setStreetNumber(String.valueOf(randomValue));

                p.setAddress(address);
                p.setUserAccountId(userAccountId);

                // Almacenamos en una variable el ID de la persona creada
                long idPersonSaved = personService.insertPerson(p);
                Toast.makeText(getApplicationContext(), "Persona con ID:" + idPersonSaved + " creada",
                        Toast.LENGTH_SHORT).show();
                refresh();
            }
        });

        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person p = personList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("person", p);
                startActivity(intent);
            }
        });

    }

    private void refresh(){
        // Inicializamos el adaptador
        personList = personService.getAll();
        personAdapter = new PersonAdapter(getApplicationContext(), 0, personList);
        // Modificamos el adaptador del listView
        lvPeople.setAdapter(personAdapter);
    }

}