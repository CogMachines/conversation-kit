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
package com.synclab.conversationkit.impl;

import com.synclab.conversationkit.model.IConversationSnippet;
import com.synclab.conversationkit.model.IConversationState;
import com.synclab.conversationkit.model.IUnmatchedResponseHandler;
import com.synclab.conversationkit.model.SnippetType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pdtyreus
 */
public class BasicUnmatchedResponseHandler<S extends IConversationState> implements IUnmatchedResponseHandler<S>{

    private String content = "I'm sorry, I did not understand your previous response";

    public BasicUnmatchedResponseHandler() {
    }

    public BasicUnmatchedResponseHandler(String content) {
        this.content = content;
    }
    
    public Iterable<IConversationSnippet> handleUnmatchedResponse(String response, S state) {
        List<IConversationSnippet> snippets = new ArrayList();
        IConversationSnippet snippet = new IConversationSnippet() {

            public String renderContent(IConversationState state) {
                return content;
            }

            public SnippetType getType() {
                return SnippetType.STATEMENT;
            }

            public Iterable getSuggestedResponses() {
                return null;
            }


        };

        snippets.add(snippet);
        return snippets;
    }
}
