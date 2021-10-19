package pl.gieted.flappy_bird

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.game.HighScoreRepository
import processing.android.CompatUtils
import processing.android.PFragment

class MainActivity : AppCompatActivity() {
    private lateinit var renderer: Renderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.renderer = Renderer(startFullscreen = true, highScoreRepository = HighScoreRepository(filesDir))
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
