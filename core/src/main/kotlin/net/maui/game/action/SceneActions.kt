package net.maui.game.action

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import net.maui.game.actor.BaseActor


class SceneActions : Actions() {
    companion object {
        fun setText(s: String): Action {
            return SetTextAction(s)
        }

        fun pause(): Action {
            return forever(delay(1f))
        }

        fun moveToScreenLeft(duration: Float): Action {
            return moveToAligned(0f, 0f, Align.bottomLeft, duration)
        }

        fun moveToScreenRight(duration: Float): Action {
            return moveToAligned(
                BaseActor.getWorldBounds().width, 0f,
                Align.bottomRight, duration
            )
        }

        fun moveToScreenCenter(duration: Float): Action {
            return moveToAligned(
                BaseActor.getWorldBounds().width / 2, 0f,
                Align.bottom, duration
            )
        }

        fun moveToOutsideLeft(duration: Float): Action {
            return moveToAligned(0f, 0f, Align.bottomRight, duration)
        }

        fun moveToOutsideRight(duration: Float): Action {
            return moveToAligned(
                BaseActor.getWorldBounds().width, 0f,
                Align.bottomLeft, duration
            )
        }

        fun setAnimation(a: Animation<TextureRegion>): Action {
            return SetAnimationAction(a)
        }

        fun typewriter(s: String): Action {
            return TypewriterAction(s)
        }
    }
}
