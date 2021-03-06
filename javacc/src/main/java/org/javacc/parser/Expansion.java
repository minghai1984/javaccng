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

package org.javacc.parser;

/**
 * Describes expansions - entities that may occur on the
 * right hand sides of productions.  This is the base class of
 * a bunch of other more specific classes.
 */
public abstract class Expansion {
  /**
   * The line and column number of the construct that corresponds
   * most closely to this node.
   */
  private int line, column;
  /**
   * An internal name for this expansion.  This is used to generate parser
   * routines.
   */
  String internalName = "";
  /**
   * The parent of this expansion node.  In case this is the top level
   * expansion of the production it is a reference to the production node
   * otherwise it is a reference to another Expansion node.  In case this
   * is the top level of a lookahead expansion,then the parent is null.
   */
  public Object parent;
  /** The ordinal of this node with respect to its parent. */
  int ordinal;
  public long myGeneration;
  /**
   * This flag is used for bookkeeping by the minimumSize method in class
   * ParseEngine.
   */
  public boolean inMinimumSize;

  void setColumn(int column) {
    this.column = column;
  }

  int getColumn() {
    return column;
  }

  void setLine(int line) {
    this.line = line;
  }

  int getLine() {
    return line;
  }

  private String getSimpleName() {
    String name = getClass().getName();
    return name.substring(name.lastIndexOf(".") + 1);
  }

  @Override
  public String toString() {
    return "[" + getLine() + "," + getColumn() + " " + getSimpleName() + "]";
  }

  /**
   * A reimplementing of Object.hashCode() to be deterministic.  This uses
   * the line and column fields to generate an arbitrary number - we assume
   * that this method is called only after line and column are set to
   * their actual values.
   * TODO remove me
   */
  public int hashCode() {
    return getLine() + getColumn();
  }
}
