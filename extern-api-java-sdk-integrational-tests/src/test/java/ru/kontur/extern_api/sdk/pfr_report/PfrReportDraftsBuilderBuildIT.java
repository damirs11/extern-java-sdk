/*
 * Copyright (c) 2019 SKB Kontur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.kontur.extern_api.sdk.pfr_report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.kontur.extern_api.sdk.ExternEngine;
import ru.kontur.extern_api.sdk.model.TaskState;
import ru.kontur.extern_api.sdk.model.builders.BuildDraftsBuilderResult;
import ru.kontur.extern_api.sdk.model.builders.BuildDraftsBuilderTaskInfo;
import ru.kontur.extern_api.sdk.model.builders.pfr_report.PfrReportDraftsBuilder;
import ru.kontur.extern_api.sdk.model.builders.pfr_report.PfrReportDraftsBuilderDocument;
import ru.kontur.extern_api.sdk.service.builders.pfr_report.PfrReportDraftsBuilderService;
import ru.kontur.extern_api.sdk.utils.CryptoUtils;
import ru.kontur.extern_api.sdk.utils.TestSuite;
import ru.kontur.extern_api.sdk.utils.builders.DraftsBuilderCreator;
import ru.kontur.extern_api.sdk.utils.builders.DraftsBuilderDocumentCreator;
import ru.kontur.extern_api.sdk.utils.builders.DraftsBuilderDocumentFileCreator;

@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("Build pfr report drafts builder")
class PfrReportDraftsBuilderBuildIT {

    private static ExternEngine engine;
    private static CryptoUtils cryptoUtils;
    private static PfrReportDraftsBuilderService pfrReportDraftsBuilderService;

    private PfrReportDraftsBuilder draftsBuilder;

    @BeforeAll
    static void setUpClass() {
        engine = TestSuite.Load().engine;
        cryptoUtils = CryptoUtils.with(engine.getCryptoProvider());

        pfrReportDraftsBuilderService = engine.getDraftsBuilderService().pfrReport();
    }

    @BeforeEach
    void setUp() {
        draftsBuilder = new DraftsBuilderCreator().createPfrReportDraftsBuilder(
                engine,
                cryptoUtils
        );
        PfrReportDraftsBuilderDocument draftsBuilderDocument = new DraftsBuilderDocumentCreator()
                .createPfrReportDraftsBuilderDocument(
                        engine,
                        draftsBuilder
                );
        new DraftsBuilderDocumentFileCreator().createPfrReportDraftsBuilderDocumentFile(
                engine,
                cryptoUtils,
                draftsBuilder,
                draftsBuilderDocument
        );
    }

    @Test
    @DisplayName("build drafts builder")
    void build() {
        BuildDraftsBuilderResult result = pfrReportDraftsBuilderService.buildAsync(draftsBuilder.getId()).join();
        assertEquals(1, result.getDraftIds().length);
    }

    @Test
    @DisplayName("build drafts builder with deferred")
    void buildWithDeferred() {
        BuildDraftsBuilderTaskInfo taskInfo =
                pfrReportDraftsBuilderService.startBuildAsync(draftsBuilder.getId()).join();
        assertEquals(TaskState.RUNNING, taskInfo.getTaskState());

        taskInfo = pfrReportDraftsBuilderService.getBuildInfoAsync(draftsBuilder.getId(), taskInfo.getId()).join();
        assertEquals(TaskState.RUNNING, taskInfo.getTaskState());

        BuildDraftsBuilderResult result =
                pfrReportDraftsBuilderService.waitBuildResultAsync(draftsBuilder.getId(), taskInfo.getId()).join();
        assertEquals(1, result.getDraftIds().length);

        taskInfo = pfrReportDraftsBuilderService.getBuildInfoAsync(draftsBuilder.getId(), taskInfo.getId()).join();
        assertEquals(TaskState.SUCCEED, taskInfo.getTaskState());
    }
}
