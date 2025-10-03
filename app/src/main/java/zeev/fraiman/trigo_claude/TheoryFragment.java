package zeev.fraiman.trigo_claude;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;

public class TheoryFragment extends Fragment {

    private LinearLayout topicContainer;
    private TrigonometryCircleView circleView;
    private SeekBar angleSeekBar;
    private TextView angleValueText, sinValueText, cosValueText, tanValueText;
    private Button btnSin, btnCos, btnTan, btnCot, btnSec, btnCsc;

    private String[] topics;
    private String[] topicDescriptions;

    private int currentTopicIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory, container, false);

        // Load resources
        topics = getResources().getStringArray(R.array.theory_topics);
        topicDescriptions = getResources().getStringArray(R.array.theory_topic_descriptions);

        initializeViews(view);
        setupInteractiveElements();
        loadTopic(0);

        return view;
    }

    private void initializeViews(View view) {
        topicContainer = view.findViewById(R.id.topic_container);
        circleView = view.findViewById(R.id.circle_view);
        angleSeekBar = view.findViewById(R.id.angle_seekbar);
        angleValueText = view.findViewById(R.id.angle_value_text);
        sinValueText = view.findViewById(R.id.sin_value_text);
        cosValueText = view.findViewById(R.id.cos_value_text);
        tanValueText = view.findViewById(R.id.tan_value_text);

        btnSin = view.findViewById(R.id.btn_sin);
        btnCos = view.findViewById(R.id.btn_cos);
        btnTan = view.findViewById(R.id.btn_tan);
        btnCot = view.findViewById(R.id.btn_cot);
        btnSec = view.findViewById(R.id.btn_sec);
        btnCsc = view.findViewById(R.id.btn_csc);
    }

    private void setupInteractiveElements() {
        // Create buttons for topic selection
        for (int i = 0; i < topics.length; i++) {
            Button topicButton = new Button(getContext());
            topicButton.setText(topics[i]);
            topicButton.setTextSize(14);
            topicButton.setPadding(16, 12, 16, 12);

            final int topicIndex = i;
            topicButton.setOnClickListener(v -> loadTopic(topicIndex));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 4, 8, 4);
            topicButton.setLayoutParams(params);

            topicContainer.addView(topicButton);
        }

        // Setup interactive angle seekbar
        angleSeekBar.setMax(360);
        angleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTrigonometricValues(progress);
                if (circleView != null) {
                    circleView.setAngle(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Setup function buttons
        setupFunctionButtons();

        // Initial values
        updateTrigonometricValues(0);
    }

    private void setupFunctionButtons() {
        btnSin.setOnClickListener(v -> highlightFunction("sin"));
        btnCos.setOnClickListener(v -> highlightFunction("cos"));
        btnTan.setOnClickListener(v -> highlightFunction("tan"));
        btnCot.setOnClickListener(v -> highlightFunction("cot"));
        btnSec.setOnClickListener(v -> highlightFunction("sec"));
        btnCsc.setOnClickListener(v -> highlightFunction("csc"));
    }

    private void highlightFunction(String function) {
        // Reset all buttons
        resetFunctionButtons();

        // Highlight the selected function
        switch (function) {
            case "sin":
                btnSin.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("sin");
                break;
            case "cos":
                btnCos.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("cos");
                break;
            case "tan":
                btnTan.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("tan");
                break;
            case "cot":
                btnCot.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("cot");
                break;
            case "sec":
                btnSec.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("sec");
                break;
            case "csc":
                btnCsc.setSelected(true);
                if (circleView != null) circleView.setHighlightedFunction("csc");
                break;
        }
    }

    private void resetFunctionButtons() {
        btnSin.setSelected(false);
        btnCos.setSelected(false);
        btnTan.setSelected(false);
        btnCot.setSelected(false);
        btnSec.setSelected(false);
        btnCsc.setSelected(false);
        if (circleView != null) {
            circleView.setHighlightedFunction(null);
        }
    }

    private void loadTopic(int index) {
        currentTopicIndex = index;
        // Update UI with information about the selected topic
        // You can add a TextView here to display the topic description
    }

    private void updateTrigonometricValues(int angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        double sinValue = Math.sin(angleRadians);
        double cosValue = Math.cos(angleRadians);
        double tanValue = Math.tan(angleRadians);

        angleValueText.setText(getString(R.string.angle_format, angleDegrees));
        sinValueText.setText(getString(R.string.sin_format, sinValue));
        cosValueText.setText(getString(R.string.cos_format, cosValue));

        if (Math.abs(cosValue) < 0.001) {
            tanValueText.setText(R.string.tan_infinity);
        } else {
            tanValueText.setText(getString(R.string.tan_format, tanValue));
        }
    }
}
