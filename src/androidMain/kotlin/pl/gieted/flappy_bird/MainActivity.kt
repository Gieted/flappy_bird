package pl.gieted.flappy_bird

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import pl.gieted.flappy_bird.engine.Renderer
import processing.android.CompatUtils
import processing.android.PFragment

class MainActivity : AppCompatActivity() {
    private val renderer = Renderer(startFullscreen = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frame = FrameLayout(this)
        frame.id = CompatUtils.getUniqueViewId()
        setContentView(
            frame, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        val fragment = PFragment(renderer.pApplet)
        fragment.setView(frame, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        renderer.pApplet.onNewIntent(intent)
    }

    override fun onBackPressed() {
        renderer.pApplet.onBackPressed()
    }
}
