package com.example.multiTranslator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multiTranslator.R;

public class HelpFragment extends Fragment {
    EditText complainField,emailField;
    Button cancelButton,submitButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_help,container,false);
       complainField=view.findViewById(R.id.complainInput);
       emailField=view.findViewById(R.id.emailInput);
       cancelButton=view.findViewById(R.id.cancel);
       submitButton=view.findViewById(R.id.submit);
//       action events for submit button
        submitButtonOnclick();
        return view;
    }

    private void submitButtonOnclick() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complain=complainField.getText().toString();
                String email=emailField.getText().toString();
                if(TextUtils.isEmpty(complain) || TextUtils.isEmpty(email)){
                    Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                }
                else{
                    SendComplain(complain,email);
                }
            }
        });
    }

    private void SendComplain(String complain, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND); // Activity Action: Send a message to someone specified by the data.
        emailIntent.setType("message/rfc822"); // This is used to create intents that only specify a type and not data
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"boiyonaudrey@gmail.com"}); //  A String[] holding e-mail addresses that should be delivered to.
//        putExtra() method Add extended data to the intent.
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Issue/Complains Multi-translator app");//A constant string holding the desired subject line of a message.
        emailIntent.putExtra(Intent.EXTRA_TEXT, complain);//A constant CharSequence that is associated with the Intent, used with .ACTION_SEND} to supply the data to be sent.
        try {
            startActivity(emailIntent); // Call activity from the fragment's containing Activity
        } catch (android.content.ActivityNotFoundException ex) {
//            ActivityNotFoundException--This exception is thrown when a call to activity or one of its
//            variants fails because an Activity can not be found to execute the given Intent.
            Toast.makeText(getContext(), "No email client configured. Please check...", Toast.LENGTH_LONG).show();
  // A toast is a view containing a quick little message for the user.  The toast class helps you create and show those little messages
//  makeText() Make a standard toast to display using the specified looper.
        }
    }
}
