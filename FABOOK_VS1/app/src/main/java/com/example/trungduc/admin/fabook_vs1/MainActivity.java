package com.example.trungduc.admin.fabook_vs1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvKQTimKiem;
    ArrayList <String> arrayCourse;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvKQTimKiem = (ListView) findViewById(R.id.listviewKQTimKiem);

        arrayCourse = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCourse);

        lvKQTimKiem.setAdapter(adapter);

        new ReadJSON().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json");  //link respond from server
    }

    private  class ReadJSON extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL (strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while ((line= bufferedReader.readLine()) != null ){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject object = new JSONObject(s);

                JSONArray array = object.getJSONArray("list");

                for(int i=0 ; i<array.length() ; i++){

                    JSONObject objectKQ = array.getJSONObject(i);

                    String tieude = objectKQ.getString("tieuDe");

                    String namxuatban = objectKQ.getString("namXuatBan");

                    String tacgia = objectKQ.getString("tacGia");

                    String theloai = objectKQ.getString("theloai");

                    //Toast.makeText(MainActivity.this, ten , Toast.LENGTH_LONG).show();
                    arrayCourse.add( (i+1) + ". " + "Tiêu đề sách: " + tieude + "\n Năm xuất bản: " + namxuatban + "\n tacgia: " + tacgia + "\n Thể loại: " + theloai + "    --------");

                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}