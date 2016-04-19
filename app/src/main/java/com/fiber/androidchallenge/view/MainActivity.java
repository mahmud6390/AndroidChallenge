package com.fiber.androidchallenge.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.fiber.androidchallenge.R;
import com.fiber.androidchallenge.model.Offers;
import com.fiber.androidchallenge.utils.Constant;
import com.fiber.androidchallenge.utils.PreferenceHelper;
import com.fiber.androidchallenge.utils.UiListener;
import com.fiber.androidchallenge.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(this);
        getFragmentManager().beginTransaction().add(R.id.content_view,new ViewOfferFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            PreferenceHelper.removeAllData();
            List<Offers>offersList=new ArrayList<>();
            UiListener.getInstance().notifyUi(Constant.ACTION_OFFER_LIST_UPDATE,offersList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                getFragmentManager().beginTransaction().add(R.id.content_view,new AddInfoParamFragment()).commit();
                break;
        }
    }
}
