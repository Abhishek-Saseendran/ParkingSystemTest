package com.abhishek.parkingsystemtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek.parkingsystemtest.Models.AppUser;
import com.abhishek.parkingsystemtest.Models.ParkingSlot;
import com.abhishek.parkingsystemtest.Models.UserHistory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    EditText etLicensePlate;
    Button btnArrive, btnCancel, btnCheckout, btn;

    AppUser user;
    ParkingSlot slot;
    UserHistory history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        etLicensePlate = findViewById(R.id.etLicensePlate);
        btnArrive = findViewById(R.id.btnArrival);
        btnCancel = findViewById(R.id.btnCancel);
        btnCheckout = findViewById(R.id.btnCheckout);
        btn = findViewById(R.id.btn);

        user = new AppUser();
        slot = new ParkingSlot();
        history = new UserHistory();

        btnArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLicensePlate.getText() != null && !etLicensePlate.getText().toString().isEmpty()){
                    updateSlotAndUser();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutParking();
            }
        });

    }

    private void checkoutParking() {
        String license = etLicensePlate.getText().toString().trim();
        firestore.collection("PARKING")
                .whereEqualTo("licensePlate", license)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0){
                            slot = queryDocumentSnapshots.getDocuments().get(0).toObject(ParkingSlot.class);
                            if(slot != null && slot.getLicensePlate().equals(license)){

                                //Just set Is Ready to true!!
                                firestore.collection("USERS").document(slot.getUserId())
                                        .update("isReady", true)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(MainActivity.this,"Successfully updated to users Checkout!!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Not booked!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(MainActivity.this, "No such Slot Exists!!" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cancelBooking() {
        String license = etLicensePlate.getText().toString().trim();
        firestore.collection("PARKING")
                .whereEqualTo("licensePlate", license)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0){
                            slot = queryDocumentSnapshots.getDocuments().get(0).toObject(ParkingSlot.class);
                            if(slot != null && slot.getLicensePlate().equals(license)){

                                //First set History arrival to new Timestamp(0,0)
                                //Then update User isReady to true and checkout!!
                                firestore.collection("USERS").document(slot.getUserId())
                                        .collection("HISTORY").document(slot.getTransactionId())
                                        .update("arrival", new Timestamp(0,0))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                //Now update isReady is user's document.. Then the app takes care of it!!
                                                if(task.isSuccessful()){
                                                    firestore.collection("USERS").document(slot.getUserId())
                                                            .update("isReady", true,
                                                                    "parked", false)
                                                            .addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull @NotNull Task task) {
                                                                    Toast.makeText(MainActivity.this,
                                                                            "Cancelled!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Not booked!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(MainActivity.this, "No such Slot Exists!!" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateSlotAndUser() {
        String license = etLicensePlate.getText().toString().trim();
        firestore.collection("PARKING")
                .whereEqualTo("licensePlate", license)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0){
                            slot = queryDocumentSnapshots.getDocuments().get(0).toObject(ParkingSlot.class);
                            if(slot != null && slot.getLicensePlate().equals(license)){
                                slot.setParked(true);

                                firestore.collection("PARKING").document(slot.getSlotId())
                                        .update("parked", slot.isParked())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(MainActivity.this,"Successfully updated to slots!!"
                                                            + slot.getSlotId(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                firestore.collection("USERS").document(slot.getUserId())
                                        .update("parked", true)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(MainActivity.this,"Successfully updated to users!!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Not booked!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(MainActivity.this, "No such Slot Exists!!" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }



}