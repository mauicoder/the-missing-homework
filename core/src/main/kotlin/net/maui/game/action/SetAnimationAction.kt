package net.maui.game.action

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Action
import net.maui.game.actor.BaseActor



class SetAnimationAction(private val  animationToDisplay : Animation<TextureRegion>) : Action() {
    override fun act(delta: Float): Boolean {
        val ba = target as BaseActor
        ba.setAnimation(animationToDisplay)
        return true
    }
}
