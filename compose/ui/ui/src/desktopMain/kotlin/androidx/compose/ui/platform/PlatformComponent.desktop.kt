/*
 * Copyright 2020 The Android Open Source Project
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

package androidx.compose.ui.platform

import androidx.compose.ui.unit.Density
import java.awt.Cursor
import java.awt.Point
import java.awt.im.InputMethodRequests
import kotlinx.coroutines.awaitCancellation

internal actual interface PlatformComponent : PlatformInputComponent, PlatformComponentWithCursor

internal actual interface PlatformComponentWithCursor {
    var componentCursor: Cursor
}

internal actual object DummyPlatformComponent : PlatformComponent {
    override var componentCursor: Cursor = Cursor(Cursor.CROSSHAIR_CURSOR)
    var enabledInput: InputMethodRequests? = null

    override fun enableInput(inputMethodRequests: InputMethodRequests) {
        enabledInput = inputMethodRequests
    }

    override fun disableInput(inputMethodRequests: InputMethodRequests?) {
        enabledInput = null
    }

    override suspend fun textInputSession(
        session: suspend PlatformTextInputSessionScope.() -> Nothing
    ): Nothing {
        awaitCancellation()
    }

    override val locationOnScreen = Point(0, 0)
    override val density: Density
        get() = Density(1f, 1f)
}
