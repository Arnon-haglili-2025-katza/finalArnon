package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class TriviaAiActivity extends BaseActivity  {

    TextView questionText;
    Button option1, option2, option3;
    String correctAnswer;
    RequestQueue queue;



    public void getNewQuestion(View view) {
        String url = "https://api.openai.com/v1/chat/completions";

        try {
            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");

            // ההנחיה ל-AI
            String prompt = "צור שאלה טריוויה על הפועל תל אביב עם 3 אפשרויות, סמן איזו נכונה. החזר JSON עם 'question', 'options' ו-'correct'";

            JSONObject msg = new JSONObject();
            msg.put("role", "user");
            msg.put("content", prompt);

            org.json.JSONArray messages = new org.json.JSONArray();
            messages.put(msg);
            body.put("messages", messages);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    body,
                    response -> {
                        try {
                            JSONObject choice = response
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message");

                            // הנחה: ה-AI מחזיר JSON בתוך content
                            JSONObject questionJson = new JSONObject(choice.getString("content"));

                            questionText.setText(questionJson.getString("question"));
                            option1.setText(questionJson.getJSONArray("options").getString(0));
                            option2.setText(questionJson.getJSONArray("options").getString(1));
                            option3.setText(questionJson.getJSONArray("options").getString(2));
                            correctAnswer = questionJson.getString("correct");

                        } catch (Exception e) {
                            questionText.setText("שגיאה בקבלת השאלה");
                        }
                    },
                    error -> questionText.setText("שגיאה בחיבור ל-AI")
            ) {
                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Authorization", "Bearer API_KEY_שלך_כאן");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            questionText.setText("שגיאה ביצירת הבקשה");
        }
    }

    public void checkAnswer(View view) {
        Button btn = (Button) view;
        String selected = btn.getText().toString();

        if (selected.equals(correctAnswer)) {
            Toast.makeText(this, "✅ נכון!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "❌ לא נכון!", Toast.LENGTH_SHORT).show();
        }
    }
}
