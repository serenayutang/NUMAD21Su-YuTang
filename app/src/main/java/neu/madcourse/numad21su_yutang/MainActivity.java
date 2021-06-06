package neu.madcourse.numad21su_yutang;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutBtn = findViewById(R.id.aboutBtn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Name: Yu Tang;\nEmail: tang.y@northeastern.edu",
                        Toast.LENGTH_LONG).show();
            }
        });
        Button launcherBtn = findViewById(R.id.launcher);
        launcherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSixBtns();
            }
        });

        Button linkCollector = findViewById(R.id.linkCollector);
        linkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkCollectorIntent = new Intent(getApplicationContext(),
                        LinkCollectorActivity.class);
                startActivity(linkCollectorIntent);
            }
        });
    }

    private void openSixBtns() {
        Intent intent = new Intent(this, SixBtns.class);
        startActivity(intent);
    }
}