package es.iescarrillo.android.ejemplosqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Random;

import es.iescarrillo.android.ejemplosqlite.R;
import es.iescarrillo.android.ejemplosqlite.adapters.PersonAdapter;
import es.iescarrillo.android.ejemplosqlite.models.Address;
import es.iescarrillo.android.ejemplosqlite.models.Person;
import es.iescarrillo.android.ejemplosqlite.services.PersonService;

public class MainActivity extends AppCompatActivity {

    // Services
    private PersonService personService;

    // Compoments
    private ListView lvPeople;
    private Button btnInsert;
    private PersonAdapter personAdapter;
    private List<Person> personList;

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

        // Cargamos los compoentes
        lvPeople = findViewById(R.id.lvPerson);
        btnInsert = findViewById(R.id.btnInsertPerson);

        refresh();


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Integer randomValue = random.nextInt(100);
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