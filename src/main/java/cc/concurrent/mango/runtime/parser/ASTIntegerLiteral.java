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

package cc.concurrent.mango.runtime.parser;

import cc.concurrent.mango.runtime.RuntimeContext;
import cc.concurrent.mango.runtime.TypeContext;

/**
 * 整数字面值
 *
 * @author ash
 */
public class ASTIntegerLiteral extends PrimaryExpression {

    private Integer value;

    public ASTIntegerLiteral(int i) {
        super(i);
    }

    public ASTIntegerLiteral(Parser p, int i) {
        super(p, i);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    void checkType(TypeContext context) {
        return;
    }

    @Override
    public Object value(RuntimeContext context) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}