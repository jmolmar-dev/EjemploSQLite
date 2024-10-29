package es.iescarrillo.android.ejemplosqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import es.iescarrillo.android.ejemplosqlite.R;
import es.iescarrillo.android.ejemplosqlite.adapters.CarAdapter;
import es.iescarrillo.android.ejemplosqlite.models.Car;
import es.iescarrillo.android.ejemplosqlite.models.Person;
import es.iescarrillo.android.ejemplosqlite.services.CarService;

public class DetailActivity extends AppCompatActivity {

    // Services
    private CarService carService;
    private List<Car> cars;
    private ListView lvCars;
    private CarAdapter carAdapter;
    private TextView tvPersonDetail, tvAddressDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Person p = (Person) intent.getSerializableExtra("person");

        carService = new CarService(getApplication());
        cars = carService.getCarByPersonId(p.getId());
        carAdapter = new CarAdapter(getApplicationContext(), 0, cars);
        lvCars = findViewById(R.id.lvCars);
        lvCars.setAdapter(carAdapter);

        tvPersonDetail = findViewById(R.id.tvPersonDetail);
        tvPersonDetail.setText(p.getId() + "-" + p.getName() + " " + p.getSurname());

        tvAddressDetail = findViewById(R.id.tvAddressDetail);
        tvAddressDetail.setText(p.getAddress().toString());

    }
}