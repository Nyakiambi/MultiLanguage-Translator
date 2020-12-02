package com.example.multiTranslator;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multiTranslator.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import java.util.ArrayList;
import java.util.List;
public class homeFragment extends Fragment {
//    variable declaration
    Spinner sourceLanguageSpin, targetLanguageSpin, retranslateSpin;
    Button translateButton;
    Button translateBackButton;
    EditText sourceLanguage, translatedTextField;
    TextView retSource;
//    context meaning
    /**
     * Interface to global information about an application environment.  This is
     * an abstract class whose implementation is provided by
     * the Android system.  It
     * allows access to application-specific resources and classes, as well as
     * up-calls for application-level operations such as launching activities,
     * broadcasting and receiving intents, etc.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        inflating home fragment xml file
        final View view = inflater.inflate(R.layout.home_fragment, container, false);
//        findviewById()-Finds the first descendant view with the given ID, the view itself if the ID matches
//   inflate()- Inflate a new view hierarchy from the specified xml resource
//param root--Optional view to be the parent of the generated hierarchy if attachToRoot is true, or else simply an object that
//provides a set of LayoutParams values for root of the returned hierarchy
//param attachToRoot- Whether the inflated hierarchy should be attached to the root parameter? If false, root is only used to create the correct subclass of LayoutParams for the root view in the XML.
        sourceLanguageSpin = view.findViewById(R.id.SourcelanguageSpinner);
        targetLanguageSpin = view.findViewById(R.id.TargetlanguageSpinner);
        retranslateSpin = view.findViewById(R.id.targetLanguage);
        translateButton = view.findViewById(R.id.translateButton);
        translateBackButton = view.findViewById(R.id.translateBack);
        retSource = view.findViewById(R.id.retSource);
        translatedTextField = view.findViewById(R.id.translatedString);
        sourceLanguage = view.findViewById(R.id.textSource);
//        calling Methods for loading Spinner data
        loadretranslateSpinner();
        loadsourceLanguageSpin();
        loadtargetLanguageSpin();
//        the following code is executed when translateButton is clicked
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getting text from the source language spinner
//                getSelectedItem()-The data corresponding to the currently selected item
//                toString()-Returns a string representation of the object.
//                trim()- Returns a string whose value with any leading and trailing whitespace removed.
                String sourceL = sourceLanguageSpin.getSelectedItem().toString().trim();
//                getting text from the  target language spinner
                String targetL = targetLanguageSpin.getSelectedItem().toString().trim();
//                getting text from the source language text field.This text is to be translated by  translator
                String text = sourceLanguage.getText().toString();
//                calling  translate function and passing the parameters: Source language,Target language and text to be translated
 translate(sourceL,targetL,text);
            }
        });
//        below is the source code for button to translate the translated text to the original source language
        translateBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceL = retSource.getText().toString();
                String targetL = retranslateSpin.getSelectedItem().toString().trim();
//                calling function to tranlsate text  back to the source language and passing the parameters: Source language  and Target language
                retranslate(sourceL,targetL);
            }
        });
        return view;
    }
// below is the source code for translating text back to the original source language
    private void retranslate(String sourceL, String targetL) {
//        if the user selects  SWAHILI AND FRENCH as the souce and target languages respectively, the following  code will be executed
        if (sourceL.equals("SWAHILI") & targetL.equals("FRENCH")){
//            creating translatorOptios object and setting the source language and target language respectively
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.SWAHILI)
                            .setTargetLanguage(TranslateLanguage.FRENCH)
                            .build();
//            creating swahiliFrenchTranslator object
            final Translator swahiliFrenchTranslator =
                    Translation.getClient(options);
//            setting conditions necessary for dowloading swahili-french model
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
//            downloading the model if it's unavailable in the user's device
            swahiliFrenchTranslator.downloadModelIfNeeded(conditions)
//                    when model is successfully downloaded
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
//                    when model download failed
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                }
                            });
//            if model is found in the device or is successfully downloaded
            swahiliFrenchTranslator.translate(translatedTextField.getText().toString())
//                    when text is translated successfully
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
//                                    displaying the translated text in the editText
                                    sourceLanguage.setText(s);
                                }
                            })
//                   text translation failed
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                }
                            });
        }
        else if (sourceL.equals("SWAHILI") & targetL.equals("ENGLISH")){
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.SWAHILI)
                            .setTargetLanguage(TranslateLanguage.ENGLISH)
                            .build();
            final Translator swahiliEnglishTranslator =
                    Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
            swahiliEnglishTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                }
                            });
            swahiliEnglishTranslator.translate(translatedTextField.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    sourceLanguage.setText(s);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                }
                            });
        }
        else if (sourceL.equals("FRENCH") & targetL.equals("SWAHILI")){
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.FRENCH)
                            .setTargetLanguage(TranslateLanguage.SWAHILI)
                            .build();
            final Translator frenchSwahiliTranslator =
                    Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
            frenchSwahiliTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                }
                            });
            frenchSwahiliTranslator.translate(translatedTextField.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    sourceLanguage.setText(s);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                }
                            });
        }
        else if (sourceL.equals("FRENCH") & targetL.equals("ENGLISH")){
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.FRENCH)
                            .setTargetLanguage(TranslateLanguage.ENGLISH)
                            .build();
            final Translator frenchEnglishTranslator =
                    Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
            frenchEnglishTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                }
                            });
            frenchEnglishTranslator.translate(translatedTextField.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    sourceLanguage.setText(s);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                }
                            });
        }
        else{
            Toast.makeText(getContext(),"Invalid source and target  language selected please redo again",Toast.LENGTH_LONG).show();
        }
    }

    private void translate(String sourceL, final String targetL, String text) {
        if (sourceL.equals("ENGLISH") & targetL.equals("SWAHILI")){
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.ENGLISH)
                            .setTargetLanguage(TranslateLanguage.SWAHILI)
                            .build();
            final Translator englishSwahiliTranslator =
                    Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
            englishSwahiliTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setIcon(R.drawable.error);
                                    builder.setTitle("Error occured");
                                    builder.setMessage("Error occured while downloading your model..Please do check your network connection");
                                    builder.show();
                                }
                            });
            englishSwahiliTranslator.translate(text)
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    translatedTextField.setText(s);
                                    retSource.setText(targetL);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                }
                            });
        }
        if (sourceL.equals("ENGLISH") & targetL.equals("FRENCH")){
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.ENGLISH)
                            .setTargetLanguage(TranslateLanguage.FRENCH)
                            .build();
            final Translator englishFrenchTranslator =
                    Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder()
                    .requireWifi()
                    .build();
            englishFrenchTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(),"model downloaded successfully",Toast.LENGTH_LONG).show();

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Model couldn’t be downloaded or other internal error.
                                    // ...
                                    Toast.makeText(getContext(),"failed to download model",Toast.LENGTH_LONG).show();
                                }
                            });
            englishFrenchTranslator.translate(text)
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    translatedTextField.setText(s);
                                    retSource.setText(targetL);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
//                                    Toast.makeText(getContext(),"failed to translate",Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    alert.setTitle("Error Ocurred");
                                    alert.setMessage("Failed to translate,Please do check the availability of the required model");
                                    alert.setCancelable(true);
                                    alert.show();
                                }
                            });
        }

    }

//below are methods for loading spinner data
    private void loadretranslateSpinner() {
        List<String> items = new ArrayList<String>();
        items.add("ENGLISH");
        items.add("SWAHILI");
        items.add("FRENCH");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retranslateSpin.setAdapter(dataAdapter);
//
    }
//
    private void loadtargetLanguageSpin() {
        List<String> items = new ArrayList<String>();
        items.add("SWAHILI");
        items.add("FRENCH");
        ArrayAdapter<String> adpt = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetLanguageSpin.setAdapter(adpt);
    }

//
    private void loadsourceLanguageSpin() {
        List<String> items = new ArrayList<String>();
        items.add("ENGLISH");
        items.add("SWAHILI");
        items.add("FRENCH");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceLanguageSpin.setAdapter(dataAdapter);
    }
}
