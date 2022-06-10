package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DormActivity extends AppCompatActivity {
    private Spinner dorm_Spinner;

    private Spinner city_Spinner;

    private ArrayAdapter<Dorm> dormArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;


    private ArrayList<Dorm> dorms;

    private ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm);
        initializeUI();
    }
    private void initializeUI() {
        dorm_Spinner = (Spinner) findViewById(R.id.spinnerDorm);
        city_Spinner = (Spinner) findViewById(R.id.spinnerCity);



        dorms= new ArrayList<>();
        cities = new ArrayList<>();

        //createLists();

        dormArrayAdapter = new ArrayAdapter<Dorm>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dorms);
        dormArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dorm_Spinner.setAdapter(dormArrayAdapter);



        cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cities);
        cityArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        city_Spinner.setAdapter(cityArrayAdapter);
        dorm_Spinner.setOnItemSelectedListener(dorm_listener);
        city_Spinner.setOnItemSelectedListener(city_listener);



    }
    private AdapterView.OnItemSelectedListener city_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final City city = (City) city_Spinner.getItemAtPosition(position);
                //Log.d("SpinnerCountry", "onItemSelected: country: "+country.getCountryID());
                int cityID = city.getCityID();//seçili
                Toast.makeText(getApplicationContext(),"ID Selected"+cityID,Toast.LENGTH_SHORT).show();
                ArrayList<Dorm> tempStates = new ArrayList<>();
                /*
                * spinnerdan seçlen ilin yurdun sorguyla id'sini getirme*/

                tempStates.add(new Dorm(0, new City(0, "Şehir Seç"), "Yurt Seç"));

                for (Dorm singleDorm : dorms) {
                    if (singleDorm.getCity().getCityID() == city.getCityID()) {
                        tempStates.add(singleDorm);
                    }
                }

                dormArrayAdapter = new ArrayAdapter<Dorm>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tempStates);
                dormArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                dorm_Spinner.setAdapter(dormArrayAdapter);
            }

            cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new ArrayList<City>());
            cityArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            city_Spinner.setAdapter(cityArrayAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener dorm_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final Dorm dorm = (Dorm) dorm_Spinner.getItemAtPosition(position);
                //Log.d("SpinnerCountry", "onItemSelected: country: "+country.getCountryID());
                ArrayList<City> tempStates = new ArrayList<>();

                tempStates.add(new City(0, "Şehir Seç"));




            }

            cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, new ArrayList<City>());
            cityArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            city_Spinner.setAdapter(cityArrayAdapter);
            /*
            if (position > 0) {
                final Dorm state = (Dorm) dorm_Spinner.getItemAtPosition(position);
                //Log.d("SpinnerCountry", "onItemSelected: state: "+state.getStateID());
                ArrayList<City> tempCities = new ArrayList<>();

                Country country = new Country(0, "Choose a Country");
                State firstState = new State(0, country, "Choose a State");
                tempCities.add(new City(0, country, firstState, "Choose a City"));

                for (City singleCity : cities) {
                    if (singleCity.getState().getStateID() == state.getStateID()) {
                        tempCities.add(singleCity);
                    }
                }

                cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, tempCities);
                cityArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                city_Spinner.setAdapter(cityArrayAdapter);


            }
            */

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void createLists() {
        City city0 = new City(0, "Şehir Seç");
        City city1 = new City(1, "Ankara");
        City city2 = new City(2, "İstanbul");
        City city3 = new City(3, "İzmir");

        cities.add(new City(0, "Şehir Seç"));
        cities.add(new City(1, "Ankara"));
        cities.add(new City(2, "İstanbul"));

        Dorm dorm0 = new Dorm(0, city0, "Yurt Seç");
        Dorm dorm1 = new Dorm(1, city1, "Bahçelievler Yurdu");
        Dorm dorm2 = new Dorm(2, city2, "Beşiktaş Yurdu");
        Dorm dorm3 = new Dorm(3, city3, "Konak Yurdu");
        Dorm dorm4 = new Dorm(4, city1, "Gazi Yurdu");

        dorms.add(dorm0);
        dorms.add(dorm1);
        dorms.add(dorm2);
        dorms.add(dorm3);
        dorms.add(dorm4);



    }
}