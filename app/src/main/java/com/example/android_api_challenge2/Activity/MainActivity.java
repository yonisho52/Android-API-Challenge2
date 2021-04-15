package com.example.android_api_challenge2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import com.example.android_api_challenge2.R;
import com.example.android_api_challenge2.Fragments.ShowStateBordersFragment;
import com.example.android_api_challenge2.Model.State;
import com.example.android_api_challenge2.Fragments.ShowAllStatesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShowAllStatesFragment.OnFirstFragmentInteractionListener, ShowStateBordersFragment.OnSecondFragmentInteractionListener {

    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fregment_container);

        if(fragment == null ) // if it the first time to call the first fragment
        {
            fragment = new ShowAllStatesFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fregment_container, fragment,"0").commit();
        }
    }

    public void LoadSecFragment(State state) // replace the first fragment with the second fragment
    {
        ShowStateBordersFragment showStateBordersFragment = new ShowStateBordersFragment();
        getIntent().putExtra("StateOb",  state);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fregment_container, showStateBordersFragment,(getSupportFragmentManager().getBackStackEntryCount()-1)+"").addToBackStack(null).commit();
        flag =1;
    }

    public void GoBack() { // return to first fragment

        Fragment fragment;
        getSupportFragmentManager().popBackStack();
        fragment = getSupportFragmentManager().findFragmentByTag("0");
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, fragment,(getSupportFragmentManager().getBackStackEntryCount()-1)+"").commit();
        flag=0;
    }

    @Override
    public void onBackPressed() { // override the back button on android phones

        if( flag == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    }).setNegativeButton("No", null).show();
        }
    }

    public int GetFlag() {return this.flag;}

    @Override
    public void onSecondFragmentInteraction(Uri uri) { }

    @Override
    public void onFirstFragmentInteraction(Uri uri) { }

    public void SetAllStates(ArrayList<State> states) {
        ShowAllStatesFragment fragment = new ShowAllStatesFragment();
        fragment.setAllStates(states);
    }
}
