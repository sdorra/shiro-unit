package com.github.sdorra.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

public class ShiroRuleTeardownTest {

    @Test
    public void testTearDownUnbindSubject() {
        ThreadContext.bind(mock(Subject.class));
        ShiroRule.tearDownShiro();
        assertNull(ThreadContext.getSubject());
    }

}
