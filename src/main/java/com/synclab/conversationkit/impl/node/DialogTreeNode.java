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
package com.synclab.conversationkit.impl.node;

import com.synclab.conversationkit.model.IConversationState;
import com.synclab.conversationkit.model.SnippetType;
import java.util.ArrayList;
import java.util.List;

/**
 * A DialogTreeNode is a simple implementation of IConversationNode that holds
 * a text string to represent the displayed conversation snippet as well
 * as an optional list of suggested responses. By default the snippet and 
 * suggested responses will be rendered unchanged, but this class can easily be
 * extended to use a templating engine for string substitution.
 * <p>
 * A Dialog Tree is a type of branching conversation often seen in adventure
 * video games. The user is given a choice of what to say and makes 
 * subsequent choices until the conversation ends. The responses to the user
 * are scripted based on the choices made. Each DialogTreeNode represents
 * either a question or statement presented to the user by the bot.
 * 
 * @author pdtyreus
 */
public class DialogTreeNode<T extends IConversationState> extends ConversationNode<T> {

    private final List<String> suggestedResponses = new ArrayList();
    protected final String content;

    /**
     * 
     * @param id the unique ID of the node from the underlying data store
     * @param type the node type
     * @param content the text prompt displayed to the user
     */
    public DialogTreeNode(int id, SnippetType type, String content) {
        super(id, type);
        this.content = content;
    }
    
    public String renderContent(T state) {
        return content;
    }

    public List<String> getSuggestedResponses() {
        return suggestedResponses;
    }
    
    
}
