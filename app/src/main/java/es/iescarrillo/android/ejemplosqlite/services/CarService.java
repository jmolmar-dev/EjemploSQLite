package es.iescarrillo.android.ejemplosqlite.services;

import android.app.Application;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import es.iescarrillo.android.ejemplosqlite.daos.CarDao;
import es.iescarrillo.android.ejemplosqlite.database.DatabaseHelper;
import es.iescarrillo.android.ejemplosqlite.models.Car;

public class CarService implements CarDao {

    // Dao
    private CarDao carDao;

    // Variables locales
    private long resultInsert;
    private List<Car> resultGetCarByPersonId;

    // Constructor
    public CarService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application.getApplicationContext());
        carDao = db.carDao();
    }

    @Override
    public long insert(Car c) {
        Thread thread = new Thread(() -> {
            resultInsert = carDao.insert(c);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - CarService - insert", e.toString());
        }

        return  resultInsert;
    }

    @Override
    public void insertCars(List<Car> cars) {

    }

    @Override
    public void update(Car c) {

    }

    @Override
    public void delete(Car c) {

    }

    @Override
    public List<Car> getAll() {
        return Collections.emptyList();
    }

    @Override
    public List<Car> getCarByPersonId(long personId) {
        Thread thread = new Thread(() -> {
           resultGetCarByPersonId = carDao.getCarByPersonId(personId);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - CarService - getCarByPersonId", e.toString());
        }

        return resultGetCarByPersonId;
    }

    @Override
    public Car getCarByPlate(String plate) {
        return null;
    }
}
