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
import es.iescarrillo.android.ejemplosqlite.models.Car;

public class CarAdapter extends ArrayAdapter<Car> {

    private List<Car> cars;

    public CarAdapter(@NonNull Context context, int resource, @NonNull List<Car> cars) {
        super(context, resource, cars);
        this.cars = cars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Car car = cars.get(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_car,
                    parent, false);
        }

        TextView tvCar = convertView.findViewById(R.id.tvCar);
        tvCar.setText(car.getModel() + " (" + car.getPlate() + ")");

        return convertView;
    }
}
