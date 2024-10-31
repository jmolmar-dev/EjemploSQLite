package es.iescarrillo.android.ejemplosqlite.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.iescarrillo.android.ejemplosqlite.models.UserAccount;

@Dao
public interface UserAccountDao {

    @Insert
    long insert(UserAccount userAccount);

    @Query("SELECT * FROM USER_ACCOUNT")
    List<UserAccount> getAll();

    @Query("SELECT * FROM USER_ACCOUNT WHERE id = :id")
    UserAccount getUserAccountById(long id);

    @Query("SELECT * FROM USER_ACCOUNT WHERE username = :username")
    UserAccount getUserAccountByUsername(String username);
}
