package es.iescarrillo.android.ejemplosqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import es.iescarrillo.android.ejemplosqlite.R;
import es.iescarrillo.android.ejemplosqlite.models.Person;

public class PersonAdapter extends ArrayAdapter<Person> {

    private List<Person> people;

    public PersonAdapter(@NonNull Context context, int resource, @NonNull List<Person> people) {
        super(context, resource, people);
        this.people = people;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person p = people.get(position);
        TextView tvPerson;

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);
        }

        tvPerson = convertView.findViewById(R.id.tvPerson);
        tvPerson.setText(p.getId() + " - " + p.getName() + " " + p.getSurname());

        return convertView;
    }
}
