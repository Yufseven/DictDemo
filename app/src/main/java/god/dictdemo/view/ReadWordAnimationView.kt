package god.dictdemo.view

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.airbnb.lottie.LottieAnimationView
import god.dictdemo.AppConfig
import god.dictdemo.MediaPlayerManager
import god.dictdemo.utils.I

class ReadWordAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LottieAnimationView(context, attrs, defStyleAttr), LifecycleObserver {
    companion object {
        private const val TAG = "ReadWordAnimationView"
    }

    private var mediaPlayerManager: MediaPlayerManager? = null

    fun playEnglish(word: String, loopPlay: Boolean = false) {
        this@ReadWordAnimationView.isClickable = false
        mediaPlayerManager = MediaPlayerManager()
        mediaPlayerManager!!.play(
            "${AppConfig.READ_WORD_PATH}$word",
            loopPlay,
            object : MediaPlayerManager.OnPlayListener {
                override fun onPrepare(mp: MediaPlayer) {
                    "播放器准备就绪".I(TAG)
                    // 开启动画
                    playAnimation()
                    // 关闭点击
                    this@ReadWordAnimationView.isClickable = false
                }

                override fun onCompleted(mp: MediaPlayer) {
                    "播放结束".I(TAG)
                    // 取消动画
                    if (!loopPlay) {
                        cancelAnimation()
                        progress = 0f
                    } else {
                        playAnimation()
                    }
                    // 开启点击
                    this@ReadWordAnimationView.isClickable = true
                }

                override fun onError(mp: MediaPlayer, what: Int, extra: Int) {
                    "播放错误：what = $what\textra = $extra".I(TAG)
                    // 取消动画
                    cancelAnimation()
                    progress = 0f
                    // 开启点击
                    this@ReadWordAnimationView.isClickable = true
                }
            })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun release() {
        // 开启点击
        this@ReadWordAnimationView.isClickable = true
        mediaPlayerManager?.release()
        mediaPlayerManager == null
        if (isAnimating) {
            cancelAnimation()
            progress = 0f
        }
    }
}