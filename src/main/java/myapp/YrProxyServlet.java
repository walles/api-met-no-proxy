/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package myapp;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class YrProxyServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(YrProxyServlet.class.getName());

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
    {
        // From: http://stackoverflow.com/a/13800995/473672
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        String referrer = req.getHeader("referer"); // Yes, with the legendary misspelling.
        String remoteAddress = req.getRemoteAddr();
        LOGGER.info("Proxying for <" + remoteAddress + ">, referrer: <" + referrer + ">...");

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");

        // This should be something like: https://api.met.no/weatherapi/locationforecast/1.9/?lat=12;lon=34
        URL proxyUrl = new URL("https://api.met.no/weatherapi"
                + req.getPathInfo()     // "/locationforecast/1.9/"
                + "?"
                + req.getQueryString()); // "lat=59.31895603;lon=18.05177629"

        HttpURLConnection urlConnection = (HttpURLConnection)proxyUrl.openConnection();
        urlConnection.setConnectTimeout(1000);
        urlConnection.setReadTimeout(1000);

        try (InputStream inputStream = urlConnection.getInputStream()) {
            resp.setStatus(urlConnection.getResponseCode());
            resp.setContentType(urlConnection.getContentType());

            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String apiResponse = scanner.hasNext() ? scanner.next() : "";
            resp.getWriter().print(apiResponse);
        }
   }
}