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

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import com.synclab.conversationkit.model.IConversationSnippetRenderer;
import com.synclab.conversationkit.model.IResponseEvaluator;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author tyreus
 */
public class JsonDialogTreeBuilder {

    private static final Logger logger = Logger.getLogger(JsonDialogTreeBuilder.class.getName());
    public DialogTree<UserDialogTreeState> fromJson(Reader reader) throws IOException {
        return fromJson(reader,null,null);
    }
    public DialogTree<UserDialogTreeState> fromJson(Reader reader, IConversationSnippetRenderer<UserDialogTreeState> renderer, IResponseEvaluator evaluator) throws IOException {

        JsonValue value = Json.parse(reader);

        JsonObject keyTree = value.asObject();

        Map<Integer, DialogTreeNode<UserDialogTreeState>> nodeMap = new HashMap();

        //run through once to create nodes
        for (Member member : keyTree) {
            String name = member.getName();
            Integer id = Integer.parseInt(name);
            JsonObject node = member.getValue().asObject();

            //make the node into something
            String type = node.get("type").asString();
            String content = node.get("content").asString();
            DialogTreeNode dtNode = new DialogTreeNode(DialogTreeNodeType.valueOf(type), id, content);
            if (renderer != null) {
                dtNode.setRenderer(renderer);
            }
            if (evaluator != null) {
                dtNode.setResponseEvaluator(evaluator);
            }
            nodeMap.put(id, dtNode);
        }

        logger.info(String.format("Created {0} named nodes", nodeMap.keySet().size()));

        //connect the nodes
        for (Member member : keyTree) {
            String name = member.getName();
            JsonObject node = member.getValue().asObject();
            Integer id = Integer.parseInt(name);

            //make the node into something
            String type = node.get("type").asString();
            DialogTreeNodeType nodeType = DialogTreeNodeType.valueOf(type);
            DialogTreeNode dtNode = nodeMap.get(id);
            if (node.get("next") != null) {
                int nextId = node.get("next").asInt();
                dtNode.addLeafNode(nodeMap.get(nextId));
                switch (nodeType) {
                    case STATEMENT:
                        break;
                    case ANSWER:
                        int prevId = node.get("question").asInt();
                        nodeMap.get(prevId).addLeafNode(dtNode);
                        break;
                }
            }

        }

        return new DialogTree(nodeMap.get(1));

    }

}
