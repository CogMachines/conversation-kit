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

import com.synclab.conversationkit.impl.node.DialogTreeNode;
import com.synclab.conversationkit.model.SnippetType;

/**
 *
 * @author tyreus
 */
public class TemplatedDialogTreeNode<T extends MapBackedState> extends DialogTreeNode<T> {

    public TemplatedDialogTreeNode(int id, SnippetType type, String content) {
        super(id, type, content);
    }

    @Override
    public String renderContent(T state) {
        if (state == null) {
            return content;
        }
        String renderedContent = content;
        for (String key : state.keySet()) {
            renderedContent = renderedContent.replace("{{" + key + "}}", state.get(key).toString());
        }

        return renderedContent;
    }

}
