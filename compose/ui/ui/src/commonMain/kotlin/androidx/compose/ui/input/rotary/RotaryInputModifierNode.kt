/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.ui.input.rotary

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode

/**
 * Implement this interface to create a [Modifier.Node] that can intercept rotary scroll events.
 *
 * The event is routed to the focused item. Before reaching the focused item,
 * [onPreRotaryScrollEvent]() is called for parents of the focused item. If the parents don't
 * consume the event, [onPreRotaryScrollEvent]() is called for the focused item. If the event is
 * still not consumed, [onRotaryScrollEvent]() is called on the focused item's parents.
 */
interface RotaryInputModifierNode : DelegatableNode {
    /**
     * This function is called when a [RotaryScrollEvent] is received by this node during the upward
     * pass. While implementing this callback, return true to stop propagation of this event. If you
     * return false, the key event will be sent to this [RotaryInputModifierNode]'s parent.
     */
    fun onRotaryScrollEvent(event: RotaryScrollEvent): Boolean

    /**
     * This function is called when a [RotaryScrollEvent] is received by this node during the
     * downward pass. It gives ancestors of a focused component the chance to intercept an event.
     * Return true to stop propagation of this event. If you return false, the event will be sent to
     * this [RotaryInputModifierNode]'s child. If none of the children consume the event, it will be
     * sent back up to the root using the [onRotaryScrollEvent] function.
     */
    fun onPreRotaryScrollEvent(event: RotaryScrollEvent): Boolean
}
