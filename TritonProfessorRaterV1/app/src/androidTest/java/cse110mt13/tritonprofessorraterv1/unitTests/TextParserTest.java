package cse110mt13.tritonprofessorraterv1.unitTests;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Test;
import static org.junit.Assert.*;

import cse110mt13.tritonprofessorraterv1.TextParser;

/**
 * Created by Rui Deng on 2016/2/14.
 */
public class TextParserTest {
    @Test
    public void testValidEmail()
    {
        boolean emailResult = TextParser.validEmail("rdeng@ucsd.edu");
        if(!emailResult)
        {
            fail("@ucsd.edu should work");
        }

        emailResult = TextParser.validEmail("rdeng@gmail.ucsd.edu");
        if(!emailResult)
        {
            fail("@gmail.ucsd.edu should work");
        }

        emailResult = TextParser.validEmail("rdeng@gmail.com");
        if(emailResult)
        {
            fail("@gmail.com should not work");
        }

        emailResult = TextParser.validEmail("RDENG@UCSD.EDU");
        if(!emailResult)
        {
            fail("upper case should work");
        }
    }

    @Test
    public void testValidPassword()
    {
        boolean passResult = TextParser.validPassword("aaa");
        if(passResult)
        {
            fail("only 3 letters should not work");
        }

        passResult = TextParser.validPassword("aaaaaaaa");
        if(passResult)
        {
            fail("only letters should not work");
        }

        passResult = TextParser.validPassword("11");
        if(passResult)
        {
            fail("only 2 numbers should not work");
        }

        passResult = TextParser.validPassword("111111111");
        if(passResult)
        {
            fail("only numbers should not work");
        }

        passResult = TextParser.validPassword("abcd1234");
        if(!passResult)
        {
            fail("this is a valid password");
        }

        passResult = TextParser.validPassword("abcd1234");
        if(!passResult)
        {
            fail("this is a valid password");
        }

        passResult = TextParser.validPassword("abcdefgh12345678");
        if(passResult)
        {
            fail("over limit should not work");
        }
    }
}
