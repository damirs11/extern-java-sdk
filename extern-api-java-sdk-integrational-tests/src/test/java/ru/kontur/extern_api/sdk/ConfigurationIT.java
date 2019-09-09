/*
 * Copyright (c) 2018 SKB Kontur
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
 *
 */

package ru.kontur.extern_api.sdk;


import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.kontur.extern_api.sdk.adaptor.ApiException;
import ru.kontur.extern_api.sdk.service.AccountService;
import ru.kontur.extern_api.sdk.utils.AuthenticationProviderAdaptor;
import ru.kontur.extern_api.sdk.utils.TestConfig;

@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("Configuration tokens should")
class ConfigurationIT {

    private Configuration configuration = TestConfig.LoadConfigFromEnvironment();

    private ExternEngine newEngine() {
        return ExternEngineBuilder.createExternEngine(configuration.getServiceBaseUri())
                .apiKey(configuration.getApiKey())
                .buildAuthentication(configuration.getAuthBaseUri(), builder -> builder.
                        passwordAuthentication(configuration.getLogin(), configuration.getPass())
                )
                .doNotUseCryptoProvider()
                .accountId(configuration.getAccountId())
                .build(Level.BODY);
    }

    @Test
    @DisplayName("share same sid across all services")
    void shareSameSid() {

        ExternEngine engine = newEngine();

        AccountService accountService = engine.getAccountService();
        Assertions.assertDoesNotThrow(
                () -> accountService.getAccountsAsync(0, 100).join().getOrThrow()
        );

        engine.setAuthenticationProvider(new AuthenticationProviderAdaptor());

        ApiException apiException = Assertions.assertThrows(
                ApiException.class,
                () -> accountService.getAccountsAsync(0, 100).get().getOrThrow()
        );
        Assertions.assertEquals(401, apiException.getCode());

        apiException = Assertions.assertThrows(
                ApiException.class,
                () -> engine.getAccountService().getAccountsAsync(0, 100).get().getOrThrow()
        );
        Assertions.assertEquals(401, apiException.getCode());
    }
}
