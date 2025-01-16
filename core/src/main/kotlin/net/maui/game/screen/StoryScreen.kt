package net.maui.game.screen

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import net.maui.game.BaseGame
import net.maui.game.action.SceneActions
import net.maui.game.actor.Background
import net.maui.game.actor.BaseActor
import net.maui.game.actor.DialogBox
import net.maui.game.actor.Kelsoe
import net.maui.game.actor.Scene
import net.maui.game.actor.SceneSegment


class StoryScreen(val baseGame: BaseGame) : BaseScreen() {
    lateinit var scene: Scene
    lateinit var background: Background
    lateinit var kelsoe: Kelsoe
    lateinit var dialogBox: DialogBox
    lateinit var continueKey: BaseActor
    lateinit var buttonTable: Table
    lateinit var theEnd: BaseActor

    override fun initialize() {
        background = Background(0f, 0f, mainStage)
        background.setOpacity(0f)
        BaseActor.setWorldBounds(background)
        kelsoe = Kelsoe(0f, 0f, mainStage)
        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setDialogSize(600f, 150f)
        dialogBox.setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 1f))
        dialogBox.isVisible = false
        continueKey = BaseActor(0f, 0f, uiStage)
        continueKey.loadTexture("assets/key-C.png")
        continueKey.setSize(32f, 32f)
        continueKey.isVisible = false
        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width, 0f)
        buttonTable = Table()
        buttonTable.isVisible = false
        uiTable.add().expandY()
        uiTable.row()
        uiTable.add(buttonTable)
        uiTable.row()
        uiTable.add(dialogBox)
        theEnd = BaseActor(0f, 0f, mainStage)
        theEnd.loadTexture("assets/the-end.png")
        theEnd.centerAtActor(background)
        theEnd.setScale(2f)
        theEnd.setOpacity(0f)
        scene = Scene()
        mainStage.addActor(scene)
        hallway()
    }

    private fun hallway() {
        background.setAnimation( background.hallway )
        dialogBox.setText("")
        kelsoe.addAction( SceneActions.moveToOutsideLeft(0f) )

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence( "My name is Kelsoe Kismet. I am a student at Aureus Ludus Academy." )
        addTextSequence( "I can be a little forgetful sometimes. Right now, I'm looking for my homework." )

        scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
        scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))

        scene.addSegment(SceneSegment(background, Actions.run { classroom() }))
        scene.start()
    }

    private fun classroom() {
        scene.clearSegments()
        background.setAnimation(background.classroom)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))
        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))
        addTextSequence("This is my classroom. My homework isn't here, though.")
        addTextSequence("Where should I look for my homework next?")
        scene.addSegment(SceneSegment(buttonTable, Actions.show()))

        // set up options
        val scienceLabButton = TextButton(
            "Look in the Science Lab",
            BaseGame.textButtonStyle
        )
        scienceLabButton.addListener { e: Event ->
            if (e !is InputEvent || e.type != InputEvent.Type.touchDown) return@addListener false

            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence("That's a great idea. I'll check the science lab.")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { scienceLab() }))

            false
        }
        val libraryButton = TextButton("Look in the Library", BaseGame.textButtonStyle)
        libraryButton.addListener { e: Event ->
            if (e !is InputEvent || e.type != InputEvent.Type.touchDown) return@addListener false
            scene.addSegment(
                SceneSegment(
                    buttonTable,
                    Actions.hide()
                )
            )
            addTextSequence("That's a great idea. Maybe I left it in the library.")
            scene.addSegment(
                SceneSegment(
                    dialogBox,
                    Actions.hide()
                )
            )
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
            scene.addSegment(
                SceneSegment(
                    background,
                    Actions.fadeOut(1f)
                )
            )
            scene.addSegment(
                SceneSegment(
                    background,
                    Actions.run { library() })
            )
            false
        }
        buttonTable.clearChildren()
        buttonTable.add(scienceLabButton)
        buttonTable.row()
        buttonTable.add(libraryButton)
        scene.start()
    }

    private fun library() {
        scene.clearSegments()
        background.setAnimation(background.library)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))
        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))
        addTextSequence("This is the library.")
        addTextSequence("Let me check the table where I was working earlier . . .")
        scene.addSegment(
            SceneSegment(
                kelsoe, SceneActions.setAnimation(kelsoe.lookRight)
            )
        )
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenRight(2f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Aha! Here it is!")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(0.5f)))
        addTextSequence("Thanks for helping me find it!")
        scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
        scene.addSegment(SceneSegment(theEnd, Actions.fadeIn(4f)))
        scene.addSegment(SceneSegment(background, Actions.delay(10f)))
        scene.addSegment(SceneSegment(background, Actions.run {
            baseGame.setActiveScreen(
                MenuScreen(baseGame)
            )
        }))
        scene.start()
    }

    private fun scienceLab() {
        scene.clearSegments()
        background.setAnimation(background.scienceLab)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))
        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))
        addTextSequence("This is the science lab.")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)))
        addTextSequence("My homework isn't here, though.")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Now where should I go?")

        scene.addSegment(SceneSegment(buttonTable, Actions.show()))


        // set up options
        val classroomButton = TextButton(
            "Return to the Classroom",
            BaseGame.textButtonStyle
        )
        classroomButton.addListener { e: Event ->
            if (e !is InputEvent || e.type != InputEvent.Type.touchDown) return@addListener false
            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence( "Maybe someone found it and put it in the classroom. I'll go check." )
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { classroom() }))

            false
        }
        val libraryButton = TextButton("Look in the Library", BaseGame.textButtonStyle)
        libraryButton.addListener { e: Event? ->
            if (e !is InputEvent || e.type != InputEvent.Type.touchDown) return@addListener false
            scene.addSegment(
                SceneSegment(
                    buttonTable,
                    Actions.hide()
                )
            )
            addTextSequence("That's a great idea. Maybe I left it in the library.")
            scene.addSegment(
                SceneSegment(
                    dialogBox,
                    Actions.hide()
                )
            )
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
            scene.addSegment(
                SceneSegment(
                    background,
                    Actions.fadeOut(1f)
                )
            )
            scene.addSegment(
                SceneSegment(
                    background,
                    Actions.run { library() })
            )
            false
        }
        buttonTable.clearChildren()
        buttonTable.add(classroomButton)
        buttonTable.row()
        buttonTable.add(libraryButton)
        scene.start()
    }

    fun addTextSequence(s: String) {
        scene.addSegment(SceneSegment(dialogBox, SceneActions.typewriter(s)))
        scene.addSegment(SceneSegment(continueKey, Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))
    }

    override fun keyDown(keycode: Int): Boolean {
        if ( keycode == Input.Keys.C )
            scene.loadNextSegment()
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
