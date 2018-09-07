/**
 * Copyright 2011-2018 Asakusa Framework Team.
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
package com.example.hive;

import java.util.Arrays;
import java.util.List;

import com.example.modelgen.dmdl.hive.orc.AbstractKsvHiveOrcFileOutputDescription;

/**
 * Output Key-Sort-Value data-set into <code>${output}</code>.
 */
public class OrcOutputDescription extends AbstractKsvHiveOrcFileOutputDescription {

    @Override
    public String getBasePath() {
        return "${output}";
    }

    @Override
    public String getResourcePattern() {
        return "*.orc";
    }

    @Override
    public List<String> getDeletePatterns() {
        return Arrays.asList(getResourcePattern());
    }
}