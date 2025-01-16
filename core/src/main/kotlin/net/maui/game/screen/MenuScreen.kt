package net.maui.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import net.maui.game.BaseGame
import net.maui.game.actor.BaseActor


class MenuScreen(private val baseGame: BaseGame): BaseScreen() {
    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/notebook.jpg")
        background.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("assets/missing-homework.png")

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            if (e !is InputEvent ||
                !e.type.equals(InputEvent.Type.touchDown)
            ) return@addListener false
            baseGame.setActiveScreen(StoryScreen(baseGame))
            false
        }
        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        quitButton.addListener { e: Event ->
            if (e !is InputEvent || e.type != InputEvent.Type.touchDown) return@addListener false
            Gdx.app.exit()
            false
        }
        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) baseGame.setActiveScreen(StoryScreen(baseGame))
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit()
        return false
    }

    override fun update(dt: Float) {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}
