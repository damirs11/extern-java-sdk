package ru.kontur.extern_api.sdk;/*
 * The MIT License
 *
 * Copyright 2018 alexs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.google.gson.internal.bind.util.ISO8601Utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


/**
 * Thread safe DateParser. ke.api.public compatible
 */
public final class PublicDateFormat {

    public static final String OUTPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS0000'Z'";

    private static final List<ThreadLocal<DateFormat>> supportedFormats
            = new ArrayList<ThreadLocal<DateFormat>>() {
        {
            add(ThreadLocal.withInitial(() ->
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")));
            add(ThreadLocal.withInitial(() ->
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")));
        }
    };

    private static final DateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMAT);
    private static final Logger logger = Logger.getLogger(PublicDateFormat.class.getName());

    /**
     * Format the given Date object into string (Datetime format).
     *
     * @param date Date object
     * @return Formatted datetime in string representation
     */
    public static String formatDatetime(Date date) {
        if (date == null) {
            return null;
        }
        return outputFormat.format(date);
    }

    public static Date parseDateTime(String date) {

        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        // ISO8601Utils.parse() doesn't take datetime without timezone indicator
        try {
            return ISO8601Utils.parse(date, new ParsePosition(0));
        } catch (ParseException ignored) {
            // // Ok, try to use other date formatters

            // Try to add 'Z'
            String dateZ = date + "Z";
            try {
                return ISO8601Utils.parse(dateZ, new ParsePosition(0));
            } catch (ParseException ignore) {
            }
        }

        // WARNING! SimpleDateFormat has errors with ticks (nanoseconds)
        // Example:
        //    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        //    // (or SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");)
        //    Date r1 = f.parse("2018-01-01T20:00:00.123");     // total milliseconds: 1514836800123 - it's correct
        //    Date r2 = f.parse("2018-01-01T20:00:00.1234567"); // total milliseconds: 1514838034567 - it's wrong
        //    So, as hot fix we will use 'Z' to be parsed by ISO8601Utils.parse

        for (ThreadLocal<DateFormat> supportedFormate : supportedFormats) {
            try {
                return supportedFormate.get().parse(date);
            } catch (ParseException ignored) {
                // maybe later?
            }
        }

        logger.warning("Cannot parse date: " + date);
        return null;
    }

}
