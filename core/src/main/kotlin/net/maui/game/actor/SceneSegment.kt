package net.maui.game.actor

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor

class SceneSegment(private val actor: Actor, private val  action: Action) {

    fun start() {
        actor.clearActions()
        actor.addAction(action)
    }

    fun isFinished(): Boolean {
        return (actor.actions.size == 0)
    }

    fun finish() {
        // simulate 100000 seconds elapsed time to complete in-progress action
        if (actor.hasActions()) actor.actions.first().act(100000f)
        // remove any remaining actions
        actor.clearActions()
    }
}
