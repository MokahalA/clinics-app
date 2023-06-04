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



public class CreateAccountActivityTest {

    //Unit test for the createAccountActivity
    // Contains 4 test cases
    // Specifically it validates the: First name, Last name, Username, and Password fields of this activity

    @Rule
    public ActivityTestRule<createAccountActivity> mActivityTestRule = new ActivityTestRule<createAccountActivity>(createAccountActivity.class);
    private createAccountActivity mActivity = null;
    private TextView text;


    @Before
    public void setUp() throws Exception{
        mActivity = mActivityTestRule.getActivity();
    }

    //Validates the First name field
    @Test
    @UiThreadTest
    public void checkFristName() throws Exception{

        assertNotNull(mActivity.findViewById(R.id.et_FirstName));
        text = mActivity.findViewById(R.id.et_FirstName);
        text.setText("ahmad");
        String firstName = text.getText().toString();
        assertNotEquals("Chris", firstName);

    }

    //Validates the Last name field
    @Test
    @UiThreadTest
    public void checkLastName() throws Exception{

        assertNotNull(mActivity.findViewById(R.id.et_LastName));
        text = mActivity.findViewById(R.id.et_LastName);
        text.setText("El Mokahal");
        String lastName = text.getText().toString();
        assertNotEquals("Test", lastName);
    }

    //Validates the Username field
    @Test
    @UiThreadTest
    public void checkUsername() throws Exception{

        assertNotNull(mActivity.findViewById(R.id.et_Username));
        text = mActivity.findViewById(R.id.et_Username);
        text.setText("admin");
        String username = text.getText().toString();
        assertNotEquals("admin1", username);
    }

    //Validates the Password field
    @Test
    @UiThreadTest
    public void checkPassword() throws Exception{

        assertNotNull(mActivity.findViewById(R.id.et_Pwd));
        text = mActivity.findViewById(R.id.et_Pwd);
        text.setText("5T5ptQ");
        String pwd = text.getText().toString();
        assertNotEquals("5A5ptQ", pwd);
    }

}
