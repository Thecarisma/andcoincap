package gig.com.andcoincap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import gig.com.andcoincap.base.BaseActivity;

public class MainActivity extends BaseActivity {

    public Fragment fragment;
    FragmentManager fragmentManager ;
    public static ImageButton addCardButton ;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        addCardButton = (ImageButton) findViewById(R.id.add_card_button);

        //listView.setAdapter(adapter);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_content, new Home())
                .commit();

    }

    public void notifyFragmentManager(int id) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .addToBackStack(""+id)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
