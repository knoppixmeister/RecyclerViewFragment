package bizapps.lv.fragmenttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            MainFragment mf = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

            if(mf != null) {
                mf.gv.invalidateViews();
            }

            return true;
        }
        else if(id == R.id.action_search) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
