package com.example.multiTranslator;

import android.os.Bundle;

import com.example.multiTranslator.R;

import org.twinone.androidwizard.WizardActivity;
import org.twinone.androidwizard.fragments.TextWizardFragment;
import org.twinone.androidwizard.fragments.WelcomeWizardFragment;

public class MyWizardActivity extends WizardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomeWizardFragment.newInstance(
                "Multi-Language translator",
                "Translate text from english to other languages in the world",
                "Tap next to continue",
                R.mipmap.britain
        ).addTo(this);

        // A TextWizardFragment is a very simple information screen
        TextWizardFragment.newInstance(
                "English-Kiswahili", "This is app can translate English to kiswahili"
        ).addTo(this);

        TextWizardFragment.newInstance(
                "Kiswahili-French", "You can use this app to translate kiswahili language to french"
        ).addTo(this);

    }
}
