package com.monkeyk.sos.infrastructure;


import com.monkeyk.sos.filter.PasswordHandler;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/*
  * @author Shengzhao Li
  */
public class PasswordHandlerTest {


    @Test
    public void testMd5() throws Exception {

        final String md5 = PasswordHandler.encode("123456");
        assertNotNull(md5);
        System.out.println(md5);
    }
}