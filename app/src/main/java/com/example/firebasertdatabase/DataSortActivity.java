package com.example.firebasertdatabase;

import static com.example.firebasertdatabase.FBref.refStudents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataSortActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * @param menu The options menu in which you place your items.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item The menu item that was selected.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.enter) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.view) {
            Intent intent = new Intent(this, DataActivity.class);
            startActivity(intent);
        }
        if (id == R.id.sort) {
            Intent intent = new Intent(this, DataSortActivity.class);
            startActivity(intent);
        }
        if (id == R.id.credits) {
            Intent intent = new Intent(this, Credits.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<String> stuList = new ArrayList<>();
    ArrayList<Student> stuValues = new ArrayList<>();

    ValueEventListener stuListener;

    CustomAdapter adp;

    ListView list;

    Spinner spinner, spinner1, spinner2;

    String[] options = {"Filter by...", "Class", "Grade", "Vaccinated (Grade Order)", "Cannot Vaccinate"};
    String[] grade = {"Grade...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] cls = {"Class...", "1", "2", "3", "4", "5"};

    Query query;

    int filterType;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sort);
        list = findViewById(R.id.list2);
        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner2);
        spinner2 = findViewById(R.id.spinner3);
        stuListener = new ValueEventListener() {
            /**
             * @param dS The current data at the location
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                stuList.clear();
                stuValues.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = data.getKey();
                    Student stuTemp = data.getValue(Student.class);
                    stuValues.add(stuTemp);
                    assert stuTemp != null;
                    String str2 = stuTemp.getStuName();
                    String str3 = String.valueOf(stuTemp.getGrade());
                    String str4 = String.valueOf(stuTemp.getStuClass());
                    String str5 = String.valueOf(stuTemp.isCanVaccinate());
                    String str6 = "No vaccination data";
                    if (str5.equals(String.valueOf(true))) {
                        str6 = stuTemp.getVaccination().toString();
                    }
                    stuList.add("Name: " + str2 + "\nID: " + str1 + "\nGrade: " + str3 + "\nClass: " + str4 + "\nCan Vaccinate: " + str5 + "\n" + str6);
                }
                adp = new CustomAdapter(DataSortActivity.this, stuList);
                list.setAdapter(adp);
            }

            /**
             * @param databaseError A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, options);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cls);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grade);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
    }

    @Override
    protected void onPause() {
        if (stuListener != null)
            refStudents.removeEventListener(stuListener);
        super.onPause();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == spinner) {
            switch (position) {
                case 1: {
                    filterType = 1;
                    break;
                }
                case 2: {
                    filterType = 2;
                    break;
                }
                case 3: {
                    query = refStudents.orderByChild("grade");
                    query.addValueEventListener(stuListener);
                    break;
                }
                case 4: {
                    query = refStudents.orderByChild("canVaccinate").equalTo(false);
                    query.addValueEventListener(stuListener);
                    break;
                }
            }
        }
        if (parent == spinner1 && filterType == 1) {
            query = refStudents.orderByChild("stuClass").equalTo(Integer.parseInt(cls[position]));
            query.addValueEventListener(stuListener);
        }
        if (parent == spinner2 && filterType == 2) {
            query = refStudents.orderByChild("grade").equalTo(Integer.parseInt(grade[position]));
            query.addValueEventListener(stuListener);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}