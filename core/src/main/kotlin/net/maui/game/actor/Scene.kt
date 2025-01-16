package net.maui.game.actor

import com.badlogic.gdx.scenes.scene2d.Actor


class Scene: Actor() {
    private val segmentList = ArrayList<SceneSegment>()
    private var index = -1
    fun addSegment(segment: SceneSegment?) {
        segmentList.add(segment!!)
    }

    fun clearSegments() {
        segmentList.clear()
    }

    fun start() {
        index = 0
        segmentList[index].start()
    }

    override fun act(dt: Float) {
        if (isSegmentFinished() && !isLastSegment()) loadNextSegment()
    }

    fun isSegmentFinished(): Boolean {
        return segmentList[index].isFinished()
    }

    fun isLastSegment(): Boolean {
        return (index >= segmentList.size - 1)
    }
    fun loadNextSegment() {
        if (isLastSegment()) return
        segmentList[index].finish()
        index++
        segmentList[index].start()
    }
    fun isSceneFinished(): Boolean {
        return (isLastSegment() && isSegmentFinished())
    }
}
