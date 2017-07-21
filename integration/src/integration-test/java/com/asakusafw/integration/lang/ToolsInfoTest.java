/**
 * Copyright 2011-2017 Asakusa Framework Team.
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
package com.asakusafw.integration.lang;

import static com.asakusafw.integration.lang.Util.*;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Test;

import com.asakusafw.integration.AsakusaConfigurator;
import com.asakusafw.integration.AsakusaProjectProvider;
import com.asakusafw.utils.gradle.Bundle;
import com.asakusafw.utils.gradle.ContentsConfigurator;

/**
 * Test for {@code tools/bin/info.sh}.
 */
public class ToolsInfoTest {

    private static final String CMD = "tools/bin/info.sh";

    /**
     * project provider.
     */
    @ClassRule
    public static final AsakusaProjectProvider PROVIDER = new AsakusaProjectProvider()
            .withProject(ContentsConfigurator.copy(data("vanilla")))
            .withProject(ContentsConfigurator.copy(data("ksv")))
            .withProject(ContentsConfigurator.copy(data("logback-test")))
            .withProject(AsakusaConfigurator.projectHome())
            .withProject(AsakusaConfigurator.hadoop(AsakusaConfigurator.Action.UNSET_ALWAYS))
            .withProvider(provider -> {
                // install framework only once
                framework = provider.newInstance("inf")
                        .gradle("attachVanillaBatchapps", "installAsakusafw")
                        .getFramework();
            });

    static Bundle framework;

    /**
     * {@code info.sh}.
     */
    @Test
    public void info() {
        framework.withLaunch(CMD);
    }

    /**
     * {@code info.sh list}.
     */
    @Test
    public void info_list() {
        framework.withLaunch(CMD, "list");
    }

    /**
     * {@code info.sh list batch}.
     */
    @Test
    public void info_list_batch() {
        framework.withLaunch(CMD, "list", "batch", "-v");
    }

    /**
     * {@code info.sh list jobflow}.
     */
    @Test
    public void info_list_jobflow() {
        checkInfo();
        framework.withLaunch(CMD, "list", "jobflow", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh list plan}.
     */
    @Test
    public void info_list_plan() {
        checkInfo();
        framework.withLaunch(CMD, "list", "plan", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh list operator}.
     */
    @Test
    public void info_list_operator() {
        checkInfo();
        framework.withLaunch(CMD, "list", "operator", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh list directio}.
     */
    @Test
    public void info_list_directio() {
        checkInfo();
        framework.withLaunch(CMD, "list", "directio");
    }

    /**
     * {@code info.sh list directio input}.
     */
    @Test
    public void info_list_directio_input() {
        checkInfo();
        framework.withLaunch(CMD, "list", "directio", "input", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh list directio output}.
     */
    @Test
    public void info_list_directio_output() {
        checkInfo();
        framework.withLaunch(CMD, "list", "directio", "output", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh list windgate}.
     */
    @Test
    public void info_list_windgate() {
        checkInfo();
        framework.withLaunch(CMD, "list", "windgate");
    }

    /**
     * {@code info.sh list windgate input}.
     */
    @Test
    public void info_list_windgate_input() {
        checkInfo();
        framework.withLaunch(CMD, "list", "windgate", "input", "-v",
                "vanilla.wg.perf.average.sort");
    }

    /**
     * {@code info.sh list windgate output}.
     */
    @Test
    public void info_list_windgate_output() {
        checkInfo();
        framework.withLaunch(CMD, "list", "windgate", "output", "-v",
                "vanilla.wg.perf.average.sort");
    }

    /**
     * {@code info.sh draw}.
     */
    @Test
    public void info_draw() {
        checkInfo();
        framework.withLaunch(CMD, "draw");
    }

    /**
     * {@code info.sh draw jobflow}.
     */
    @Test
    public void info_draw_jobflow() {
        checkInfo();
        framework.withLaunch(CMD, "draw", "jobflow", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh draw plan}.
     */
    @Test
    public void info_draw_plan() {
        checkInfo();
        framework.withLaunch(CMD, "draw", "plan", "-v",
                "vanilla.perf.average.sort");
    }

    /**
     * {@code info.sh draw operator}.
     */
    @Test
    public void info_draw_operator() {
        checkInfo();
        framework.withLaunch(CMD, "draw", "operator", "-v",
                "vanilla.perf.average.sort");
    }

    private static void checkInfo() {
        Assume.assumeThat(
                framework.find("batchapps/vanilla.perf.average.sort/etc/batch-info.json"),
                is(not(Optional.empty())));
    }
}
