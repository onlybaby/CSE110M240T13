package cse110mt13.tritonprofessorraterv1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
*/
import com.parse.ParseUser;

import java.util.Vector;

public class SearchPage extends AppCompatActivity {
    //UI--- search page
    int[] searcImages = {R.mipmap.defaultprofile};
    Vector<String> searchNames =  new Vector<>();
    Vector<String> easiness = new Vector<>();
    Vector<String> helpfulness = new Vector<>();
    Vector<String> clarity = new Vector<>();
    ListView l;
    ProfList profs;
    EditText etSearch;
    String search = "";
    ProfList nameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etSearch = (EditText)findViewById(R.id.search_ET);
        findViewById(R.id.search_B).setOnClickListener(onclickListener);
        findViewById(R.id.sp_AddProf_B).setOnClickListener(onclickListener);

        Resources res = getResources();
        nameList = new ProfList();
        String easy;
        String help;
        String clear;
        Intent intentBundle = getIntent();
        if(intentBundle.hasExtra("search")) {
            search = intentBundle.getStringExtra("search");
            nameList.nameSearch(search);
        }

        if(nameList.professors.size() == 0){
            forwardToSearchNotFound();
        }

        for(Professor name: nameList.professors){
            searchNames.add(name.getName());
            easy = "easiness : " + name.getEasiness();
            help = "helpfulness : " + name.getHelpfulness();
            clear = "clarity : " + name.getClarity();
            easiness.add(easy);
            helpfulness.add(help);
            clarity.add(clear);
        }
        //get listview for main page
        l= (ListView) findViewById(R.id.listViewSearch_page);
        MyAdapter adapter = new MyAdapter(this, searchNames, searcImages,easiness, helpfulness, clarity);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT).show();
 //               String sText = ((TextView) view).getText().toString();
               // Log.d("SearchTest", nameList.professors.get(position).getObjID()); //retrieve objectID of the corresponding professor

                Intent intent = null;
                intent = new Intent(getBaseContext(), ProfPage.class);
                intent.putExtra("profID",nameList.professors.get(position).getObjID());
                if(intent != null)
                    startActivity(intent);
            }
        });

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void forwardToSearchNotFound(){
        Intent intent = new Intent(SearchPage.this, SearchNotFound.class);
        intent.putExtra("search",search);
        finish();
        startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        int[] images;
        Vector<String> nameArray;
        Vector<String> easyR;
        Vector<String> helpfulR;
        Vector<String> clarityR;
        MyAdapter(Context c, Vector<String> prof_name, int imgs[], Vector<String> easiness, Vector<String> helpfulness, Vector<String> clarity)
        {
            super(c, R.layout.single_row, R.id.textView9, prof_name);
            this.context = c;
            this.nameArray = prof_name;
            this.images = imgs;
            this.easyR = easiness;
            this.helpfulR = helpfulness;
            this.clarityR = clarity;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);
            ImageView myImage = (ImageView)row.findViewById(R.id.imageView);
            TextView myProfNames = (TextView) row.findViewById(R.id.textView9);
            TextView myEasiness = (TextView) row.findViewById(R.id.easiness);
            TextView myHelpfulness = (TextView) row.findViewById(R.id.helpfulness);
            TextView myClarity = (TextView) row.findViewById(R.id.clarity);
            myImage.setImageResource(images[0]);
            myProfNames.setText(nameArray.get(position));


            if(nameList.professors.get(position).getNumRatings() >=1){
                myEasiness.setText(easyR.get(position));
                myHelpfulness.setText(helpfulR.get(position));
                myClarity.setText(clarityR.get(position));
            }
            else{
                myEasiness.setText("Easiness: N/A");
                myHelpfulness.setText("Helpfulness: N/A");
                myClarity.setText("Clarity: N/A");
            }
            return row;
        }
    }


    private View.OnClickListener onclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_B:
                     /*  TODO: write backend function to determine whether clicking on search
                   should jump to none found page, or search-found page
                 */
                    Intent searchIntent = new Intent(SearchPage.this, SearchPage.class);
                    searchIntent.putExtra("search", etSearch.getText().toString());
                  //  finish(); //end current activity
                    startActivity(searchIntent); //create a new activity*/
                    break;
                case R.id.sp_AddProf_B:
                  //  finish(); //end current activity
                    startActivity(new Intent(SearchPage.this, AddProf.class)); //create a new activity
                    break;
            }
        }
    };

    public void ForwardToAddProf(View view) {
        startActivity(new Intent(this, AddProf.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_Logout:
                logout();
                return true;
        }
        return false;
        // return super.onOptionsItemSelected(item);
    }

    public void refresh () {
        Intent intent = new Intent(SearchPage.this, SearchPage.class);
        finish();
        startActivity(intent);
    }

    public void logout() {
        Intent intent = new Intent(SearchPage.this, LoginActivity.class);
        if(ParseUser.getCurrentUser() != null) {
            Log.d("Test123","Logging out");
            ParseUser.logOut();
        }
        finish();
        startActivity(intent);
    }
}
