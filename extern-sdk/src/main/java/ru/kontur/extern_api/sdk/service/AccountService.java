/*
 * MIT License
 *
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
 */

package ru.kontur.extern_api.sdk.service;

import java.util.UUID;
import ru.kontur.extern_api.sdk.model.Account;
import ru.kontur.extern_api.sdk.model.AccountList;
import ru.kontur.extern_api.sdk.model.CreateAccountRequest;
import ru.kontur.extern_api.sdk.model.Link;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import ru.kontur.extern_api.sdk.provider.Providers;
import ru.kontur.extern_api.sdk.service.transport.adaptor.QueryContext;


/**
 * @author AlexS
 *
 * Группа методов предоставляет доступ к операциям для работы с учетными записями:
 *  - получение списка учетных записей;
 *  - создать учетную запись
 *  - получить учетную запись по идентификатору
 *
 * @see QueryContext
 */
public interface AccountService extends Providers {

    /**
     * GET /
     * Асинхронный метод возвращает список ссылок на доступные ресурсы.
     * Для выполнения аутентификация не требуется.
     * @return список ссылок
     * @see Link
     */
    CompletableFuture<QueryContext<List<Link>>> acquireBaseUriAsync();

    /**
     * GET /
     * Синхронный метод возвращает список ссылок на доступные ресурсы.
     * Для выполнения аутентификация не требуется.
     * @return список ссылок
     * @see Link
     */
    QueryContext<List<Link>> acquireBaseUri(QueryContext<?> cxt);

    /**
     * GET /v1
     * Асинхронный метод возвращает список учетных записей с разбивкой по страницам
     * @return список учетных записей
     * @see AccountList
     */
    CompletableFuture<QueryContext<AccountList>> acquireAccountsAsync();

    /**
     * GET /v1
     * Синхронный метод возвращает список учетных записей с разбивкой по страницам
     * @return список учетных записей
     * @see AccountList
     */
    QueryContext<AccountList> acquireAccounts(QueryContext<?> cxt);

    /**
     * POST /v1
     * Асинхронный метод предназначен для создания новой учетной записи
     * @param createAccountRequest {@link CreateAccountRequest} структура данных, содержащая информацию для создания новой учетной записи
     * @return новая учетная запись {@link Account}
     */
    CompletableFuture<QueryContext<Account>> createAccountAsync(CreateAccountRequest createAccountRequest);

    /**
     * POST /v1
     * Синхронный метод предназначен для создания новой учетной записи
     * @param cxt контекст, содержащий следующие параметры:
     *  - структура данных {@link CreateAccountRequest}. Для установки необходимо использовать метод {@link QueryContext#setCreateAccountRequest(CreateAccountRequest)}
     * @return новая учетная запись {@link Account}
     */
    QueryContext<Account> createAccount(QueryContext<?> cxt);

    /**
     * GET /v1/{accountId}
     * Асинхронный метод предназначен для получения учетной записи
     * @param accountId идентификатор учетной записи
     * @return учетная запись
     * @see Account
     */
    CompletableFuture<QueryContext<Account>> getAccountAsync(String accountId);

    /**
     * GET /v1/{accountId}
     * Асинхронный метод предназначен для получения учетной записи
     * @param cxt контекст, содержащий следующие параметры:
     *  - идентификатор учетной записи. Для установки необходимо использовать метод {@link QueryContext#setAccountId}
     * @return учетная запись
     * @see Account
     */
    QueryContext<Account> getAccount(QueryContext<?> cxt);
}
