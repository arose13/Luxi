package se.stephenro.luxi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.stephenro.luxi.views.FadeInTextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private static final int ANIMATION_DURATION = 1500;

    @Bind(R.id.welcomeTitle)
    FadeInTextView welcomeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        welcomeTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/OleoScript-Regular.ttf"));
        welcomeTitle.setIsVisible(false);
        welcomeTitle.setDuration(ANIMATION_DURATION - 300);
        welcomeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeTitle.toggle();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Go to next activity
        welcomeTitle.show();

        new CountDownTimer(ANIMATION_DURATION, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing
            }

            @Override
            public void onFinish() {
                Intent lightSensor = new Intent(WelcomeActivity.this, LightSensorActivity.class);
                startActivity(lightSensor);
                finish();
            }
        }.start();
    }
}
