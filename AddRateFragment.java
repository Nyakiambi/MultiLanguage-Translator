package com.example.multiTranslator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.multiTranslator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AddRateFragment extends Fragment {
    private DatabaseReference mDatabase;//A Firebase reference represents a particular location in your Database and can be used for
      //reading or writing data to that Database location.



    RatingBar rateBar;
    Button cancelButton,submittButton;
    EditText review,email;
    ListView lst;
    ArrayList<String> emailAddress;
    ArrayList<String> ratings;
    ArrayList<String> reviews;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LayoutInflator-- * Instantiates a layout XML file into its corresponding view objects. It is never used directly.
        View view= inflater.inflate(R.layout.fragment_addrate,container,false);
//         View-- This class represents the basic building block for user interface components.
//         A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
//         View is the base class for widgets, which are used to create interactive UI components (buttons, text fields, etc.).
        mDatabase = FirebaseDatabase.getInstance().getReference(); //The entry point for accessing a Firebase Database.
//        getInstance() -Gets a FirebaseDatabase instance for the specified URL.
//        getReference()--Gets a DatabaseReference for the database root node. this method returns A DatabaseReference pointing to the root node.
        rateBar=view.findViewById(R.id.ratingBar);
        cancelButton=view.findViewById(R.id.cancel);
        submittButton=view.findViewById(R.id.submit);
        review=view.findViewById(R.id.reviewInput);
        email=view.findViewById(R.id.emailInput);
        lst=view.findViewById(R.id.lisView);
        emailAddress=new ArrayList<>();
        ratings=new ArrayList<String>();
        reviews=new ArrayList<>();
//        function for fetching reviews

        fetchReviews();
//        when submit button is clicked
         submitButtonOnClick();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Intents-- An intent is an abstract description of an operation to be performed.  It
// can be used to launch an activity,
//  to send it to any interested  components,
// and to communicate with a background service.
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void fetchReviews() {
//code for fetching from remote db starts here


try {
    // Get a reference to our Rates
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("ratings");
//    getReference--   Gets a DatabaseReference for the provided path.
//   * @param path Path to a location in your FirebaseDatabase.
//   * @return A DatabaseReference pointing to the specified path.

// Attach a listener to read the data at our posts reference
    final List<Rate> rates = new ArrayList<>();
//         * Returns the number of elements in this list.  If this list contains
//     * more than {@code Integer.MAX_VALUE} elements, returns
//            * {@code Integer.MAX_VALUE}.
//            *
//     * @return the number of elements in this list
//            */
    Query query=ref.orderByChild("ratings");
//    Query class-- * The Query class (and its subclass,  DatabaseReference) are used for reading data.
// Listeners are attached, and they will be triggered when the corresponding data changes.
    query.addValueEventListener(new ValueEventListener() {
//        addValueEventListener-
//         * Add a listener for child events occurring at this location. When child locations are added,
//         * removed, changed, or moved, the listener will be triggered for the appropriate event

//        orderBy()---
//         * Create a query in which child nodes are ordered by the values of the specified path.
//         *
//         * @param path The path to the child node to use for sorting
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            dataSnapshot--* A DataSnapshot instance contains data from a Firebase Database location. Any time you read
// * Database data, you receive the data as a DataSnapshot.
            rates.clear();
//            clear()--     * Removes all of the elements from this list .
//                    * The list will be empty after this call returns.
            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                Rate rate =postSnapshot.getValue(Rate.class);
//                getValue--   * This method is used to marshall the data contained in this snapshot into a class of your choosing.
                rates.add(rate);
                emailAddress.add(rate.Email);
                ratings.add(String.valueOf(rate.ratings));
                reviews.add(rate.reviews);
            }
            CustomListView customListView=new CustomListView(getContext(),emailAddress,ratings,reviews);
            lst.setAdapter(customListView);
//            setAdapter-- The ListAdapter which is responsible for maintaining the data backing this list and for producing a view to represent an item in that data set.
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
//            DatabaseError -- * Instances of DatabaseError are passed to callbacks when an operation failed. They contain a
//                    * description of the specific error that occurred.
            Toast.makeText(getContext(),"Fetching data from the database failed "+databaseError.getCode(),Toast.LENGTH_LONG).show();
        }
    });
}
catch (Exception e){
    Toast.makeText(getContext(),"Error /t"+e,Toast.LENGTH_LONG).show();
}

//fetchng from remote db ends here

    }

    private void submitButtonOnClick() {
        submittButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ratings=rateBar.getNumStars();
                String reviews=review.getText().toString();
                String emailAddress=email.getText().toString();
                if (TextUtils.isEmpty(reviews) || TextUtils.isEmpty(emailAddress) || ratings==0){
//                    displaying alert dialog when the fields are empty
//                    AlertDialog - A subclass of Dialog that can display one, two or three buttons. If you only want to
//                    display a String in this dialog box, use the setMessage() method.
                    AlertDialog.Builder builder= new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
//                    Builder-- Creates a builder for an alert dialog that uses the default alert dialog theme.
//         * @param context the parent context
                    builder.setIcon(R.drawable.about_icon_email);
                    builder.setTitle("Error occured");
                    builder.setMessage("Please enter all fields before submitting your ratings");
                    builder.show();
                }
                else{
//                    function for submitting rates into the database
                    SubmitData(emailAddress,ratings,reviews);
                }
            }
        });
    }

    private void SubmitData( String emailAddress,  int ratings,String reviews) {
try {
//    creating Rate object
//    generating random numbers
      Random rand= new Random(); //an instance of random class
      int upperbound =10; //generating random numbers from 0 -10
      String tokenNumber= String.valueOf(rand.nextInt(upperbound)); // Typecasting generated random integers to String type i.e  Returns the string representation of the integer argument.
     Rate rate = new Rate(emailAddress,ratings,reviews);
    mDatabase.child("ratings").child(tokenNumber).setValue(rate);
    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());  //creating new alertDialogObject
//    getContext()  Return the context this fragment is currently associated with.
    alert.setIcon(R.drawable.successfull); //set the icon to be displayed
    alert.setTitle("Successfull"); // Set the title displayed in the  dialog.
    alert.setMessage("Ratings submitted successfully"); // Set the message to display.
    alert.setCancelable(true); //Sets whether the dialog is cancelable or not.  Default is true.
    alert.show(); //calling this method  Creates an dialog with the arguments supplied to this builder and immediately displays the dialog.
}
catch (Exception e){
    Toast.makeText(getContext(),"Error /t"+e,Toast.LENGTH_LONG).show();
}
    }
}
