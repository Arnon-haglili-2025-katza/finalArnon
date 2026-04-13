package com.example.arnonfinalhta;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class TriviaAiActivity extends BaseActivity {

    TextView questionText, scoreText;
    Button option1, option2, option3, btnLeaderboard;

    int score = 0;
    int currentQuestion = 0;
    int totalQuestions = 10;

    boolean isAnswered = false;

    TriviaQuestion currentQ;

    RequestQueue queue;
    ArrayList<TriviaQuestion> questionBank = new ArrayList<>();

    MediaPlayer correctSound, wrongSound;

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

    private void saveScore() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("score")
                .setValue(score);
    }

    private void initQuestions() {

        questionBank.add(new TriviaQuestion(
                "באיזה שנה נוסדה הפועל תל אביב?",
                new String[]{"1900", "1923", "1935"},
                1
        ));

        questionBank.add(new TriviaQuestion(
                "איזה צבעים מייצגים את הפועל תל אביב?",
                new String[]{"אדום ולבן", "צהוב וכחול", "ירוק"},
                0
        ));

        questionBank.add(new TriviaQuestion(
                "באיזה אצטדיון משחקת הפועל תל אביב?",
                new String[]{"טדי", "בלומפילד", "סמי עופר"},
                1
        ));
    }

    private void nextQuestion() {

        if (currentQuestion >= totalQuestions) {
            endGame();
            return;
        }

        currentQuestion++;

        if (Math.random() < 0.7) {
            loadLocalQuestion();
        } else {
            getNewQuestionFromAI();
        }
    }

    private void loadLocalQuestion() {
        int index = new Random().nextInt(questionBank.size());
        currentQ = questionBank.get(index);
        showQuestion();
    }

    private void getNewQuestionFromAI() {

        String url = "https://api.openai.com/v1/chat/completions";

        try {
            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");

            String prompt = "שאלת טריוויה על הפועל תל אביב עם 3 תשובות בלבד. JSON בלבד: {\"question\":\"...\",\"options\":[\"...\",\"...\",\"...\"],\"correctIndex\":0}";

            JSONObject msg = new JSONObject();
            msg.put("role", "user");
            msg.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(msg);
            body.put("messages", messages);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    body,

                    response -> {
                        try {
                            String content = response
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            JSONObject q = new JSONObject(content);

                            currentQ = new TriviaQuestion(
                                    q.getString("question"),
                                    new String[]{
                                            q.getJSONArray("options").getString(0),
                                            q.getJSONArray("options").getString(1),
                                            q.getJSONArray("options").getString(2)
                                    },
                                    q.getInt("correctIndex")
                            );

                            showQuestion();

                        } catch (Exception e) {
                            loadLocalQuestion();
                        }
                    },

                    error -> loadLocalQuestion()
            ) {
                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Authorization", "Bearer YOUR_API_KEY_HERE");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            loadLocalQuestion();
        }
    }

    private void showQuestion() {
        resetButtons();

        questionText.setText(currentQ.question);
        option1.setText(currentQ.options[0]);
        option2.setText(currentQ.options[1]);
        option3.setText(currentQ.options[2]);

        isAnswered = false;
    }

    public void checkAnswer(View view) {

        if (isAnswered) return;
        isAnswered = true;

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

        new Handler().postDelayed(() -> {
            nextQuestion();
        }, 1200);
    }

    private void animateButton(Button btn, boolean correct) {

        int color = correct ? getColor(R.color.green) : getColor(R.color.red_dark);

        btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(color));

        btn.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(150)
                .withEndAction(() ->
                        btn.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(150)
                );
    }

    private void resetButtons() {

        int defaultColor = getColor(R.color.red_main);

        option1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
        option2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
        option3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
    }

    private void endGame() {

        saveScore();

        Toast.makeText(this, "🏆 סיימת! ניקוד: " + score, Toast.LENGTH_LONG).show();

        score = 0;
        currentQuestion = 0;

        updateScore();
        nextQuestion();
    }
}