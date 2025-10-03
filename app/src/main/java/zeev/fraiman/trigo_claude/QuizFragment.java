package zeev.fraiman.trigo_claude;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends Fragment {

    private TextView questionText, timerText, progressText, resultText;
    private LinearLayout answersContainer, difficultyContainer;
    private Button startQuizButton, nextQuestionButton, restartButton;
    private ProgressBar progressBar;

    private List<QuizQuestion> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private final int totalQuestions = 10;
    private CountDownTimer timer;
    private boolean quizInProgress = false;
    private int selectedDifficultyIndex = 1; // 0: Easy, 1: Medium, 2: Hard

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initializeViews(view);
        setupDifficultySelection();
        setupClickListeners();

        return view;
    }

    private void initializeViews(View view) {
        questionText = view.findViewById(R.id.question_text);
        timerText = view.findViewById(R.id.timer_text);
        progressText = view.findViewById(R.id.progress_text);
        resultText = view.findViewById(R.id.result_text);
        answersContainer = view.findViewById(R.id.answers_container);
        difficultyContainer = view.findViewById(R.id.difficulty_container);
        startQuizButton = view.findViewById(R.id.start_quiz_button);
        nextQuestionButton = view.findViewById(R.id.next_question_button);
        restartButton = view.findViewById(R.id.restart_button);
        progressBar = view.findViewById(R.id.progress_bar);

        // Initial state
        hideQuizElements();
        showStartScreen();
    }

    private void setupDifficultySelection() {
        String[] difficulties = getResources().getStringArray(R.array.quiz_difficulties);
        difficultyContainer.removeAllViews(); // Clear previous buttons if any

        for (int i = 0; i < difficulties.length; i++) {
            Button difficultyButton = new Button(getContext());
            difficultyButton.setText(difficulties[i]);
            difficultyButton.setTextSize(14);
            difficultyButton.setPadding(16, 12, 16, 12);

            final int difficultyIndex = i;
            difficultyButton.setOnClickListener(v -> {
                selectedDifficultyIndex = difficultyIndex;
                highlightSelectedDifficulty(difficultyButton);
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            );
            params.setMargins(4, 0, 4, 0);
            difficultyButton.setLayoutParams(params);

            difficultyContainer.addView(difficultyButton);
        }
        // Set initial selection
        highlightSelectedDifficulty((Button)difficultyContainer.getChildAt(selectedDifficultyIndex));
    }

    private void highlightSelectedDifficulty(Button selectedButton) {
        for (int i = 0; i < difficultyContainer.getChildCount(); i++) {
            difficultyContainer.getChildAt(i).setSelected(false);
        }
        selectedButton.setSelected(true);
    }

    private void setupClickListeners() {
        startQuizButton.setOnClickListener(v -> startQuiz());
        nextQuestionButton.setOnClickListener(v -> nextQuestion());
        restartButton.setOnClickListener(v -> restartQuiz());
    }

    private void prepareQuestions() {
        questions = new ArrayList<>();
        List<QuizQuestion> questionPool = new ArrayList<>();

        List<QuizQuestion> easyQuestions = createEasyQuestions();
        List<QuizQuestion> mediumQuestions = createMediumQuestions();
        List<QuizQuestion> hardQuestions = createHardQuestions();

        // Select questions based on difficulty
        switch (selectedDifficultyIndex) {
            case 0: // Easy
                questionPool.addAll(easyQuestions);
                break;
            case 1: // Medium
                questionPool.addAll(easyQuestions);
                questionPool.addAll(mediumQuestions);
                break;
            case 2: // Hard
                questionPool.addAll(mediumQuestions);
                questionPool.addAll(hardQuestions);
                break;
        }
        Collections.shuffle(questionPool);
        questions = questionPool.subList(0, Math.min(totalQuestions, questionPool.size()));
    }

    private List<QuizQuestion> createEasyQuestions() {
        List<QuizQuestion> easy = new ArrayList<>();
        easy.add(new QuizQuestion(getString(R.string.q_easy_1), getResources().getStringArray(R.array.a_easy_1), 0));
        easy.add(new QuizQuestion(getString(R.string.q_easy_2), getResources().getStringArray(R.array.a_easy_2), 1));
        easy.add(new QuizQuestion(getString(R.string.q_easy_3), getResources().getStringArray(R.array.a_easy_3), 2));
        easy.add(new QuizQuestion(getString(R.string.q_easy_4), getResources().getStringArray(R.array.a_easy_4), 1));
        easy.add(new QuizQuestion(getString(R.string.q_easy_5), getResources().getStringArray(R.array.a_easy_5), 1));
        return easy;
    }

    private List<QuizQuestion> createMediumQuestions() {
        List<QuizQuestion> medium = new ArrayList<>();
        medium.add(new QuizQuestion(getString(R.string.q_medium_1), getResources().getStringArray(R.array.a_medium_1), 0));
        medium.add(new QuizQuestion(getString(R.string.q_medium_2), getResources().getStringArray(R.array.a_medium_2), 1));
        medium.add(new QuizQuestion(getString(R.string.q_medium_3), getResources().getStringArray(R.array.a_medium_3), 0));
        medium.add(new QuizQuestion(getString(R.string.q_medium_4), getResources().getStringArray(R.array.a_medium_4), 1));
        medium.add(new QuizQuestion(getString(R.string.q_medium_5), getResources().getStringArray(R.array.a_medium_5), 2));
        return medium;
    }

    private List<QuizQuestion> createHardQuestions() {
        List<QuizQuestion> hard = new ArrayList<>();
        hard.add(new QuizQuestion(getString(R.string.q_hard_1), getResources().getStringArray(R.array.a_hard_1), 0));
        hard.add(new QuizQuestion(getString(R.string.q_hard_2), getResources().getStringArray(R.array.a_hard_2), 0));
        hard.add(new QuizQuestion(getString(R.string.q_hard_3), getResources().getStringArray(R.array.a_hard_3), 2));
        hard.add(new QuizQuestion(getString(R.string.q_hard_4), getResources().getStringArray(R.array.a_hard_4), 0));
        hard.add(new QuizQuestion(getString(R.string.q_hard_5), getResources().getStringArray(R.array.a_hard_5), 2));
        return hard;
    }

    private void startQuiz() {
        prepareQuestions();
        currentQuestionIndex = 0;
        correctAnswers = 0;
        quizInProgress = true;

        hideStartScreen();
        showQuizElements();
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        QuizQuestion question = questions.get(currentQuestionIndex);
        questionText.setText(question.question);

        answersContainer.removeAllViews();

        // Create answer buttons
        for (int i = 0; i < question.answers.length; i++) {
            Button answerButton = new Button(getContext());
            answerButton.setText(question.answers[i]);
            answerButton.setTextSize(12);
            answerButton.setPadding(12, 16, 12, 16);

            final int answerIndex = i;
            answerButton.setOnClickListener(v -> selectAnswer(answerIndex));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 4, 8, 4);
            answerButton.setLayoutParams(params);

            answersContainer.addView(answerButton);
        }

        updateProgress();
        startTimer();
    }

    private void selectAnswer(int selectedIndex) {
        if (!quizInProgress) return;

        stopTimer();

        QuizQuestion question = questions.get(currentQuestionIndex);
        boolean isCorrect = selectedIndex == question.correctAnswerIndex;

        // Highlight correct and incorrect answers
        for (int i = 0; i < answersContainer.getChildCount(); i++) {
            Button button = (Button) answersContainer.getChildAt(i);
            button.setEnabled(false);

            if (i == question.correctAnswerIndex) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            } else if (i == selectedIndex && !isCorrect) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            }
        }

        if (isCorrect) {
            correctAnswers++;
            Toast.makeText(getContext(), R.string.correct_answer_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.incorrect_answer_toast_quiz, Toast.LENGTH_SHORT).show();
        }

        nextQuestionButton.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        nextQuestionButton.setEnabled(false);
        displayCurrentQuestion();
    }

    private void startTimer() {
        stopTimer(); // Ensure any existing timer is cancelled
        long timePerQuestion = 30000; // 30 seconds
        timer = new CountDownTimer(timePerQuestion, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerText.setText(getString(R.string.time_format, seconds));

                if (seconds <= 5) {
                    timerText.setTextColor(Color.RED);
                } else {
                    timerText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onFinish() {
                timeOut();
            }
        };
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void timeOut() {
        Toast.makeText(getContext(), R.string.time_up_toast, Toast.LENGTH_SHORT).show();

        // Show the correct answer
        QuizQuestion question = questions.get(currentQuestionIndex);
        for (int i = 0; i < answersContainer.getChildCount(); i++) {
            Button button = (Button) answersContainer.getChildAt(i);
            button.setEnabled(false);

            if (i == question.correctAnswerIndex) {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }

        nextQuestionButton.setEnabled(true);
    }

    private void updateProgress() {
        progressText.setText(getString(R.string.question_progress_format, currentQuestionIndex + 1, questions.size()));
        progressBar.setMax(questions.size());
        progressBar.setProgress(currentQuestionIndex + 1);
    }

    private void finishQuiz() {
        quizInProgress = false;
        stopTimer();

        hideQuizElements();
        showResultsScreen();
    }

    private void showResultsScreen() {
        int percentage = (questions.size() > 0) ? (int) ((double) correctAnswers / questions.size() * 100) : 0;
        String[] grades = getResources().getStringArray(R.array.quiz_grades);
        String grade;

        if (percentage >= 90) grade = grades[0];
        else if (percentage >= 80) grade = grades[1];
        else if (percentage >= 70) grade = grades[2];
        else if (percentage >= 60) grade = grades[3];
        else grade = grades[4];

        String[] encouragements = getResources().getStringArray(R.array.quiz_encouragements);
        String encouragement;
        if (percentage >= 90) encouragement = encouragements[0];
        else if (percentage >= 70) encouragement = encouragements[1];
        else encouragement = encouragements[2];

        String resultMessage = getString(R.string.quiz_results_title) + "\n\n" +
                getString(R.string.quiz_results_score, correctAnswers, questions.size()) + "\n" +
                getString(R.string.quiz_results_percentage, percentage) + "\n" +
                getString(R.string.quiz_results_grade, grade) + "\n\n" +
                getString(R.string.quiz_results_message, encouragement);

        resultText.setText(resultMessage);
        resultText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
    }

    private void restartQuiz() {
        hideResultsScreen();
        showStartScreen();
    }

    private void showStartScreen() {
        difficultyContainer.setVisibility(View.VISIBLE);
        startQuizButton.setVisibility(View.VISIBLE);
    }

    private void hideStartScreen() {
        difficultyContainer.setVisibility(View.GONE);
        startQuizButton.setVisibility(View.GONE);
    }

    private void showQuizElements() {
        questionText.setVisibility(View.VISIBLE);
        answersContainer.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        nextQuestionButton.setVisibility(View.VISIBLE);
        nextQuestionButton.setEnabled(false);
    }

    private void hideQuizElements() {
        questionText.setVisibility(View.GONE);
        answersContainer.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        nextQuestionButton.setVisibility(View.GONE);
    }

    private void hideResultsScreen() {
        resultText.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private static class QuizQuestion {
        final String question;
        final String[] answers;
        final int correctAnswerIndex;

        QuizQuestion(String question, String[] answers, int correctAnswerIndex) {
            this.question = question;
            this.answers = answers;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }
}