package net.maui.game.actor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import net.maui.game.BaseGame

class DialogBox(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    private var dialogLabel = Label(" ", BaseGame.labelStyle)
    private val padding = 16f

    init {
        loadTexture("assets/dialog-translucent.png")
        dialogLabel.wrap = true
        dialogLabel.setAlignment(Align.topLeft)
        dialogLabel.setPosition(padding, padding)
        this.setDialogSize(width, height)
        this.addActor(dialogLabel)
    }

    internal fun setDialogSize(width: Float, height: Float) {
        this.setSize(width, height)
        dialogLabel.width = width - 2 * padding
        dialogLabel.height = height - 2 * padding
    }
    fun setText(text: String) {
        dialogLabel.setText(text)
    }

    fun setFontScale(scale: Float) {
        dialogLabel.setFontScale(scale)
    }

    fun setFontColor(color: Color) {
        dialogLabel.color = color
    }

    fun setBackgroundColor(color: Color) {
        this.color = color
    }

    fun alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft)
    }

    fun alignCenter() {
        dialogLabel.setAlignment(Align.center)
    }
}
