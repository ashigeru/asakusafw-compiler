/**
 * Copyright 2011-2019 Asakusa Framework Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asakusafw.dag.compiler.extension.windgate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asakusafw.dag.compiler.flow.ExternalPortDriver;
import com.asakusafw.dag.compiler.flow.ExternalPortDriverProvider;

/**
 * A provider of {@link WindGateJdbcPortDriver}.
 * @since 0.4.0
 */
public final class WindGateJdbcPortDriverProvider implements ExternalPortDriverProvider {

    static final Logger LOG = LoggerFactory.getLogger(WindGateJdbcPortDriverProvider.class);

    @Override
    public ExternalPortDriver newInstance(Context context) {
        LOG.debug("enabling embedded WindGate JDBC");
        return new WindGateJdbcPortDriver(context);
    }
}