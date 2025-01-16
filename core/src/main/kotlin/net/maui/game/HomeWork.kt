package net.maui.game

import net.maui.game.screen.MenuScreen

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class HomeWork : BaseGame(){
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen(this))
    }
}
