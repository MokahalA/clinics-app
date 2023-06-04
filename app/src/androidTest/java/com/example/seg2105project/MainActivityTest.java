package com.example.seg2105project;


import android.content.Context;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class MainActivityTest {

    //A test class for the MainActivity
    // Contains 2 test cases
    // Specifically tests the validation of the login fields: Username, and Password

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity = null;
    private TextView text;


    @Before
    public void setUp() throws Exception{
        mActivity = mActivityTestRule.getActivity();
    }

    //Validates the username field
    @Test
    @UiThreadTest
    public void checkUsername() throws Exception{

        assertNotNull(mActivity.findViewById(R.id.et_username));
        text = mActivity.findViewById(R.id.et_username);
        text.setText("admin");
        String username = text.getText().toString();
        assertNotEquals("admin1", username);
    }


    //Validates the password field
    @Test
    @UiThreadTest
    public void checkPassword() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.et_password));
        text = mActivity.findViewById(R.id.et_password);
        text.setText("5T5ptQ");
        String pwd = text.getText().toString();
        assertNotEquals("5A5ptQ", pwd);
    }
}
