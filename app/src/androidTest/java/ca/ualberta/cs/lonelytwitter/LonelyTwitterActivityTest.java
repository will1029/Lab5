package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2<LonelyTwitterActivity> {
    private Solo solo;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void TestStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testTweet() {
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");

        solo.clickOnButton("Save");
        solo.clearEditText((EditText) solo.getView(R.id.body));

        assertTrue(solo.waitForText("Test Tweet!"));

        solo.clickOnButton("Clear");
        assertFalse(solo.searchText("Test Tweet!"));
    }

    public void testClickTweetList() {
        LonelyTwitterActivity activity = (LonelyTwitterActivity) solo.getCurrentActivity();

        String text = "Test Tweet!";

        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
        solo.clickOnButton("Clear");

        solo.enterText((EditText) solo.getView(R.id.body), text);
        solo.clickOnButton("Save");
        solo.waitForText(text);

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals(text, tweet.getMessage());

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
        //assertTrue(solo.waitForText("TextView"));

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
    }

    /*
        Lab 7 UI test cases
     */
    public void testEditTweetActivityView() {
        String testText = "Text1";
        String testText2 = "Text2";

        //Get activity
        LonelyTwitterActivity activity = (LonelyTwitterActivity) solo.getCurrentActivity();

        //Clear
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
        solo.clickOnButton("Clear");

        //Enter first test string
        solo.enterText((EditText) solo.getView(R.id.body), testText);
        solo.clickOnButton("Save");
        solo.waitForText(testText);

        //Enter second test string
        solo.clearEditText((EditText) solo.getView(R.id.body));
        solo.enterText((EditText) solo.getView(R.id.body), testText2);
        solo.clickOnButton("Save");
        solo.waitForText(testText2);

        //Click on first item
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
        assertTrue(solo.waitForText(testText));

        //Back
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

        //Click second item
        solo.clickInList(2);
        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
        assertTrue(solo.waitForText(testText2));

        //Back
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}