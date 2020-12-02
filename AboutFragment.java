package com.example.multiTranslator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multiTranslator.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        @Nullable Bundle savedInstanceState --nullable simply means its(bundle) value can be null when there was no process running in the previous activity
        Element versionElement=new Element();// * Element class represents an about item in the about page.
        // Use addItem() to add your items to the AboutPage.
        versionElement.setTitle("version 1.0.0"); //Set the title for the element versionElement
        Element adsElement = new Element();
        adsElement.setTitle("multiple languages translator");
        return new AboutPage(getContext())
                .isRTL(false) //Turning  on the RTL mode.
                .enableDarkMode(false) //Provide a way to force dark mode or not
                .setDescription("This   app is mainly used to translate english to kiswahili.Kiswahili" +
                        " can further be translated to french and vice versa")

                .setImage(R.drawable.britain)
                .addItem(versionElement)
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("hansonkib@gmail.com")
                .addWebsite("#")
                .addFacebook("#")
                .create();
    }
}
