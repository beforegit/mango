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

package cc.concurrent.mango.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ash
 */
public class ObjectResultSetExtractor<T> implements ResultSetExtractor<T> {

    private final RowMapper<T> rowMapper;

    public ObjectResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public T extractData(ResultSet rs) throws SQLException {
        Class<T> mappedClass = rowMapper.getMappedClass();
        if (!mappedClass.isPrimitive()) {
            return rs.next() ? rowMapper.mapRow(rs, 1) : null;
        }

        // 原生类型
        if (!rs.next()) {
            throw new NullPointerException("can't cast null to primitive type " + mappedClass);
        }
        T r = rowMapper.mapRow(rs, 1);
        if (r == null) {
            throw new NullPointerException("can't cast null to primitive type " + mappedClass);
        }
        return r;
    }

}
