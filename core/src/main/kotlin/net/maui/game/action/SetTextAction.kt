package net.maui.game.action

import com.badlogic.gdx.scenes.scene2d.Action
import net.maui.game.actor.DialogBox

open class SetTextAction(protected val textToDisplay: String) : Action() {
    override fun act(delta: Float): Boolean {
        val dialogBox = target as DialogBox
        dialogBox.setText(textToDisplay)
        return true
    }
}
