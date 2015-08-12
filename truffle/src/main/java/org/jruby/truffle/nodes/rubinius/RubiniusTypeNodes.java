/*
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.rubinius;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.nodes.core.CoreClass;
import org.jruby.truffle.nodes.core.CoreMethod;
import org.jruby.truffle.nodes.core.ModuleNodes;
import org.jruby.truffle.nodes.core.YieldingCoreMethodNode;
import org.jruby.truffle.runtime.RubyContext;
import com.oracle.truffle.api.object.DynamicObject;

@CoreClass(name = "Rubinius::Type")
public abstract class RubiniusTypeNodes {

    @CoreMethod(names = "each_ancestor", onSingleton = true, required = 1, needsBlock = true)
    public abstract static class EachAncestorNode extends YieldingCoreMethodNode {

        public EachAncestorNode(RubyContext context, SourceSection sourceSection) {
            super(context, sourceSection);
        }

        @Specialization(guards = {"isRubyModule(module)", "isRubyProc(block)"})
        public DynamicObject eachAncestor(VirtualFrame frame, DynamicObject module, DynamicObject block) {
            for (DynamicObject ancestor : ModuleNodes.getFields(module).ancestors()) {
                yield(frame, block, ancestor);
            }
            return nil();
        }

    }

}
