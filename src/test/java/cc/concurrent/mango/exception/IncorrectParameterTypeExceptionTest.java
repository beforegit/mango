/*
 * Copyright 2014 mango.concurrent.cc
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package cc.concurrent.mango.exception;

import cc.concurrent.mango.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试{@link IncorrectParameterTypeException}
 *
 * @author ash
 */
public class IncorrectParameterTypeExceptionTest {


    private final static Mango mango = new Mango(Config.getDataSource());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid type of :1, " +
                "expected int or java.lang.Integer or java.lang.String " +
                "but class java.lang.Object");
        Dao dao = mango.create(Dao.class);
        dao.add(new Object());
    }

    @Test
    public void test2() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid type of :1, " +
                "expected int or java.lang.Integer " +
                "but class java.lang.String");
        Dao dao = mango.create(Dao.class);
        dao.add2("");
    }

    @Test
    public void test3() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("parameter of batch update " +
                "expected array or implementations of java.util.List or implementations of java.util.Set " +
                "but class java.lang.Integer");
        Dao dao = mango.create(Dao.class);
        dao.batchAdd2(1);
    }

    @Test
    public void test4() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid type of :1, " +
                "expected array or implementations of java.util.List or implementations of java.util.Set " +
                "but int");
        Dao dao = mango.create(Dao.class);
        dao.get(1);
    }

    @Test
    public void test5() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid actual type of :1, actual type of :1 " +
                "expected a class can be identified by jdbc " +
                "but java.util.List<java.lang.Integer>");
        Dao dao = mango.create(Dao.class);
        dao.get2(new ArrayList<List<Integer>>());
    }

    @Test
    public void test6() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid component type of :1, component type of :1 " +
                "expected a class can be identified by jdbc " +
                "but class java.lang.Object");
        Dao dao = mango.create(Dao.class);
        dao.get3(new Object[]{});
    }

    @Test
    public void test7() {
        thrown.expect(IncorrectParameterTypeException.class);
        thrown.expectMessage("invalid type of :1, " +
                "expected a class can be identified by jdbc " +
                "but class java.lang.Object");
        Dao dao = mango.create(Dao.class);
        dao.get4(new Object());
    }

    @DB
    static interface Dao {
        @SQL("insert into ${1 + :1} ...")
        public int add(Object obj);

        @SQL("insert into ${1 + :1 * 10} ...")
        public int add2(String str);

        @SQL("insert into ...")
        public int[] batchAdd2(Integer a);

        @SQL("select ... where a in (:1)")
        public List<Integer> get(int a);

        @SQL("select ... where a in (:1)")
        public List<Integer> get2(List<List<Integer>> list);

        @SQL("select ... where a in (:1)")
        public List<Integer> get3(Object[] objs);

        @SQL("select ... where a=:1")
        public int get4(Object obj);
    }

}
