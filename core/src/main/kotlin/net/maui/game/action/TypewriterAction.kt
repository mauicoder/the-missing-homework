package net.maui.game.action

import net.maui.game.actor.DialogBox



class TypewriterAction(textToDisplay: String) : SetTextAction(textToDisplay) {
    private var elapsedTime = 0f
    private var charactersPerSecond = 30

    override fun act(delta: Float): Boolean {
        elapsedTime += delta
        var numberOfCharacters = (elapsedTime * charactersPerSecond).toInt()
        if (numberOfCharacters > textToDisplay.length) numberOfCharacters = textToDisplay.length
        val partialText = textToDisplay.substring(0, numberOfCharacters)
        val db = target as DialogBox
        db.setText(partialText)
        // action is complete when all characters have been displayed
        return ( numberOfCharacters >= textToDisplay.length)
    }
}
