package apextechies.singhmehandi.component.activity.order;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Download_web extends AsyncTask<String, Void, String> {
    private String response = "";
    private OnTaskCompleted listener;
    private boolean isGet = true;
    private boolean isAuth = false;
    private JSONObject data;


    private static HttpClient mHttpClient;
    public static final int HTTP_TIMEOUT = 60 * 1000; // milliseconds

    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    public Download_web(OnTaskCompleted listener) {
        this.listener = listener;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost("https://ssm.smocglobal.com/androidApp/salesOrderAPI.php");
            request.addHeader("Content-Type:", "application/json");

            request.setEntity(new StringEntity(data.toString()));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while (true) {
                try {
                    if (!((line = in.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line + NL);
            }
            in.close();


            String result = sb.toString();
            return result;
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String result) {

        if (!result.equals("")) {
            if (listener != null)
                listener.onTaskCompleted(result);
        } else {

            if (listener != null)
                listener.onTaskCompleted("");
        }

    }


}