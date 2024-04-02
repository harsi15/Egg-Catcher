package edu.binghamton.eggcatcher;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {

    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String SCORE_KEY = "Score";

    private List<Integer> scoresList;
    private TextView leaderboardTextView;
    private Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        leaderboardTextView = findViewById(R.id.leaderboardTextView);

        scoresList = new ArrayList<>();

        // Retrieve scores from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        for (int i = 1; i <= 5; i++) {
            int score = prefs.getInt(SCORE_KEY + i, 0); // Default value is 0 if no score is found
            scoresList.add(score);
        }

        // Sort the scores in descending order
        Collections.sort(scoresList, Collections.reverseOrder());

        // Display the scores on the leaderboard
        StringBuilder leaderboardText = new StringBuilder();
        for (int i = 0; i < scoresList.size(); i++) {
            leaderboardText.append("Rank ").append(i + 1).append(": ").append(scoresList.get(i)).append("\n");
        }
        leaderboardTextView.setText(leaderboardText.toString());
    }
}