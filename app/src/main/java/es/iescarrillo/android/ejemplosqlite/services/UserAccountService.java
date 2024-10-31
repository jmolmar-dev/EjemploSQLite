package es.iescarrillo.android.ejemplosqlite.services;

import android.app.Application;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import es.iescarrillo.android.ejemplosqlite.daos.UserAccountDao;
import es.iescarrillo.android.ejemplosqlite.database.DatabaseHelper;
import es.iescarrillo.android.ejemplosqlite.models.UserAccount;

public class UserAccountService implements UserAccountDao {

    private UserAccountDao userAccountDao;
    private long resultInsert;
    private List<UserAccount> resultGetAll;
    private UserAccount resultGetById, resultGetByUsername;

    public UserAccountService(Application application){
        // Llamamos a la instancia de la BBDD con el contexto del parámetro application
        DatabaseHelper db = DatabaseHelper.getInstance(application.getApplicationContext());
        // Inicializamos el dao
        userAccountDao = db.userAccountDao();
    }

    @Override
    public long insert(UserAccount userAccount) {
        Thread thread = new Thread(() -> {
            resultInsert = userAccountDao.insert(userAccount);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - UserAccountService - Insert", e.toString());
        }

        return resultInsert;
    }

    @Override
    public List<UserAccount> getAll() {
        Thread thread = new Thread(() -> {
            resultGetAll = userAccountDao.getAll();
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - UserAccountService - getAll", e.toString());
        }

        return resultGetAll;
    }

    @Override
    public UserAccount getUserAccountById(long id) {
        Thread thread = new Thread(() -> {
            resultGetById = userAccountDao.getUserAccountById(id);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - UserAccountService - getUserAccountById", e.toString());
        }

        return resultGetById;
    }

    @Override
    public UserAccount getUserAccountByUsername(String username) {
        Thread thread = new Thread(() -> {
            resultGetByUsername = userAccountDao.getUserAccountByUsername(username);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e){
            Log.e("Error - UserAccountService - getUserAccountByUsername", e.toString());
        }

        return resultGetByUsername;
    }
}
