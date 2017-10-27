package sigma.scsapp.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sigma.scsapp.R;
import sigma.scsapp.model.User;


/**
 * Created by lucianahaugen on 24/10/17.
 */

public class HttpActivity extends AppCompatActivity{

    final String API_USERS = "http://10.0.2.2:8080/api/csa/users/";
    Button btnGetApi;
    User activeUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        btnGetApi = (Button) findViewById(R.id.btnGetApi);
        btnGetApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoGetUser().execute();
            }
        });


    }

    class DoGetUser extends AsyncTask<Void, Void, Void>{

        //String content;
        String result;
        String error;
        String data;
        TextView serverDataReceived = (TextView)findViewById(R.id.serverDataReceived);
        TextView showParsedJson = (TextView)findViewById(R.id.showParsedJson);
        EditText userInput = (EditText)findViewById(R.id.userInput);
        ProgressDialog progressDialog = new ProgressDialog(HttpActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait");
            progressDialog.show();

            try {
                data += "&" + URLEncoder.encode("data", "UTF-8") + "-" + userInput.getText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(API_USERS+activeUser.getUserId());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                int byteCharacter;
                //String result = "";
                while ((byteCharacter = inputStream.read()) != -1){
                    result += (char)byteCharacter;
                }
                Log.d("json api", result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if (error != null) {
                serverDataReceived.setText("Error " + error);
            } else {
                serverDataReceived.setText(result); //content

                String output = "";
                JSONObject jsonResponse;

                try {
                    jsonResponse = new JSONObject(result); //content
                    JSONArray jsonArray = jsonResponse.optJSONArray("Android");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        String userName = child.getString("userName");
                        String userId = child.getString("userId");
                        String userImage = child.getString("userImage");

                        output = "User name " + userName + System.getProperty("line.separator") +
                                userId + System.getProperty("line.separator") + userId;

                        output += System.getProperty("line.separator");
                    }
                    showParsedJson.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
