/*
 * The MIT License
 *
 * Copyright 2016 Synclab Consulting LLC.
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
package com.synclab.conversationkit.impl.edge;

import com.synclab.conversationkit.model.IConversationEdge;
import com.synclab.conversationkit.model.IConversationNode;
import com.synclab.conversationkit.model.IConversationState;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matches responses based on a regular expression pattern. If a stateKey is
 * provided, the <code>onMatch</code> method sets the value of this key in the
 * conversation state equal to the first group found in the match or to the
 * stateValue constructor argument if specified.
 *
 * @author pdtyreus
 * @param <S> an implementation of IConversationState
 */
public class RegexEdge<S extends IConversationState> implements IConversationEdge<S> {

    private final IConversationNode<S> endNode;
    protected final Pattern pattern;
    protected final String stateKey;
    protected final String stateValue;

    public RegexEdge(String matchRegex, String stateKey, String stateValue, IConversationNode<S> endNode) {
        this.endNode = endNode;
        this.stateKey = stateKey;
        this.pattern = Pattern.compile(matchRegex);
        this.stateValue = stateValue;
    }

    public RegexEdge(String matchRegex, String stateKey, IConversationNode<S> endNode) {
        this.endNode = endNode;
        this.stateKey = stateKey;
        this.pattern = Pattern.compile(matchRegex);
        this.stateValue = null;
    }

    public RegexEdge(String matchRegex, IConversationNode<S> endNode) {
        this.endNode = endNode;
        this.stateKey = null;
        this.stateValue = null;
        this.pattern = Pattern.compile(matchRegex);
    }

    public IConversationNode<S> getEndNode() {
        return endNode;
    }

    @Override
    public boolean isMatchForState(S state) {
        Matcher matcher = pattern.matcher(state.getCurrentResponse());
        return matcher.find();
    }

    @Override
    public S onMatch(S state) {
        if (stateValue != null) {
            state.set(stateKey, stateValue);
        } else {
            Matcher matcher = pattern.matcher(state.getCurrentResponse());
            if ((stateKey != null) && matcher.find()) {
                state.set(stateKey, matcher.group());
            }
        }

        return state;
    }

    public String toString() {
        return "RegexEdge {" + pattern.pattern() + '}';
    }

}
