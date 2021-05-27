package com.example.db_for_st;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;




import java.util.ArrayList;

public class MainActivity extends Activity {
    DBTeams mDBConnector;
    Context mContext;
    ListView mListView;
    myListAdapter myAdapter;
    int ADD_ACTIVITY = 0;
    int UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mDBConnector = new DBTeams(this);
        mListView = (ListView) findViewById(R.id.list);
        myAdapter = new myListAdapter(mContext, mDBConnector.selectAll());
        mListView.setAdapter(myAdapter);
        registerForContextMenu(mListView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(mContext, AddTeams.class);
                startActivityForResult(i, ADD_ACTIVITY);
                updateList();
                return true;
            case R.id.deleteAll:
                mDBConnector.deleteAll();
                updateList();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(mContext, AddTeams.class);
                Teams md = mDBConnector.select(info.id);
                i.putExtra("Teams", md);
                startActivityForResult(i, UPDATE_ACTIVITY);
                updateList();
                return true;
            case R.id.show:
                Intent g = new Intent(mContext, SecondActivity.class);
                Teams d = mDBConnector.select(info.id);
                g.putExtra("Show", d);
                startActivity(g);
                updateList();
                return true;
            case R.id.delete:
                mDBConnector.delete (info.id);
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
            Teams md = (Teams) data.getExtras().getSerializable("Teams");
            if (requestCode == UPDATE_ACTIVITY)
                mDBConnector.update(md);
            else
                mDBConnector.insert(md.getTeam());
            updateList();
        }
    }

    class myListAdapter extends BaseAdapter {
        private final LayoutInflater mLayoutInflater;
        private ArrayList<Teams> arrayMyMatches;

        public myListAdapter(Context ctx, ArrayList<Teams> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        public ArrayList<Teams> getArrayMyData() {
            return arrayMyMatches;
        }

        public void setArrayMyData(ArrayList<Teams> arrayMyData) {
            this.arrayMyMatches = arrayMyData;
        }

        public int getCount() {
            return arrayMyMatches.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            Teams md = arrayMyMatches.get(position);
            if (md != null) {
                return md.getId();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);
            TextView vTeamHome = (TextView) convertView.findViewById(R.id.Team);
            Teams md = arrayMyMatches.get(position);
            vTeamHome.setText(md.getTeam());

            return convertView;
        }

    }
}
