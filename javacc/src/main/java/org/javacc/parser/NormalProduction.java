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

import java.util.ArrayList;
import java.util.List;

/** Describes JavaCC productions. */
public abstract class NormalProduction {
  /**
   * The line and column number of the construct that corresponds
   * most closely to this node.
   */
  private int line, column;
  /** The NonTerminal nodes which refer to this production. */
  private List<Expansion> parents = new ArrayList<Expansion>();
  /** The access modifier of this production. */
  private String accessModifier;
  /** The name of the non-terminal of this production. */
  private String lhs;
  /** The tokens that make up the return type of this production. */
  private List<Token> returnTypeTokens = new ArrayList<Token>();
  /** The tokens that make up the parameters of this production. */
  private List<Token> parameterListTokens = new ArrayList<Token>();
  /**
   * Each entry in this list is a list of tokens that represents an
   * exception in the throws list of this production.  This list does not
   * include ParseException which is always thrown.
   */
  private List<List<Token>> throwsList = new ArrayList<List<Token>>();
  /** The RHS of this production.  Not used for JavaCodeProduction. */
  private Expansion expansion;
  /** This boolean flag is true if this production can expand to empty. */
  private boolean emptyPossible = false;
  /**
   * A list of all non-terminals that this one can expand to without
   * having to consume any tokens.  Also an index that shows how many
   * pointers exist.
   */
  private NormalProduction[] leftExpansions = new NormalProduction[10];
  int leIndex = 0;
  /**
   * The following variable is used to maintain state information for the
   * left-recursion determination algorithm:  It is initialized to 0, and
   * set to -1 if this node has been visited in a pre-order walk, and then
   * it is set to 1 if the pre-order walk of the whole graph from this
   * node has been traversed.  i.e., -1 indicates partially processed,
   * and 1 indicates fully processed.
   */
  private int walkStatus = 0;
  /**
   * The first and last tokens from the input stream that represent this
   * production.
   */
  private Token lastToken;
  private Token firstToken;

  public void setLine(int line) {
    this.line = line;
  }

  public int getLine() {
    return line;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getColumn() {
    return column;
  }

  List<Expansion> getParents() {
    return parents;
  }

  public void setAccessModifier(String accessModifier) {
    this.accessModifier = accessModifier;
  }

  public String getAccessModifier() {
    return accessModifier;
  }

  public void setLhs(String lhs) {
    this.lhs = lhs;
  }

  public String getLhs() {
    return lhs;
  }

  public List<Token> getReturnTypeTokens() {
    return returnTypeTokens;
  }

  public List<Token> getParameterListTokens() {
    return parameterListTokens;
  }

  public void setThrowsList(List<List<Token>> throwsList) {
    this.throwsList = throwsList;
  }

  public List<List<Token>> getThrowsList() {
    return throwsList;
  }

  public void setExpansion(Expansion expansion) {
    this.expansion = expansion;
  }

  public Expansion getExpansion() {
    return expansion;
  }

  boolean setEmptyPossible(boolean emptyPossible) {
    this.emptyPossible = emptyPossible;
    return emptyPossible;
  }

  boolean isEmptyPossible() {
    return emptyPossible;
  }

  void setLeftExpansions(NormalProduction[] leftExpansions) {
    this.leftExpansions = leftExpansions;
  }

  NormalProduction[] getLeftExpansions() {
    return leftExpansions;
  }

  void setWalkStatus(int walkStatus) {
    this.walkStatus = walkStatus;
  }

  int getWalkStatus() {
    return walkStatus;
  }

  public Token setFirstToken(Token firstToken) {
    this.firstToken = firstToken;
    return firstToken;
  }

  public Token getFirstToken() {
    return firstToken;
  }

  public void setLastToken(Token lastToken) {
    this.lastToken = lastToken;
  }

  public Token getLastToken() {
    return lastToken;
  }
}
