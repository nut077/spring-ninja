package com.github.nut077.springninja.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class EmailMatcher extends TypeSafeMatcher<String> {

    private final String expression;

    private EmailMatcher(final String expression) {
        this.expression = expression;
    }

    public static EmailMatcher isEmail() {
        return new EmailMatcher("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @Override
    protected boolean matchesSafely(final String string) {
        return string.matches(expression);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("value must be match email expression =`" + expression + "`");
    }
}
