/* Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.javacc.jjdoc;

import org.javacc.parser.Expansion;
import org.javacc.parser.JavaCodeProduction;
import org.javacc.parser.NonTerminal;
import org.javacc.parser.NormalProduction;
import org.javacc.parser.RegularExpression;
import org.javacc.parser.TokenProduction;
import org.javacc.utils.io.IndentingPrintWriter;

import java.util.HashMap;
import java.util.Map;

/** Output BNF in HTML 3.2 format. */
public class HTMLFormatter extends TextFormatter {
  private final Map<String, String> idMap = new HashMap<String, String>();
  private int id = 1;

  public HTMLFormatter(IndentingPrintWriter out) {
    super(out);
  }

  private String getId(String nt) {
    String i = idMap.get(nt);
    if (i == null) {
      idMap.put(nt, i = "prod" + id++);
    }
    return i;
  }

  private void println(String s) {
    print(s + "\n");
  }

  @Override
  public void text(String s) {
    String r = "";
    for (int i = 0; i < s.length(); ++i) {
      if (s.charAt(i) == '<') {
        r += "&lt;";
      }
      else if (s.charAt(i) == '>') {
        r += "&gt;";
      }
      else if (s.charAt(i) == '&') {
        r += "&amp;";
      }
      else {
        r += s.charAt(i);
      }
    }
    print(r);
  }

  @Override
  public void print(String s) {
    out.print(s);
  }

  @Override
  public void documentStart() {
    println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
    println("<HTML>");
    println("<HEAD>");
    if (!"".equals(JJDocOptions.getCSS())) {
      println("<LINK REL=\"stylesheet\" type=\"text/css\" href=\"" + JJDocOptions.getCSS() + "\"/>");
    }
    if (JJDocGlobals.inputFile != null) {
      println("<TITLE>BNF for " + JJDocGlobals.inputFile + "</TITLE>");
    }
    else {
      println("<TITLE>A BNF grammar by JJDoc</TITLE>");
    }
    println("</HEAD>");
    println("<BODY>");
    println("<H1 ALIGN=CENTER>BNF for " + JJDocGlobals.inputFile + "</H1>");
  }

  @Override
  public void documentEnd() {
    println("</BODY>");
    println("</HTML>");
  }

  @Override
  public void specialTokens(String s) {
    println(" <!-- Special token -->");
    println(" <TR>");
    println("  <TD>");
    println("   <PRE>");
    print(s);
    println("   </PRE>");
    println("  </TD>");
    println("</TR>");
  }

  @Override
  public void tokenStart(TokenProduction tp) {
    println(" <!-- Token -->");
    println(" <TR>");
    println("  <TD>");
    println("   <PRE>");
  }

  @Override
  public void tokenEnd(TokenProduction tp) {
    println("   </PRE>");
    println("  </TD>");
    println(" </TR>");
  }

  @Override
  public void nonterminalsStart() {
    println("<H2 ALIGN=CENTER>NON-TERMINALS</H2>");
    if (JJDocOptions.getOneTable()) {
      println("<TABLE>");
    }
  }

  @Override
  public void nonterminalsEnd() {
    if (JJDocOptions.getOneTable()) {
      println("</TABLE>");
    }
  }

  @Override
  public void tokensStart() {
    println("<H2 ALIGN=CENTER>TOKENS</H2>");
    println("<TABLE>");
  }

  @Override
  public void tokensEnd() {
    println("</TABLE>");
  }

  @Override
  public void javacode(JavaCodeProduction jp) {
    productionStart(jp);
    println("<I>java code</I></TD></TR>");
    productionEnd(jp);
  }

  @Override
  public void productionStart(NormalProduction np) {
    if (!JJDocOptions.getOneTable()) {
      println("");
      println("<TABLE ALIGN=CENTER>");
      println("<CAPTION><STRONG>" + np.getLhs() + "</STRONG></CAPTION>");
    }
    println("<TR>");
    println("<TD ALIGN=RIGHT VALIGN=BASELINE><A NAME=\"" + getId(np.getLhs()) + "\">" + np.getLhs() + "</A></TD>");
    println("<TD ALIGN=CENTER VALIGN=BASELINE>::=</TD>");
    print("<TD ALIGN=LEFT VALIGN=BASELINE>");
  }

  @Override
  public void productionEnd(NormalProduction np) {
    if (!JJDocOptions.getOneTable()) {
      println("</TABLE>");
      println("<HR>");
    }
  }

  @Override
  public void expansionStart(Expansion e, boolean first) {
    if (!first) {
      println("<TR>");
      println("<TD ALIGN=RIGHT VALIGN=BASELINE></TD>");
      println("<TD ALIGN=CENTER VALIGN=BASELINE>|</TD>");
      print("<TD ALIGN=LEFT VALIGN=BASELINE>");
    }
  }

  @Override
  public void expansionEnd(Expansion e, boolean first) {
    println("</TD>");
    println("</TR>");
  }

  @Override
  public void nonTerminalStart(NonTerminal nt) {
    print("<A HREF=\"#" + getId(nt.getName()) + "\">");
  }

  @Override
  public void nonTerminalEnd(NonTerminal nt) {
    print("</A>");
  }

  @Override
  public void reStart(RegularExpression r) {}

  @Override
  public void reEnd(RegularExpression r) {}
}
