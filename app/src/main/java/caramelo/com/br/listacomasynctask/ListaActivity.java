package caramelo.com.br.listacomasynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import caramelo.com.br.listacomasynctask.adapter.AndroidAdapter;
import caramelo.com.br.listacomasynctask.model.Android;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AndroidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AndroidAdapter();
        adapter.setOnItemClickListener(new AndroidAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Android android) {
                Intent intent = new Intent(ListaActivity.this, DetalheActivity.class);
                intent.putExtra("android", android);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        new AndroidListAsync(this) {
            @Override
            protected void onPostExecute(List<Android> list) {
                super.onPostExecute(list);
                adapter.update(list);
            }
        }.execute();
    }

    private static class AndroidListAsync extends AsyncTask<String, String, List<Android>> {

        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        private AlertDialog loading;

        public AndroidListAsync(Context context) {
            loading = new ProgressDialog.Builder(context)
                    .setMessage("Loading...")
                    .setCancelable(false)
                    .create();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected List<Android> doInBackground(String... strings) {
            String response = request();
            return readResponse(response);
        }

        @Override
        protected void onPostExecute(List<Android> list) {
            super.onPostExecute(list);
            loading.hide();
        }

        private String request() {
            URL url;
            HttpURLConnection conn;
            try {
                url = new URL("http://www.mocky.io/v2/58af1fb21000001e1cc94547");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "";
            }
            try {
                // Setup HttpURLConnection class to send and receive data
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return "";
            }

            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                } else {
                    return "";
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                conn.disconnect();
            }
        }

        private List<Android> readResponse(String response) {
            List<Android> list = new ArrayList<>();
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonAndroidList = jsonResponse.getJSONArray("android");
                for (int i = 0; i < jsonAndroidList.length(); i++) {
                    JSONObject jsonAndroid = jsonAndroidList.getJSONObject(i);
                    Android android = new Android(jsonAndroid);
                    list.add(android);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}
