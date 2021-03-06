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
package com.asakusafw.bridge.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link LaunchOption} for Hadoop platform.
 */
public class HadoopPropertiesOption extends AbstractPropertiesOption {

    static final Logger LOG = LoggerFactory.getLogger(HadoopPropertiesOption.class);

    /**
     * The command name.
     */
    public static final String COMMAND = "--hadoop-conf"; //$NON-NLS-1$

    @Override
    public Set<String> getCommands() {
        return Collections.singleton(COMMAND);
    }

    @Override
    protected Map<String, String> extract(File file) throws LaunchConfigurationException {
        LOG.debug("loading file: {} ({})", file, COMMAND); //$NON-NLS-1$
        Configuration configuration = new Configuration(false);
        try (InputStream in = new FileInputStream(file)) {
            configuration.addResource(in);
            Map<String, String> results = new TreeMap<>();
            for (Map.Entry<String, String> entry : configuration) {
                results.put(entry.getKey(), entry.getValue());
            }
            return results;
        } catch (IOException e) {
            throw new LaunchConfigurationException(MessageFormat.format(
                    "error occurred while loading configuration file: {1} ({0})",
                    COMMAND, file), e);
        }
    }
}
