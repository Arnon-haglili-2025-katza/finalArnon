package com.example.arnonfinalhta;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class AiActivity extends AppCompatActivity {

    EditText input;
    TextView output;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        input = findViewById(R.id.inputQuestion);
        output = findViewById(R.id.aiAnswer);
        queue = Volley.newRequestQueue(this);
    }

    public void askAI(View view) {
        String userQuestion = input.getText().toString();

        String url = "https://api.openai.com/v1/chat/completions";

        try {
            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject msg = new JSONObject();
            msg.put("role", "user");
            msg.put("content", "ענה בעברית על שאלה על הפועל תל אביב: " + userQuestion);
            messages.put(msg);

            body.put("messages", messages);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    body,
                    response -> {
                        try {
                            String answer = response
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            output.setText(answer);
                        } catch (Exception e) {
                            output.setText("שגיאה בפענוח תשובה");
                        }
                    },
                    error -> output.setText("שגיאה בחיבור ל-AI")
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
            output.setText("שגיאה ביצירת הבקשה");
        }
    }
}
