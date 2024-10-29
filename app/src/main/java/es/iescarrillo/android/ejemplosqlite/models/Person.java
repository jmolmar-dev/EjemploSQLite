package es.iescarrillo.android.ejemplosqlite.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// La anotación @Entity sirve para transformar a clase Java en una entidad de la BBDD
@Entity(tableName = "PERSON")
public class Person  implements Serializable {

    // La anotación PrimaryKey autogenerate permite indicar que es la clave primaria y se va autogenerar
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    // La anotación ColumnInfo-name me permite indicar el nombre de la columna
    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "surname")
    private String surname;

    @ColumnInfo(name = "age")
    private Integer age;

    @ColumnInfo(name = "email")
    private String email;

    @Embedded
    private Address address;

    public Person(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}
