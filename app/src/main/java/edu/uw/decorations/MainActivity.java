package edu.uw.decorations;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add a context menu to the big button
        registerForContextMenu(findViewById(R.id.button));

        //show fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new WordListFragment());
        ft.commit();
    }

    //toggles the action bar
    public void handleButton(View v) {

        ActionBar toolbar = getSupportActionBar();
        if(toolbar.isShowing())
            toolbar.hide();
        else
            toolbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true; //we've provided a menu!
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.hello_menu_item:
                new HelloDialogFragment().show(getSupportFragmentManager(), null);
                return true; //handled
            case R.id.hi_menu_item:
                Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show();
                return true; //handled
            case R.id.list_menu_item:
                WordListFragment.newInstance().show(getSupportFragmentManager(), null);
                return true; //handled
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.v(TAG, "You clicked a context menu item!");
        return super.onContextItemSelected(item);
    }

    public static class HelloDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Alert!")
                    .setMessage("Danger Will Robinson!"); //note chaining
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.v(TAG, "You clicked okay! Good times :)");
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.v(TAG, "You clicked cancel! Sad times :(");
                }
            });

            AlertDialog dialog = builder.create();
            return dialog;
        }
    }
}
