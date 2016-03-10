package com.example.noa.laboratorio_81_2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by noa on 10/03/16.
 */
public class SigninActivity extends AsyncTask<String, String, String>{

    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;

    public SigninActivity (Context context, TextView statusField, TextView roleField, int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        byGetOrPost = flag;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String stream = null;
        JSONObject json = new JSONObject();
        if(byGetOrPost == 0){ //means by Get Method
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];
                String link = "http://noa2016.esy.es/laboratorio8/metodoGET.php? usuario="+username+"&clave="+password;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                if(response.getStatusLine().getStatusCode() == 200){
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        InputStream instream = entity.getContent();
                        String result= convertStreamToString(instream);
                        Log.d("result ****", String.valueOf((result)));
                        json.put("response_", new JSONObject(result));
                        instream.close();
                    }
                } else {
                    Log.d("result **** error", String.valueOf((0)));
                }
                return String.valueOf(json);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        } else {
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];
                String link="http://noa2016.esy.es/laboratorio8/metodoPOST.php";
                String data = URLEncoder.encode("usuario", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("clave", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr =
                        new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
                if(conn.getResponseCode() == 200){
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                    }
                    stream = sb.toString();
                    Log.d("result ****", String.valueOf((stream)));
                    json.put("response_", new JSONObject(stream));
                    conn.disconnect();
                }else {
// something
                }
                return String.valueOf(json);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    protected void onPostExecute(String result){
        this.statusField.setText("Correct!");
        String json_str = String.valueOf(result);
        try {
            JSONObject my_obj = new JSONObject(json_str);
            String response = my_obj.getString("response_");
            JSONObject response_ = new JSONObject(response);
            String data = response_.getString("data");
            Log.d("result", String.valueOf(data));
            this.roleField.setText(data);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}



















