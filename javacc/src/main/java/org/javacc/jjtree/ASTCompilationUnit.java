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

package org.javacc.jjtree;

public class ASTCompilationUnit extends JJTreeNode {
  ASTCompilationUnit(int id) {
    super(id);
  }

  @Override
  public void print(IO io) {
    Token t = getFirstToken();

    while (true) {
      if (t == JJTreeGlobals.parserImports) {

        // If the parser and nodes are in separate packages (NODE_PACKAGE specified in
        // OPTIONS), then generate an import for the node package.
        if (!JJTreeGlobals.nodePackageName.equals("")
            && !JJTreeGlobals.nodePackageName.equals(JJTreeGlobals.packageName)) {
          io.getOut().println("");
          io.getOut().println("import " + JJTreeGlobals.nodePackageName + ".*;");
        }
      }

      if (t == JJTreeGlobals.parserImplements) {
        if (t.getImage().equals("implements")) {
          print(io, t);
          openJJTreeComment(io, null);
          io.getOut().print(" " + JJTreeGlobals.treeConstantsClass() + ", ");
          closeJJTreeComment(io);
        }
        else {
          // t is pointing at the opening brace of the class body.
          openJJTreeComment(io, null);
          io.getOut().print("implements " + JJTreeGlobals.treeConstantsClass());
          closeJJTreeComment(io);
          print(io, t);
        }
      }
      else {
        print(io, t);
      }

      if (t == JJTreeGlobals.parserClassBodyStart) {
        openJJTreeComment(io, null);
        TreeStateFile.insertParserMembers(io);
        closeJJTreeComment(io);
      }

      if (t == getLastToken()) {
        return;
      }
      t = t.next;
    }
  }
}
