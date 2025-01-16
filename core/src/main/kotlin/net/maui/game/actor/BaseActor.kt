package net.maui.game.actor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array


open class BaseActor(x: Float, y: Float, stage: Stage) : Group() {
    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime = 0f
    private var animationPaused = false
    private var  velocityVec = Vector2(0f, 0f)
    private val accelerationVec = Vector2(0f, 0f)
    private var acceleration = 0f
    private var maxSpeed = 1000f
    private var deceleration = 0f
    private var boundaryPolygon: Polygon? = null

    init {
        this.setPosition(x, y)
        stage.addActor(this)
    }

    fun setAnimation(anim: Animation<TextureRegion>) {
        animation = anim
        val tr = animation!!.getKeyFrame(0f)
        val w = tr.regionWidth.toFloat()
        val h = tr.regionHeight.toFloat()
        setSize(w, h)
        setOrigin(w / 2, h / 2)
        if (boundaryPolygon == null)
            setBoundaryRectangle()
    }

    fun setAnimationPaused(pause: Boolean) {
        animationPaused = pause
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (!animationPaused) {
            elapsedTime += dt
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        // apply color tint effect
        batch.setColor(color.r, color.g, color.b, color.a)
        if (animation != null && isVisible)
            batch.draw(
                animation!!.getKeyFrame(elapsedTime),
                x,
                y,
                originX,
                originY,
                width,
                height,
                scaleX,
                scaleY,
                rotation
            )
        super.draw(batch, parentAlpha)
    }

    fun loadAnimationFromFiles(
        fileNames: Array<String>, frameDuration: Float, loop: Boolean
    ): Animation<TextureRegion> {
        val fileCount = fileNames.size
        val textureArray = Array<TextureRegion>()
        for (n in 0 until fileCount) {
            val fileName = fileNames[n]
            val texture = Texture(Gdx.files.internal(fileName))
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
            textureArray.add(TextureRegion(texture))
        }

        val anim = Animation(frameDuration, textureArray)
        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP)
        else anim.setPlayMode(Animation.PlayMode.NORMAL)

        if (animation == null) setAnimation(anim)
        return anim
    }

    fun loadAnimationFromSheet(
        fileName: String, rows: Int, cols: Int, frameDuration: Float, loop: Boolean
    ): Animation<TextureRegion> {
        val texture = Texture(Gdx.files.internal(fileName), true)
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        val frameWidth = texture.width / cols
        val frameHeight = texture.height / rows
        val temp = TextureRegion.split(texture, frameWidth, frameHeight)
        val textureArray = Array<TextureRegion>()
        for (r in 0 until rows)
            for (c in 0 until cols)
                textureArray.add(temp[r][c])
        val anim = Animation(
            frameDuration, textureArray
        )
        if (loop) anim.setPlayMode(Animation.PlayMode.LOOP)
        else anim.setPlayMode(Animation.PlayMode.NORMAL)
        if (animation == null) {
            setAnimation(anim)
        }
        return anim
    }
    fun loadTexture(fileName: String): Animation<TextureRegion> {
        val fileNames = arrayOf(fileName)
        return loadAnimationFromFiles(Array<String>(fileNames), 1f, true)
    }

    fun isAnimationFinished(): Boolean {
        return animation!!.isAnimationFinished(elapsedTime)
    }

    fun setSpeed(speed: Float) {
        // if length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0f) velocityVec[speed] = 0f
        else velocityVec.setLength(speed)
    }

    fun getSpeed(): Float {
        return velocityVec.len()
    }

    fun setMotionAngle(angle: Float) {
        velocityVec.setAngleDeg(angle)
    }

    fun getMotionAngle(): Float {
        return velocityVec.angleDeg()
    }

    fun isMoving() = (getSpeed() > 0)

    fun setAcceleration(acc: Float) {
        acceleration = acc
    }

    fun accelerateAtAngle(angle: Float) {
        accelerationVec.add(Vector2(acceleration, 0f).setAngleDeg(angle))
    }

    fun accelerateForward() = accelerateAtAngle(rotation)

    fun setMaxSpeed(ms: Float) {
        maxSpeed = ms
    }

    fun setDeceleration(dec: Float) {
        deceleration = dec
    }
    fun applyPhysics(dt: Float) {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)
        var speed = getSpeed()
        // decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0f) speed -= deceleration * dt
        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0f, maxSpeed)
        // update velocity
        setSpeed(speed)
        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt)
        // reset acceleration
        accelerationVec[0f] = 0f
    }

    fun setBoundaryRectangle() {
        val w = width
        val h = height
        val vertices = floatArrayOf(0f, 0f, w, 0f, w, h, 0f, h)
        boundaryPolygon = Polygon(vertices)
    }

    /* This method must be called after the size of the actor
    *  has been set, with setSize or by setAnimation
    * */
    fun setBoundaryPolygon(numSides: Int) {
        val w = width
        val h = height
        val vertices = FloatArray(2 * numSides)
        for (i in 0 until numSides) {
            val angle = i * 6.28f / numSides
            // x-coordinate
            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2
            // y-coordinate
            vertices[2 * i + 1] = h / 2 * MathUtils.sin(angle) + h / 2
        }
        boundaryPolygon = Polygon(vertices)
    }

    fun getBoundaryPolygon(): Polygon {
        boundaryPolygon!!.setPosition(x, y)
        boundaryPolygon!!.setOrigin(originX, originY)
        boundaryPolygon!!.rotation = rotation
        boundaryPolygon!!.setScale(scaleX, scaleY)
        return boundaryPolygon!!
    }

    fun overlaps(other: BaseActor): Boolean {
        val poly1 = this.getBoundaryPolygon()
        val poly2 = other.getBoundaryPolygon()
        // initial test to improve performance
        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle)) return false
        return Intersector.overlapConvexPolygons(poly1, poly2)
    }

    fun centerAtPosition(x: Float, y: Float) {
        setPosition(x - width / 2, y - height / 2)
    }

    fun centerAtActor(other: BaseActor) {
        centerAtPosition(other.x + other.width / 2, other.y + other.height / 2)
    }

    fun setOpacity(opacity: Float) {
        color.a = opacity
    }

    fun preventOverlap(other: BaseActor): Vector2? {
        val poly1 = this.getBoundaryPolygon()
        val poly2 = other.getBoundaryPolygon()
        // initial test to improve performance
        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle)) return null
        val mtv = MinimumTranslationVector()
        val polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv)
        if (!polygonOverlap) return null
        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth)
        return mtv.normal
    }

    fun boundToWorld() {
        // check left edge
        if (x < 0) x = 0f
        // check right edge
        if (x + width > worldBounds!!.width) x = worldBounds!!.width - width
        // check bottom edge
        if (y < 0) y = 0f
        // check top edge
        if (y + height > worldBounds!!.height) y = worldBounds!!.height - height
    }

    fun alignCamera() {
        val cam = stage.camera
        // center camera on actor
        cam.position[x + this.originX, y + this.originY] = 0f
        // bound camera to layout
        cam.position.x = MathUtils.clamp(
            cam.position.x,
            cam.viewportWidth / 2, worldBounds!!.width - cam.viewportWidth / 2
        )
        cam.position.y = MathUtils.clamp(
            cam.position.y,
            cam.viewportHeight / 2, worldBounds!!.height - cam.viewportHeight / 2
        )
        cam.update()
    }

    fun wrapAroundWorld() {
        if (x + width < 0) x = worldBounds!!.width
        if (x > worldBounds!!.width) x = -width
        if (y + height < 0) y = worldBounds!!.height
        if (y > worldBounds!!.height) y = -height
    }

    fun removeOffWorld() {
        if (x + width < 0) remove()
        if (x > worldBounds!!.width) remove()
        if (y + height < 0) remove()
        if (y > worldBounds!!.height) remove()
    }

    fun isWithinDistance(distance: Float, other : BaseActor) : Boolean {
        val poly1: Polygon = this.getBoundaryPolygon()
        val scaleX = (this.width + 2 * distance) / this.width
        val scaleY = (this.height + 2 * distance) / this.height
        poly1.setScale(scaleX, scaleY)

        val poly2 = other.getBoundaryPolygon()

        // initial test to improve performance
        if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
            return false
        return Intersector.overlapConvexPolygons( poly1, poly2 )
    }

    companion object {
        private var worldBounds: Rectangle? = null

        fun getWorldBounds(): Rectangle {
            return worldBounds!!
        }

        fun setWorldBounds(width: Float, height: Float) {
            worldBounds = Rectangle(0f, 0f, width, height)
        }

        fun setWorldBounds(ba: BaseActor) {
            setWorldBounds(ba.width, ba.height)
        }
    }
}
