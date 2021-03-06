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
package com.asakusafw.lang.compiler.core.dummy;

import com.asakusafw.lang.compiler.api.DataModelLoader;
import com.asakusafw.lang.compiler.api.reference.DataModelReference;
import com.asakusafw.lang.compiler.model.description.TypeDescription;

/**
 * Mock {@link DataModelLoader}.
 */
@SuppressWarnings("javadoc")
public class DummyDataModelLoader implements DataModelLoader, DummyElement {

    final String id;

    public DummyDataModelLoader() {
        this("default");
    }

    public DummyDataModelLoader(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DataModelReference load(TypeDescription type) {
        throw new UnsupportedOperationException();
    }
}
