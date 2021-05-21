package pl.gieted.flappy_bird.engine.collisions.colliders

import kotlin.math.*

sealed interface Collider {
    companion object {
        fun areColliding(colliderOne: Collider, colliderTwo: Collider): Boolean = when {
            colliderOne is MultiCollider -> colliderOne.subColliders.any { areColliding(it, colliderTwo) }
            colliderTwo is MultiCollider -> colliderTwo.subColliders.any { areColliding(colliderOne, it) }
            colliderOne is CircularCollider && colliderTwo is RectangularCollider -> testRectangleToCircle(
                colliderTwo.size.x,
                colliderTwo.size.y,
                0.0,
                colliderTwo.position.x,
                colliderTwo.position.y,
                colliderOne.position.x,
                colliderOne.position.y,
                colliderOne.diameter / 2
            )
            colliderOne is RectangularCollider && colliderTwo is CircularCollider -> testRectangleToCircle(
                colliderOne.size.x,
                colliderOne.size.y,
                0.0,
                colliderOne.position.x,
                colliderOne.position.y,
                colliderTwo.position.x,
                colliderTwo.position.y,
                colliderTwo.diameter / 2
            )
            else -> throw Exception("Tried to match incompatible collider types")
        }

        private fun testRectangleToPoint(
            rectWidth: Double,
            rectHeight: Double,
            rectRotation: Double,
            rectCenterX: Double,
            rectCenterY: Double,
            pointX: Double,
            pointY: Double
        ): Boolean {
            if (rectRotation == 0.0)
                return abs(rectCenterX - pointX) < rectWidth / 2 && abs(rectCenterY - pointY) < rectHeight / 2
            val tx = cos(rectRotation) * pointX - sin(rectRotation) * pointY
            val ty = cos(rectRotation) * pointY + sin(rectRotation) * pointX
            val cx = cos(rectRotation) * rectCenterX - sin(rectRotation) * rectCenterY
            val cy = cos(rectRotation) * rectCenterY + sin(rectRotation) * rectCenterX
            return abs(cx - tx) < rectWidth / 2 && abs(cy - ty) < rectHeight / 2
        }

        private fun testCircleToSegment(
            circleCenterX: Double,
            circleCenterY: Double,
            circleRadius: Double,
            lineAX: Double,
            lineAY: Double,
            lineBX: Double,
            lineBY: Double
        ): Boolean {
            val lineSize = sqrt((lineAX - lineBX).pow(2.0) + (lineAY - lineBY).pow(2.0))
            val distance: Double
            if (lineSize == 0.0) {
                distance = sqrt((circleCenterX - lineAX).pow(2.0) + (circleCenterY - lineAY).pow(2.0))
                return distance < circleRadius
            }
            val u =
                ((circleCenterX - lineAX) * (lineBX - lineAX) + (circleCenterY - lineAY) * (lineBY - lineAY)) / (lineSize * lineSize)
            distance = when {
                u < 0 -> {
                    sqrt((circleCenterX - lineAX).pow(2.0) + (circleCenterY - lineAY).pow(2.0))
                }
                u > 1 -> {
                    sqrt((circleCenterX - lineBX).pow(2.0) + (circleCenterY - lineBY).pow(2.0))
                }
                else -> {
                    val ix = lineAX + u * (lineBX - lineAX)
                    val iy = lineAY + u * (lineBY - lineAY)
                    sqrt((circleCenterX - ix).pow(2.0) + (circleCenterY - iy).pow(2.0))
                }
            }
            return distance < circleRadius
        }

        private fun testRectangleToCircle(
            rectWidth: Double,
            rectHeight: Double,
            rectRotation: Double,
            rectCenterX: Double,
            rectCenterY: Double,
            circleCenterX: Double,
            circleCenterY: Double,
            circleRadius: Double
        ): Boolean {
            val tx: Double
            val ty: Double
            val cx: Double
            val cy: Double
            if (rectRotation == 0.0) {
                tx = circleCenterX
                ty = circleCenterY
                cx = rectCenterX
                cy = rectCenterY
            } else {
                tx = cos(rectRotation) * circleCenterX - sin(rectRotation) * circleCenterY
                ty = cos(rectRotation) * circleCenterY + sin(rectRotation) * circleCenterX
                cx = cos(rectRotation) * rectCenterX - sin(rectRotation) * rectCenterY
                cy = cos(rectRotation) * rectCenterY + sin(rectRotation) * rectCenterX
            }
            return testRectangleToPoint(
                rectWidth,
                rectHeight,
                rectRotation,
                rectCenterX,
                rectCenterY,
                circleCenterX,
                circleCenterY
            ) ||
                    testCircleToSegment(
                        tx,
                        ty,
                        circleRadius,
                        cx - rectWidth / 2,
                        cy + rectHeight / 2,
                        cx + rectWidth / 2,
                        cy + rectHeight / 2
                    ) ||
                    testCircleToSegment(
                        tx,
                        ty,
                        circleRadius,
                        cx + rectWidth / 2,
                        cy + rectHeight / 2,
                        cx + rectWidth / 2,
                        cy - rectHeight / 2
                    ) ||
                    testCircleToSegment(
                        tx,
                        ty,
                        circleRadius,
                        cx + rectWidth / 2,
                        cy - rectHeight / 2,
                        cx - rectWidth / 2,
                        cy - rectHeight / 2
                    ) ||
                    testCircleToSegment(
                        tx,
                        ty,
                        circleRadius,
                        cx - rectWidth / 2,
                        cy - rectHeight / 2,
                        cx - rectWidth / 2,
                        cy + rectHeight / 2
                    )
        }
    }
}
