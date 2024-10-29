package es.iescarrillo.android.ejemplosqlite.services;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import es.iescarrillo.android.ejemplosqlite.daos.PersonDao;
import es.iescarrillo.android.ejemplosqlite.database.DatabaseHelper;
import es.iescarrillo.android.ejemplosqlite.models.Person;

public class PersonService implements PersonDao {

    // Variables globales
    private long resultInsert;
    private List<Person> resultGetAll, resultGetAllOlder;

    // DAO
    private PersonDao personDao;

    // Constructor van a recibir un objeto Application
    public PersonService(Application application){
        // Vamos a llamar a la instancia de la BBDD
        DatabaseHelper db = DatabaseHelper.getInstance(application.getApplicationContext());
        personDao = db.personDao();
    }

    // Implemented methods
    @Override
    public long insertPerson(Person p) {
        Thread thread = new Thread(() -> {
            resultInsert = personDao.insertPerson(p);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - insertPerson", e.toString());
        }

        return resultInsert;
    }

    @Override
    public void insertPeople(List<Person> people) {
        Thread thread = new Thread(() -> {
            personDao.insertPeople(people);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - insertPeople", e.toString());
        }
    }

    @Override
    public void updatePerson(Person p) {
        Thread thread = new Thread(() -> {
            personDao.updatePerson(p);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - updatePerson", e.toString());
        }
    }

    @Override
    public void deletePerson(Person p) {
        Thread thread = new Thread(() -> {
            personDao.deletePerson(p);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - deletePerson", e.toString());
        }
    }

    @Override
    public List<Person> getAll() {
        Thread thread = new Thread(() -> {
            resultGetAll = personDao.getAll().isEmpty() ? new ArrayList<>() : personDao.getAll();
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - getAll", e.toString());
        }

        return resultGetAll.stream().sorted((o1, o2) -> o1.getAge().compareTo(o2.getAge())).collect(Collectors.toList());
    }

    @Override
    public List<Person> getAllOlder() {
        Thread thread = new Thread(() -> {
            resultGetAllOlder = personDao.getAllOlder().isEmpty() ? new ArrayList<>() : personDao.getAllOlder();
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - PersonService - getAllOlder", e.toString());
        }

        return resultGetAllOlder;
    }
}
