package es.iescarrillo.android.ejemplosqlite.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.iescarrillo.android.ejemplosqlite.daos.CarDao;
import es.iescarrillo.android.ejemplosqlite.daos.PersonDao;
import es.iescarrillo.android.ejemplosqlite.daos.UserAccountDao;
import es.iescarrillo.android.ejemplosqlite.models.Car;
import es.iescarrillo.android.ejemplosqlite.models.Person;
import es.iescarrillo.android.ejemplosqlite.models.UserAccount;

@Database(entities = {Person.class, Car.class, UserAccount.class}, version = 6)
public abstract class DatabaseHelper extends RoomDatabase{

    // Añadir los DAOs
    public abstract PersonDao personDao();
    public abstract CarDao carDao();
    public abstract UserAccountDao userAccountDao();

    // Singleton => variable estática y única en tod_o el proyecto
    // en nuestro caso va a ser la instancia de la BBDD
    private static DatabaseHelper instance;

    // Synchronized es para que solo un hilo pueda modificar un objeto al mismo tiempo
    public static synchronized DatabaseHelper getInstance(Context context){

        // Si aún no se ha creado la base de datos la creamos
        if(instance == null){
            instance = Room.databaseBuilder(context, DatabaseHelper.class, "ContactBook")
                    .fallbackToDestructiveMigration().build();
        }

        return instance;
    }


}
