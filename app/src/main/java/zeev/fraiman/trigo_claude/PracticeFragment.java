package zeev.fraiman.trigo_claude;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.Random;

public class PracticeFragment extends Fragment {

    private LinearLayout exerciseContainer;
    private TextView questionText, hintText, scoreText;
    private EditText answerInput;
    private Button submitButton, nextButton, hintButton;
    private TrigonometryCircleView graphView;

    private int currentScore = 0;
    private int totalQuestions = 0;
    private Random random = new Random();

    private String[] exerciseTypes;

    private Exercise currentExercise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        exerciseTypes = getResources().getStringArray(R.array.practice_exercise_types);

        initializeViews(view);
        setupExerciseTypes();
        generateNewExercise();

        return view;
    }

    private void initializeViews(View view) {
        exerciseContainer = view.findViewById(R.id.exercise_container);
        questionText = view.findViewById(R.id.question_text);
        hintText = view.findViewById(R.id.hint_text);
        scoreText = view.findViewById(R.id.score_text);
        answerInput = view.findViewById(R.id.answer_input);
        submitButton = view.findViewById(R.id.submit_button);
        nextButton = view.findViewById(R.id.next_button);
        hintButton = view.findViewById(R.id.hint_button);
        graphView = view.findViewById(R.id.graph_view);

        setupClickListeners();
        updateScore();
    }

    private void setupClickListeners() {
        submitButton.setOnClickListener(v -> checkAnswer());
        nextButton.setOnClickListener(v -> generateNewExercise());
        hintButton.setOnClickListener(v -> showHint());
    }

    private void setupExerciseTypes() {
        for (String exerciseType : exerciseTypes) {
            Button typeButton = new Button(getContext());
            typeButton.setText(exerciseType);
            typeButton.setTextSize(12);
            typeButton.setPadding(12, 8, 12, 8);

            typeButton.setOnClickListener(v -> {
                generateExerciseOfType(exerciseType);
                highlightSelectedType(typeButton);
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 2, 4, 2);
            typeButton.setLayoutParams(params);

            exerciseContainer.addView(typeButton);
        }
    }

    private void highlightSelectedType(Button selectedButton) {
        for (int i = 0; i < exerciseContainer.getChildCount(); i++) {
            View child = exerciseContainer.getChildAt(i);
            if (child instanceof Button) {
                child.setSelected(false);
            }
        }
        selectedButton.setSelected(true);
    }

    private void generateNewExercise() {
        int exerciseTypeIndex = random.nextInt(exerciseTypes.length);
        generateExerciseOfType(exerciseTypes[exerciseTypeIndex]);
    }

    private void generateExerciseOfType(String type) {
        if (type.equals(exerciseTypes[0])) { // Calculating Values
            generateValueCalculationExercise();
        } else if (type.equals(exerciseTypes[1])) { // Solving Equations
            generateEquationExercise();
        } else if (type.equals(exerciseTypes[2])) { // Graphing
            generateGraphExercise();
        } else if (type.equals(exerciseTypes[3])) { // Applying Identities
            generateIdentityExercise();
        }
        resetUI();
    }

    private void generateValueCalculationExercise() {
        int[] commonAngles = {0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};
        int angle = commonAngles[random.nextInt(commonAngles.length)];
        String[] functions = {"sin", "cos", "tan"};
        String function = functions[random.nextInt(functions.length)];

        String question = getString(R.string.question_calculate_value, function, angle);
        double correctAnswer = calculateTrigValue(function, angle);
        String hint = getString(R.string.hint_use_unit_circle, angle);

        currentExercise = new Exercise(question, correctAnswer, hint, "calculation");
        displayExercise();
    }

    private void generateEquationExercise() {
        String[] equations = getResources().getStringArray(R.array.practice_equation_questions);
        double[] answers = {30, 120, 45, 60}; // Simplified answers for the example

        int index = random.nextInt(equations.length);
        String question = equations[index];
        double correctAnswer = answers[index];
        String hint = getString(R.string.hint_use_inverse_functions);

        currentExercise = new Exercise(question, correctAnswer, hint, "equation");
        displayExercise();
    }

    private void generateGraphExercise() {
        String[] functions = {"y = sin(x)", "y = cos(x)", "y = tan(x)"};
        String function = functions[random.nextInt(functions.length)];

        String question = getString(R.string.question_determine_period, function);
        double correctAnswer = function.contains("tan") ? 180 : 360;
        String hint = getString(R.string.hint_period_definition);

        currentExercise = new Exercise(question, correctAnswer, hint, "graph");
        displayExercise();

        // Show graph
        if (graphView != null) {
            graphView.setAngle(0);
            graphView.setVisibility(View.VISIBLE);
        }
    }

    private void generateIdentityExercise() {
        String[] identities = getResources().getStringArray(R.array.practice_identity_questions);
        double[] answers = {0.64, 4.0/3.0, 0}; // 0 for tan(x)
        String[] hints = getResources().getStringArray(R.array.practice_identity_hints);

        int index = random.nextInt(identities.length);
        currentExercise = new Exercise(identities[index], answers[index], hints[index], "identity");
        displayExercise();
    }

    private double calculateTrigValue(String function, int angle) {
        double radians = Math.toRadians(angle);
        switch (function) {
            case "sin":
                return Math.sin(radians);
            case "cos":
                return Math.cos(radians);
            case "tan":
                return Math.tan(radians);
            default:
                return 0;
        }
    }

    private void displayExercise() {
        questionText.setText(currentExercise.question);
        hintText.setVisibility(View.GONE);
    }

    private void checkAnswer() {
        String userInput = answerInput.getText().toString().trim();

        if (userInput.isEmpty()) {
            Toast.makeText(getContext(), R.string.enter_answer_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double userAnswer = Double.parseDouble(userInput);
            boolean isCorrect = Math.abs(userAnswer - currentExercise.correctAnswer) < 0.01;

            totalQuestions++;
            if (isCorrect) {
                currentScore++;
                Toast.makeText(getContext(), R.string.correct_answer_toast, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),
                        getString(R.string.incorrect_answer_toast, currentExercise.correctAnswer),
                        Toast.LENGTH_LONG).show();
            }

            updateScore();
            submitButton.setEnabled(false);
            nextButton.setEnabled(true);

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_numeric_value_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void showHint() {
        hintText.setText("💡 " + currentExercise.hint);
        hintText.setVisibility(View.VISIBLE);
    }

    private void resetUI() {
        answerInput.setText("");
        hintText.setVisibility(View.GONE);
        submitButton.setEnabled(true);
        nextButton.setEnabled(false);

        if (graphView != null && currentExercise != null && !currentExercise.type.equals("graph")) {
            graphView.setVisibility(View.GONE);
        }
    }

    private void updateScore() {
        if (totalQuestions > 0) {
            int percentage = (int) ((double) currentScore / totalQuestions * 100);
            scoreText.setText(getString(R.string.score_format, currentScore, totalQuestions, percentage));
        } else {
            scoreText.setText(R.string.initial_score);
        }
    }

    private static class Exercise {
        String question;
        double correctAnswer;
        String hint;
        String type;

        Exercise(String question, double correctAnswer, String hint, String type) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.hint = hint;
            this.type = type;
        }
    }
}
