package net.maui.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import net.maui.game.screen.BaseScreen


abstract class BaseGame : Game(){
    private val game: BaseGame by lazy { this }

    override fun create() {
        // prepare for multiple classes/stages to receive discrete input
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im

        //Font configuration
        val fontGenerator =
            FreeTypeFontGenerator(Gdx.files.internal("assets/OpenSans.ttf"))

        val customFont = fontGenerator.generateFont(createFontConfig())
        labelStyle.font = customFont

        val buttonPatch =
            NinePatch(Texture(Gdx.files.internal("assets/button.png")), 24, 24, 24, 24)
        textButtonStyle.up = NinePatchDrawable(buttonPatch)
        textButtonStyle.font = customFont
        textButtonStyle.fontColor = Color.GRAY
    }

    private fun createFontConfig(): FreeTypeFontParameter {
        val fontParameters = FreeTypeFontParameter()
        fontParameters.size = 24
        fontParameters.color = Color.WHITE
        fontParameters.borderWidth = 2f
        fontParameters.borderColor = Color.BLACK
        fontParameters.borderStraight = true
        fontParameters.minFilter = TextureFilter.Linear
        fontParameters.magFilter = TextureFilter.Linear
        return fontParameters
    }

    fun setActiveScreen(s: BaseScreen) {
        game.setScreen(s)
    }

    companion object {
        val labelStyle = LabelStyle()
        val textButtonStyle = TextButton.TextButtonStyle()

    }
}
