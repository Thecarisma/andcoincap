package gig.com.andcoincap.base;

/**
 * Created by Thecarisma on 25-Oct-17.
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    static InputStream is = null;
    static JSONObject jsonObj ;
    static String json = "";

    // default no argument constructor for jsonpaser class
    public JSONParser() {

    }


    public JSONObject getJSONFromUrl(final String url) {

        // Making HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // Executing POST request & storing the response from server  locally.
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {


            // Create a BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            // Declaring string builder
            StringBuilder str = new StringBuilder();
            //  string to store the JSON object.
            String strLine = null;

            // Building while we have string !equal null.
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }

            // Close inputstream.
            is.close();
            // string builder data conversion  to string.
            json = str.toString();
        } catch (Exception e) {
            Log.e("Error", " something wrong with converting result " + e.toString());
        }

        // Try block used for pasrseing String to a json object
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("json Parsering", "" + e.toString());
        }

        // Returning json Object.
        return jsonObj;

    }



    public JSONObject makeHttpRequest(String url, String method,
                                      List<BasicNameValuePair> params) {

        // Make HTTP request
        try {

            // checking request method
            if(method == "POST"){

                // now defaultHttpClient object
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (IOException e) {
            Log.e("JSONPARSER", "Exception: " + e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder str = new StringBuilder();
            String strLine = null;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            is.close();
            json = str.toString();
        } catch (Exception e) {
            Log.e("JSONPARSER", "Exception: " + e.getMessage());
        }

        // now will try to parse the string into JSON object
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSONPARSER", "Exception: " + e.getMessage());
        }


        return jsonObj;

    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (Exception e) {
            Log.e("JSONPARSER", "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e("JSONPARSER", "Exception: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e("JSONPARSER", "Exception: " + e.getMessage());
            }
        }
        return sb.toString();
    }

}