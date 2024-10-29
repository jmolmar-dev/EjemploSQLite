package es.iescarrillo.android.ejemplosqlite.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.android.ejemplosqlite.models.Car;

@Dao
public interface CarDao {

    @Insert
    long insert(Car c);

    @Insert
    void insertCars(List<Car>cars);

    @Update
    void update(Car c);

    @Delete
    void delete(Car c);

    @Query("SELECT * FROM CAR")
    List<Car> getAll();

    @Query("SELECT * FROM CAR c WHERE c.person_id = :personId")
    List<Car> getCarByPersonId(long personId);

    @Query("SELECT * FROM CAR c WHERE c.plate = :plate")
    Car getCarByPlate(String plate);
}
