package lingchuang.lesson_0812_sql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {
    Spinner sp1;
    EditText et1,et2,et3;
    Button b1,b2,b3,b4,b5;
    ArrayAdapter<String> adapter;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cur;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp1=(Spinner)findViewById(R.id.spinner);
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        context=this;
        dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
        MyDBQuery();

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] field = {
                        "_id", "name", "tel", "email"
                };
                String user_name = parent.getSelectedItem().toString();
                cur = db.query("users", field, "name=?", new String[]{user_name}, null, null, null);
                cur.moveToFirst();
                if (cur.getCount() > 0) {
                    et1.setText(cur.getString(1));
                    et2.setText(cur.getString(2));
                    et3.setText(cur.getString(3));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, tel, email;
                name = et1.getText().toString();
                tel = et2.getText().toString();
                email = et3.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("name", name);
                cv.put("tel", tel);
                cv.put("email", email);
                long num = db.insert("users", null, cv);
                Toast.makeText(context, "add Success!", Toast.LENGTH_SHORT).show();
                MyDBQuery();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,tel,email;
                name=et1.getText().toString();
                tel=et2.getText().toString();
                email=et3.getText().toString();
                ContentValues cv=new ContentValues();
                cv.put("name",name);
                cv.put("tel", tel);
                cv.put("email", email);
                int num=db.update("users", cv, "name=?", new String[]{name});
                Toast.makeText(context,"update Success!",Toast.LENGTH_SHORT).show();
                MyDBQuery();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Deleting this record.You sure?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=et1.getText().toString();
                                int num = db.delete("users", "name=?",new String[]{name});
                                Toast.makeText(context, "delete Success", Toast.LENGTH_SHORT).show();
                                MyDBQuery();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] field = {
                        "_id", "name", "tel", "email"
                };
                String user_name = et1.getText().toString();
                cur = db.query("users", field, "name=?", new String[]{user_name}, null, null, null);
                cur.moveToFirst();
                if (cur.getCount() > 0) {
                    et1.setText(cur.getString(1));
                    et2.setText(cur.getString(2));
                    et3.setText(cur.getString(3));
                }else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete")
                            .setMessage("No record!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }
        });
    }

    public void MyDBQuery(){
        cur=db.rawQuery("select name from users",null);
        cur.moveToFirst();
        String[] tmp=null;
        if(cur.getCount()>0){
            tmp=new String[cur.getCount()];
            for(int i=0;i<cur.getCount();i++){
                tmp[i]=cur.getString(0);
                cur.moveToNext();
            }
            adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,tmp);
            sp1.setAdapter(adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
