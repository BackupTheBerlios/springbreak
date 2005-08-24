/**
 * Copyright 2005 Darren L. Spurgeon
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
package at.newsagg.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An abstract class from which each example servlet extends.  This class wraps the XML creation
 * (delegated to the child servlet class) and submission back through the HTTP response.
 * 
 * @author Darren L. Spurgeon
 * @version $Revision: 1.1 $ $Date: 2005/08/24 08:40:50 $
 */
public abstract class BaseAjaxServlet extends HttpServlet {

  /**
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String xml = null;

    try {
      xml = getXmlContent(request);
    } catch (Exception ex) {
      // Send back a 500 error code.
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can not create response");
      return;
    }

    // Set content to xml
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter pw = response.getWriter();
    pw.write(xml);
    pw.close();
  }

  /**
   * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

  /**
   * Each child class should override this method to generate the specific XML content necessary
   * for each AJAX action.
   * 
   * @param request the {@javax.servlet.http.HttpServletRequest} object
   * @return a {@java.lang.String} representation of the XML response/content
   */
  public abstract String getXmlContent(HttpServletRequest request);
}