package cse110mt13.tritonprofessorraterv1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddComment extends AppCompatActivity {


    final Context context = this;

    EditText acCourseName, acComment;
    RatingBar ac_RatingC, ac_RatingE, ac_RatingH;
    TextView profNameET;
    Professor prof;

    String profID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
  /*      Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseObject.registerSubclass(Professor.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Comment.class);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        acCourseName = (EditText) findViewById(R.id.ac_AddCourse_ET);
        acComment = (EditText) findViewById(R.id.ac_Comment_ET);

        ac_RatingC = (RatingBar) findViewById(R.id.ac_RatingC);
        ac_RatingE = (RatingBar) findViewById(R.id.ac_RatingE);
        ac_RatingH = (RatingBar) findViewById(R.id.ac_RatingH);
        profNameET=(TextView)findViewById(R.id.ac_ProfName_TV);

        findViewById(R.id.ac_Submit_B).setOnClickListener(onclickListener);
        findViewById(R.id.ac_Cancel_B).setOnClickListener(onclickListener);

        Intent intentBundle = getIntent();
        String profID = intentBundle.getStringExtra("profID");
        prof = Professor.getProf(profID);
        profNameET.setText(prof.getName());

    }

    private View.OnClickListener onclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ac_Submit_B:

                     /*  TODO:

                           1. If course name is empty(done) or invalid(to do) (such as CSE9999),make a warning toast(done)
                           2. if course name is valid (CSE100), parse it properly (to CSE 100, with space)(done)
                           3. check if comment is empty or a bunch of empty spaces ( >25%-or more of chars are space-or more?),
                              if so, send warning - comment is empty/invalid(done)
                           4. verification box when clicking submit
                           5. verification box when clicking cancel
                     */



                    /*
                    TODO: Add the new comment , let Neil or Eric do this part
                     */

                    String ac_Course, ac_Comment;
                    int ac_C, ac_E, ac_H;

                    ac_Course = acCourseName.getText().toString();
                    ac_Comment = acComment.getText().toString();

                    ac_C = (int) ac_RatingC.getRating();
                    ac_E = (int) ac_RatingE.getRating();
                    ac_H = (int) ac_RatingH.getRating();



                    if (!validCourse(ac_Course)) {
                        if (!ac_Course.matches(".*\\d.*")) {
                            acCourseName.setError("Invalid Course Number!");
                        }
                    }
                    if (!validComment(ac_Comment))
                        acComment.setError("Invalid Comment!");


                    else{

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        alertDialogBuilder.setTitle("Are you sure?");

                        alertDialogBuilder
                                .setMessage("Click yes to submit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        submitComment();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        alertDialog.show();

                    }

                    break;
                case R.id.ac_Cancel_B:

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setTitle("Are you sure?");

                    alertDialogBuilder
                            .setMessage("Click yes to cancel!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cancelComment();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                    break;
            }

        }
    };


    // Functions below are for testing, will update later.

    private boolean validComment(String newComment){

        if (newComment.isEmpty() || newComment == null)
            return false;

        String replace = newComment.replace(" ","");

        int countSpace = newComment.length() - replace.length();

      //  if(countSpace > (int) 0..25 * newComment.length())
     //       return false;

      //  else
            return true;

    }

    private boolean validCourse(String newCourse){
        newCourse = newCourse.toLowerCase();
        newCourse = this.addSpace(newCourse);
        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        query.whereEqualTo("CourseName", newCourse);
        List<Course> course = new ArrayList<Course>();
        try{
            course = query.find();
        }
        catch (ParseException e){
            Log.e("validCourseError", e.getMessage());
        }

        if (newCourse.isEmpty() || newCourse == null)
            return false;


        else if(course.size() <= 0){
            return false;
        }

        else return true;
    }

    private String addSpace(String newCourse){

        char currentChar;
        for(int i = 0; i < newCourse.length(); i++){
            currentChar = newCourse.charAt(i);
            if(i > 0 && Character.isDigit(currentChar)){
                if(!((Character)newCourse.charAt(i - 1)).equals(' ')){
                    newCourse = newCourse.substring(0, i) + " " + newCourse.substring(i, newCourse.length());
                }
                break;
            }
        }

        return newCourse;
    }

    private void submitComment(){
        String ac_Course, ac_Comment;
        int ac_C, ac_E, ac_H;

        ac_Course = acCourseName.getText().toString().toLowerCase();
        ac_Course = addSpace(ac_Course);
        ac_Comment = acComment.getText().toString();

        ac_C = (int) ac_RatingC.getRating();
        ac_E = (int) ac_RatingE.getRating();
        ac_H = (int) ac_RatingH.getRating();

        if (!validCourse(ac_Course)) {
            Course newCourse = new Course();
            newCourse.setCourseName(ac_Course.toLowerCase());
            try {
                newCourse.save();
            } catch (ParseException e) {
                Log.e("failed to create course", e.getMessage());
            }
        }
        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        query.whereEqualTo("CourseName", ac_Course);
        Course course = new Course();
        try{
            course = query.getFirst();
        }
        catch (ParseException e){
            Log.e("validCourseError", e.getMessage());
        }
        if(!course.getProfessors().toString().contains(prof.getObjectId())) {
            course.addProfToCourse(prof.getObjectId());
        }
        prof.addRating(ac_C, ac_E, ac_H);
        prof.addComment(ac_Comment, TextParser.convertToUpperCase(ac_Course), ParseUser.getCurrentUser().getObjectId(), ac_C, ac_E, ac_H);
        BackToProfPage();
    }

    private void cancelComment(){
        BackToProfPage();

    }

    public void BackToProfPage() {
        Intent intent = null;
        intent = new Intent(getBaseContext(), ProfPage.class);
        intent.putExtra("profID", prof.getObjectId());
        finish(); //end current activity
        if(intent != null)
            startActivity(intent);
      //  Intent intent = new Intent(AddComment.this, ProfPage.class);
      //  intent.putExtra("profID", profID);
      //  startActivity(intent);
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
        Intent intent = new Intent(AddComment.this, AddComment.class);
        finish();
        startActivity(intent);
    }

    public void logout() {
        Intent intent = new Intent(AddComment.this, LoginActivity.class);
        if(ParseUser.getCurrentUser() != null) {
            Log.d("Test123","Logging out");
            ParseUser.logOut();
        }
        finish();
        startActivity(intent);
    }

}