
package com.example.firebasertdatabase;

import static com.example.firebasertdatabase.FBref.refStudents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Credits:
 * yes
 */
public class MainActivity extends AppCompatActivity {

    int gradeN, gradeClassN;
    String stuNameN, stuIDN, placeN, dateN, place1N, date1N, error;

    CheckBox canVaccinate, vaccinate1;
    boolean canVaccinateN, vaccinatedTwice;

    EditText grade, gradeClass, stuName, stuID, place, date, place1, date1;

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

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grade = findViewById(R.id.grade);
        gradeClass = findViewById(R.id.gradeClass);
        stuName = findViewById(R.id.stuName);
        stuID = findViewById(R.id.stuID);
        place = findViewById(R.id.place);
        date = findViewById(R.id.date);
        place1 = findViewById(R.id.place1);
        date1 = findViewById(R.id.date1);

        canVaccinate = findViewById(R.id.canVaccinate);
        vaccinate1 = findViewById(R.id.vaccinate1);
    }

    public void onSend(View view) {
        try {
            gradeN = Integer.parseInt(grade.getText().toString());
            gradeClassN = Integer.parseInt(gradeClass.getText().toString());
            stuNameN = stuName.getText().toString();
            stuIDN = stuID.getText().toString();

            canVaccinateN = canVaccinate.isChecked();

            vaccinatedTwice = vaccinate1.isChecked();

            if (gradeN > 12) {
                error = "Invalid grade.";
                throw new Exception();
            }

            if (gradeClassN > 6) {
                error = "Invalid Class.";
                throw new Exception();
            }

            Student student;
            if (canVaccinateN) {
                placeN = place.getText().toString();
                dateN = date.getText().toString();
                String[] dateA = dateN.split("/");
                int dateD = Integer.parseInt(dateA[0]), dateM = Integer.parseInt(dateA[1]);
                if (dateA.length != 3 || dateD > 31 || dateM > 12) {
                    error = "Invalid date format.";
                    throw new Exception();
                }
                if (vaccinatedTwice) {
                    place1N = place1.getText().toString();
                    date1N = date1.getText().toString();
                    String[] dateA1 = date1N.split("/");
                    int dateD1 = Integer.parseInt(dateA[0]), dateM1 = Integer.parseInt(dateA[1]);
                    if (dateA1.length != 3 || dateD1 > 31 || dateM1 > 12) {
                        error = "Invalid date format.";
                        throw new Exception();
                    }
                    student = new Student(gradeN, gradeClassN, stuNameN, stuIDN, true, new StuVaccine(placeN, dateN), new StuVaccine(place1N, date1N));
                } else {
                    student = new Student(gradeN, gradeClassN, stuNameN, stuIDN, true, new StuVaccine(placeN, dateN), null);
                }
            } else {
                student = new Student(gradeN, gradeClassN, stuNameN, stuIDN, false, null, null);
            }
            refStudents.child(stuIDN).setValue(student);
            Toast.makeText(this, "Data sent successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "ERROR: " + error, Toast.LENGTH_SHORT).show();
        }
    }
}
