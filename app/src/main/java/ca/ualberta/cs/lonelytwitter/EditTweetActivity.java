package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditTweetActivity extends Activity {
    private TextView singleTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        Intent intent = getIntent();
        NormalTweet tweet = new NormalTweet(intent.getStringExtra("Message"));

        singleTweet = (TextView) findViewById(R.id.singleTweet);
        singleTweet.setText(tweet.getMessage());
    }
}
