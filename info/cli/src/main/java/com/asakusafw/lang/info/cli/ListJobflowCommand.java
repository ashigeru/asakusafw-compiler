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
package com.asakusafw.lang.info.cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import com.asakusafw.lang.info.BatchInfo;
import com.asakusafw.lang.info.JobflowInfo;
import com.asakusafw.lang.info.task.TaskListAttribute;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

/**
 * A command for printing list of jobflows.
 * @since 0.4.2
 */
@Command(
        name = "jobflow",
        description = "Displays list of jobflows",
        hidden = false
)
public class ListJobflowCommand extends InfoCommand {

    @Option(
            name = { "--verbose", "-v", },
            title = "verbose mode",
            description = "verbose mode",
            arity = 0,
            required = false)
    boolean showVerbose = false;

    @Override
    protected void process(PrintWriter writer, BatchInfo info) throws IOException {
        if (showVerbose) {
            for (JobflowInfo jobflow : info.getJobflows()) {
                writer.printf("%s (%s):%n",
                        jobflow.getId(),
                        ListUtil.normalize(jobflow.getDescriptionClass()));
                ListUtil.printBlock(
                        writer,
                        4,
                        "blockers",
                        jobflow.getBlockerIds().stream()
                            .sorted()
                            .collect(Collectors.toList()));
                jobflow.findAttribute(TaskListAttribute.class)
                    .map(TaskListAttribute::getPhases)
                    .ifPresent(phases -> phases.forEach((phase, tasks) -> {
                        ListUtil.printBlock(
                                writer,
                                4,
                                phase.getSymbol(),
                                tasks.stream()
                                    .map(it -> String.format(
                                            "%s (@%s)",
                                            it.getModuleName(),
                                            ListUtil.normalize(it.getProfileName())))
                                    .collect(Collectors.toList()));
                    }));
            }
        } else {
            info.getJobflows().forEach(it -> writer.println(it.getId()));
        }
    }
}
