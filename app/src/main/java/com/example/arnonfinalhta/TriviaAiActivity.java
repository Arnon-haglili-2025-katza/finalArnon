package com.example.arnonfinalhta;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

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

    TriviaQuestion currentQ;

    RequestQueue queue;
    ArrayList<TriviaQuestion> questionBank = new ArrayList<>();

    MediaPlayer correctSound, wrongSound;

    CountDownTimer countDownTimer;
    int timeLeft = 30;

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

    private void startTimer() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        timeLeft = 30;
        timerProgress.setMax(30);
        timerProgress.setProgress(30);
        timerText.setText("⏳ זמן: 30");

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft--;
                timerText.setText("⏳ זמן: " + timeLeft);
                timerProgress.setProgress(timeLeft);
            }

            @Override
            public void onFinish() {
                if (!isAnswered) {
                    isAnswered = true;

                    Toast.makeText(
                            TriviaAiActivity.this,
                            "⏰ הזמן נגמר!",
                            Toast.LENGTH_SHORT
                    ).show();

                    new Handler().postDelayed(
                            TriviaAiActivity.this::nextQuestion,
                            1000
                    );
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

    private void initQuestions() {

        questionBank.add(new TriviaQuestion("באיזה שנה נוסדה הפועל תל אביב?",
                new String[]{"1900", "1923", "1935"}, 1));

        questionBank.add(new TriviaQuestion("איזה צבעים מייצגים את הפועל?",
                new String[]{"אדום ולבן", "צהוב וכחול", "ירוק"}, 0));

        questionBank.add(new TriviaQuestion("איפה משחקת הפועל?",
                new String[]{"טדי", "בלומפילד", "סמי עופר"}, 1));

        questionBank.add(new TriviaQuestion("כמה אליפויות יש להפועל?",
                new String[]{"5", "13", "20"}, 1));

        questionBank.add(new TriviaQuestion("איזה קבוצה יריבה של הפועל?",
                new String[]{"מכבי תל אביב", "בית\"ר", "חיפה"}, 0));

        questionBank.add(new TriviaQuestion("איזה צבע לא שייך להפועל?",
                new String[]{"אדום", "לבן", "צהוב"}, 2));
    }

    private void nextQuestion() {

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

        int index = new Random().nextInt(questionBank.size());
        currentQ = questionBank.get(index);

        showQuestion();
    }

    private void getNewQuestionFromAI() {
        loadLocalQuestion();
    }

    private void showQuestion() {

        resetButtons();

        questionText.setText(currentQ.question);
        option1.setText(currentQ.options[0]);
        option2.setText(currentQ.options[1]);
        option3.setText(currentQ.options[2]);

        isAnswered = false;

        startTimer();
    }

    public void checkAnswer(View view) {

        if (isAnswered) return;
        isAnswered = true;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

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
            correctSound.start();
            animateButton(btn, true);

        } else {

            wrongSound.start();
            animateButton(btn, false);
            animateButton(correctBtn, true);
        }

        updateScore();
        saveScore();

        new Handler().postDelayed(this::nextQuestion, 1200);
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
                android.content.res.ColorStateList.valueOf(defaultColor));

        option2.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(defaultColor));

        option3.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(defaultColor));
    }

    private void endGame() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        saveScore();

        Toast.makeText(this,
                "🏆 סיימת! ניקוד: " + score,
                Toast.LENGTH_LONG).show();

        score = 0;
        currentQuestion = 0;

        updateScore();
        nextQuestion();
    }
}