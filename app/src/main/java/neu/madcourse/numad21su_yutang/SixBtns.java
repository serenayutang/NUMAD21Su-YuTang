package neu.madcourse.numad21su_yutang;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SixBtns extends AppCompatActivity {
    String pressed = "Pressed: Please Press A Button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_buttons);
        if (savedInstanceState != null){
            pressed = savedInstanceState.getString("show");
        }
        TextView show = findViewById(R.id.TextWhenPress);
        show.setText(pressed);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnA:
                TextView show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: A";
                show.setText(pressed);
                break;

            case R.id.btnB:
                show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: B";
                show.setText(pressed);
                break;

            case R.id.btnC:
                show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: C";
                show.setText(pressed);
                break;

            case R.id.btnD:
                show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: D";
                show.setText(pressed);
                break;

            case R.id.btnE:
                show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: E";
                show.setText(pressed);
                break;

            case R.id.btnF:
                show = findViewById(R.id.TextWhenPress);
                pressed = "Pressed: F";
                show.setText(pressed);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("show", pressed);
    }
}
