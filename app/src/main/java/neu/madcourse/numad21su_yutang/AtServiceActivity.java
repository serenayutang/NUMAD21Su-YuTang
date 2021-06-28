package neu.madcourse.numad21su_yutang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

public class AtServiceActivity extends AppCompatActivity {
    TextView textViewNewCases;
    private Handler handler;
    String requestUrl = "https://api.covidtracking.com/v1/states/current.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_service);
        textViewNewCases = findViewById(R.id.textViewNewCase);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        handler = new Handler();
    }

    public void runService(View view) {
        serviceRunnable serviceRunnable = new serviceRunnable();
        new Thread(serviceRunnable).start();
    }

    class serviceRunnable implements Runnable {
        @Override
        public void run() {
            handler.post(() -> {
                try {
                    HttpURLConnection httpClient =
                            (HttpURLConnection) new URL(requestUrl).openConnection();
                    // optional default is GET
                    httpClient.setRequestMethod("GET");
                    //add request header
                    httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
                    int responseCode = httpClient.getResponseCode();
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(httpClient.getInputStream()))) {

                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        String numberNewCases = getNumberNewCases(response.toString());
                        textViewNewCases.setText(numberNewCases);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });
        }

        private String getNumberNewCases(String response) throws JSONException {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = (JSONObject) jsonArray.get(5);
            String newCases = jsonObject.getString("positiveIncrease");
            return newCases;
        }
    }
}
