package com.example.db_for_st;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends Activity {
    DBBadBoys mDBConnector;
    Context context;
    ListView mListView;
    ListAdapt myAdapter;
    int ADD_ACTIVITY = 0;
    int UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        context = this;
        mDBConnector = new DBBadBoys(this);
        mListView = (ListView) findViewById(R.id.second_list);
        myAdapter = new ListAdapt(context, mDBConnector.selectAll());
        mListView.setAdapter(myAdapter);
        registerForContextMenu(mListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_second:
                Intent i = new Intent(context, AddBoys.class);
                startActivityForResult(i, ADD_ACTIVITY);
                updateList();
                return true;
            case R.id.return_s:
                Intent in = new Intent(context, MainActivity.class);
                startActivityForResult(in, ADD_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_second, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit_second:
                Intent i = new Intent(context, AddBoys.class);
                BadBoys md = mDBConnector.select(info.id);
                i.putExtra("BadBoys", md);
                startActivityForResult(i, UPDATE_ACTIVITY);
                updateList();
                return true;
            case R.id.delete_second:
                mDBConnector.delete(info.id);
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateList() {
        myAdapter.setArrayMyData(mDBConnector.selectAll());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            BadBoys md = (BadBoys) data.getExtras().getSerializable("BadBoys");
            if (requestCode == UPDATE_ACTIVITY)
                mDBConnector.update(md);
            else
                mDBConnector.insert(md.getBadboy(), md.getGoalshouse(), md.getGoalsguest());
            updateList();
        }
    }

    class ListAdapt extends BaseAdapter {
        private final LayoutInflater layoutInflater;
        private ArrayList<BadBoys> arrayMyBoys;

        public ListAdapt(Context ctx, ArrayList<BadBoys> arr) {
            layoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        public ArrayList<BadBoys> getArrayMyData() {
            return arrayMyBoys;
        }

        public void setArrayMyData(ArrayList<BadBoys> arrayMyData) {
            this.arrayMyBoys = arrayMyData;
        }

        public int getCount() {
            return arrayMyBoys.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            BadBoys md = arrayMyBoys.get(position);
            if (md != null) {
                return md.getId();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.boy, null);
            TextView vBoy = (TextView) convertView.findViewById(R.id.badboys);
            TextView vTotal = (TextView) convertView.findViewById(R.id.total);
            TextView vResult = (TextView) convertView.findViewById(R.id.result);
            BadBoys md = arrayMyBoys.get(position);
            vBoy.setText(md.getBadboy());
            vTotal.setText(md.getGoalshouse() + ":" + md.getGoalsguest());
            if (md.getGoalshouse()>md.getGoalsguest()) {
                vResult.setText("Победа");
                vResult.setTextColor(Color.GREEN);
            }
            else {
                vResult.setText("Поражение");
                vResult.setTextColor(Color.RED);
            }
            return convertView;
        }
    }
}
