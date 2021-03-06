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
 * @author ash
 */
public abstract class MathExpression extends ValuableExpression {

    public MathExpression(int i) {
        super(i);
    }

    public MathExpression(Parser p, int i) {
        super(p, i);
    }

    @Override
    void checkType(TypeContext context) {
        ((ValuableExpression) jjtGetChild(0)).checkType(context);
        ((ValuableExpression) jjtGetChild(1)).checkType(context);
    }

    @Override
    public Object value(RuntimeContext context) {
        Object left = ((ValuableExpression) jjtGetChild(0)).value(context);
        Object right = ((ValuableExpression) jjtGetChild(1)).value(context);

        Object special = handleSpecial(left, right);
        if (special != null) {
            return special;
        }

        if (!(left instanceof Integer) || !(right instanceof Integer)) {
            throw new IllegalStateException("error type");
        }
        return perform((Integer) left, (Integer) right);
    }

    @Override
    Token getFirstToken() {
        ValuableExpression parent = (ValuableExpression) jjtGetParent();
        if (parent instanceof ASTExpression) { // 父节点是表达式根节点
            return parent.jjtGetFirstToken();
        }
        if (this == parent.jjtGetChild(1)) { // 父节点的右侧节点
            return parent.jjtGetFirstToken();
        } else { // 父节点的左侧节点
            return parent.getFirstToken();
        }
    }

    @Override
    Token getLastToken() {
        ValuableExpression parent = (ValuableExpression) jjtGetParent();
        if (parent instanceof ASTExpression) { // 父节点是表达式根节点
            return parent.jjtGetLastToken();
        }
        if (this == parent.jjtGetChild(1)) { // 父节点的右侧节点
            return parent.jjtGetLastToken();
        } else { // 父节点的左侧节点
            Token t = jjtGetLastToken();
            Token end = parent.jjtGetFirstToken();
            while (t.next.next != end) {
                t = t.next;
            }
            return t;
        }
    }

    protected Object handleSpecial(Object left, Object right) {
        return null;
    }

    public abstract Integer perform(Integer left, Integer right);

}
