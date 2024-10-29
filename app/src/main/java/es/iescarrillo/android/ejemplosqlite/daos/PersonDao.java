package es.iescarrillo.android.ejemplosqlite.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import es.iescarrillo.android.ejemplosqlite.models.Person;

@Dao
public interface PersonDao {

    @Insert
    long insertPerson(Person p);

    @Insert
    void insertPeople(List<Person> people);

    @Update
    void updatePerson(Person p);

    @Delete
    void deletePerson(Person p);

    @Query("SELECT * FROM PERSON")
    List<Person> getAll();

    @Query("SELECT * FROM PERSON p WHERE p.age >= 18")
    List<Person> getAllOlder();

}
