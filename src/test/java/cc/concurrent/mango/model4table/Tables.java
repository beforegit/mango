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

package cc.concurrent.mango.model4table;

import cc.concurrent.mango.Config;
import cc.concurrent.mango.MangoTest;
import cc.concurrent.mango.util.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ash
 */
public enum Tables {

    USER("user.sql"),
    PERSON("person.sql"),
    BYTE_INFO("byte_info.sql"),
    MSG("msg.sql"),
    SHARDING_MSG("sharding_msg.sql"),
    LONG_ID_MSG("long_id_msg.sql"),
    ;

    private String name;

    private Tables(String name) {
        this.name = name;
    }

    public void load(Connection conn) throws IOException, SQLException {
        ScriptRunner sr = new ScriptRunner(conn, false, true);
        InputStream is = MangoTest.class.getResourceAsStream("/" + Config.getDir() + "/" + name);
        sr.runScript(new InputStreamReader(is));
    }

}
