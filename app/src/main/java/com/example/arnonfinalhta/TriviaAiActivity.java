package com.example.arnonfinalhta;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class TriviaAiActivity extends BaseActivity {

    TextView questionText, scoreText, timerText;
    Button option1, option2, option3, btnLeaderboard;
    ProgressBar timerProgress;

    int score = 0;
    int currentQuestion = 0;
    int totalQuestions = 10;

    boolean isAnswered = false;
    boolean isActivityActive = true;

    TriviaQuestion currentQ;

    RequestQueue queue;
    ArrayList<TriviaQuestion> questionBank = new ArrayList<>();

    MediaPlayer correctSound, wrongSound;

    CountDownTimer countDownTimer;
    int timeLeft = 30;

    Handler gameHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);

        View view = getLayoutInflater().inflate(
                R.layout.activity_trivia_ai,
                container,
                false
        );

        container.removeAllViews();
        container.addView(view);

        setupBottomNav(R.id.nav_trivia);

        questionText = view.findViewById(R.id.questionText);
        scoreText = view.findViewById(R.id.scoreText);
        timerText = view.findViewById(R.id.timerText);
        timerProgress = view.findViewById(R.id.timerProgress);

        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        btnLeaderboard = view.findViewById(R.id.btnLeaderboard);

        btnLeaderboard.setOnClickListener(v ->
                startActivity(new Intent(this, LeaderboardActivity.class))
        );

        queue = Volley.newRequestQueue(this);

        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);

        View.OnClickListener listener = this::checkAnswer;
        option1.setOnClickListener(listener);
        option2.setOnClickListener(listener);
        option3.setOnClickListener(listener);

        initQuestions();
        updateScore();
        nextQuestion();
    }

    private void updateScore() {
        scoreText.setText("נקודות: " + score);
    }

    private void initQuestions() {
        questionBank.clear();
        questionBank.addAll(TriviaQuestionBank.getQuestions());
    }

    private void stopGameTimers() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        if (gameHandler != null) {
            gameHandler.removeCallbacksAndMessages(null);
        }
    }

    private void startTimer() {

        stopGameTimers();

        if (!isActivityActive) return;

        timeLeft = 30;
        timerProgress.setMax(30);
        timerProgress.setProgress(30);
        timerText.setText("⏳ זמן: 30");

        countDownTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (!isActivityActive) {
                    cancel();
                    return;
                }

                timeLeft--;
                timerText.setText("⏳ זמן: " + timeLeft);
                timerProgress.setProgress(timeLeft);
            }

            @Override
            public void onFinish() {

                if (!isActivityActive) return;

                if (!isAnswered) {
                    isAnswered = true;

                    Toast.makeText(
                            TriviaAiActivity.this,
                            "⏰ הזמן נגמר!",
                            Toast.LENGTH_SHORT
                    ).show();

                    gameHandler.postDelayed(() -> {
                        if (isActivityActive) {
                            nextQuestion();
                        }
                    }, 1000);
                }
            }
        };

        countDownTimer.start();
    }

    private void saveScore() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) return;

        String uid = auth.getCurrentUser().getUid();
        String email = auth.getCurrentUser().getEmail();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid);

        ref.child("score").setValue(score);
        ref.child("email").setValue(email);
    }

    private void nextQuestion() {

        if (!isActivityActive) return;

        if (currentQuestion >= totalQuestions) {
            endGame();
            return;
        }

        currentQuestion++;

        if (Math.random() < 0.5) {
            getNewQuestionFromAI();
        } else {
            loadLocalQuestion();
        }
    }

    private void loadLocalQuestion() {

        if (!isActivityActive) return;

        if (questionBank.isEmpty()) {
            initQuestions();
        }

        if (questionBank.isEmpty()) return;

        int index = new Random().nextInt(questionBank.size());

        currentQ = questionBank.remove(index);

        showQuestion();
    }

    private void getNewQuestionFromAI() {
        loadLocalQuestion();
    }

    private void showQuestion() {

        if (!isActivityActive) return;
        if (currentQ == null) return;

        resetButtons();

        questionText.setText(currentQ.question);
        option1.setText(currentQ.options[0]);
        option2.setText(currentQ.options[1]);
        option3.setText(currentQ.options[2]);

        isAnswered = false;

        startTimer();
    }

    public void checkAnswer(View view) {

        if (!isActivityActive) return;
        if (isAnswered) return;
        if (currentQ == null) return;

        isAnswered = true;

        stopGameTimers();

        Button btn = (Button) view;

        int selected = -1;

        if (btn == option1) selected = 0;
        if (btn == option2) selected = 1;
        if (btn == option3) selected = 2;

        Button correctBtn = option1;

        if (currentQ.correctIndex == 1) correctBtn = option2;
        if (currentQ.correctIndex == 2) correctBtn = option3;

        if (selected == currentQ.correctIndex) {

            score += 10;

            if (correctSound != null) {
                correctSound.start();
            }

            animateButton(btn, true);

        } else {

            if (wrongSound != null) {
                wrongSound.start();
            }

            animateButton(btn, false);
            animateButton(correctBtn, true);
        }

        updateScore();
        saveScore();

        gameHandler.postDelayed(() -> {
            if (isActivityActive) {
                nextQuestion();
            }
        }, 1200);
    }

    private void animateButton(Button btn, boolean correct) {

        int color = correct ? getColor(R.color.green) : getColor(R.color.red_dark);

        btn.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );
    }

    private void resetButtons() {

        int defaultColor = getColor(R.color.red_main);

        option1.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(defaultColor)
        );

        option2.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(defaultColor)
        );

        option3.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(defaultColor)
        );
    }

    private void endGame() {

        if (!isActivityActive) return;

        stopGameTimers();

        saveScore();

        Toast.makeText(
                this,
                "🏆 סיימת! ניקוד: " + score,
                Toast.LENGTH_LONG
        ).show();

        score = 0;
        currentQuestion = 0;

        initQuestions();
        updateScore();

        if (isActivityActive) {
            nextQuestion();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        isActivityActive = false;
        stopGameTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isActivityActive = false;
        stopGameTimers();

        if (correctSound != null) {
            correctSound.release();
            correctSound = null;
        }

        if (wrongSound != null) {
            wrongSound.release();
            wrongSound = null;
        }
    }
}