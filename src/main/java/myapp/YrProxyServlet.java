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

public class YrProxyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        resp.setContentType("text/plain");

        // This should be something like: https://api.met.no/weatherapi/locationforecast/1.9/?lat=12;lon=34
        String proxyUrl = "https://api.met.no/weatherapi"
                + req.getPathInfo()     // "/locationforecast/1.9/"
                + "?"
                + req.getQueryString(); // "lat=59.31895603;lon=18.05177629"
        resp.getWriter().println("{ \"proxy url\": \"" + proxyUrl + "\" }");
    }
}
