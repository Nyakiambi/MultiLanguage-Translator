package com.example.multiTranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.multiTranslator.R;
import com.google.android.material.navigation.NavigationView;

import org.twinone.androidwizard.WizardActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    NavigationView- Represents a standard navigation menu for application. The menu contents can be populated by a menu resource file.
//    NavigationView.OnNavigationItemSelectedListener - Listener for handling events on navigation items.
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
 //the implementation of the onCreate method might bind data to lists, associate activity to ViewModel and instantiate some class scope variables.
//        this method receives savedInstanceState parameter which is bundle object containing the activity's previously saved state.If activity has never
//        existed before bundle is null
        super.onCreate(savedInstanceState);
//method overriding i.e telling VM to run our code in addition to the existing code in the onCreate() of the parent class

        setContentView(R.layout.activity_main);
//loads the ui component from activity_main xml file via method setContentView();Set the content activity from the layout resource.The resource will be inflated
        mDrawerlayout=findViewById(R.id.drawer);
//        requireViewById() method returns a view Object
        mToggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.Open,R.string.Close);
//        -Construct a new ActionBarDrawerToggle.
//        The given activity will be linked to the specified DrawerLayout and its Actionbar's Up button will be set to a custom drawable.
//        This drawable shows a Hamburger icon when drawer is closed and an arrow when drawer is open. It animates between these two states as the drawer opens.
//        String resources must be provided to describe the open/close drawer actions for accessibility services.
//        param activity-The Activity hosting the drawer. Should have an ActionBar.
//        param drawerLayout-The DrawerLayout to link to the given Activity's ActionBar
//        param openDrawerContentDescResA String resource to describe the "open drawer" action for accessibility
//        param closeDrawerContentDescRes- A String resource to describe the "close drawer" action
        mDrawerlayout.addDrawerListener(mToggle);
//        Adds the specified listener to the list of listeners that will be notified of drawer events.
        mToggle.syncState();
//        Synchronize the state of the drawer indicator
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Retrieve a reference to this activity's ActionBar.
//             Set whether home should be displayed as an "up" affordance.
//             Set this to true if selecting "home" returns up by a single level in your UImrather than back to the top level or front page.
//             param showHomeAsUp true to show the user that selecting home will return one level up rather than to the top level of the app.

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); //Set a listener that will be notified when a menu item is selected.
if (savedInstanceState==null){
//    setting homeFragment a default fragment when MainActivity is started i.e the fragment container is replaced with the HomeFragment
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homeFragment()).commit();
    navigationView.setCheckedItem(R.id.homeItem);
}

    }


    @Override
//    when drawer menu item is clicked and matches with the ids assigned to each item
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homeFragment()).commit();
                break;
            case R.id.exploreItem:
//                calling openExplorePage function
                openExplorePage();
                break;
            case R.id.aboutItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutFragment()).commit();
                break;
            case R.id.helpItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HelpFragment()).commit();
                break;
            case  R.id.rateItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddRateFragment()).commit();
                break;
            case R.id.shareItem:
//                calling share function to display action chooser
                share();
                break;


        }
//        instructing the navigationDrawer to close once the selected item has been closed
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
//method for opening action chooser
    private void share() {
//        Intent is(intention to perform action 'layman's def).A messaging Object that you can use to request an action from another app component or an Activity
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String title="Language translator";
        String shareSubText="Multi-Language translator";
        String shareBodyText="https://group_18language_translator";
//        intents for passing data to the target application
        intent.putExtra(Intent.EXTRA_TITLE,title);
        intent.putExtra(Intent.EXTRA_SUBJECT,shareSubText);
        intent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
//        starting the new activity and passing the parameters
        startActivity(Intent.createChooser(intent,"Share multi-translator with:"));
    }
//method for opening explore option from the drawer menu
    private void openExplorePage() {
        WizardActivity.show(this, MyWizardActivity.class, true);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
