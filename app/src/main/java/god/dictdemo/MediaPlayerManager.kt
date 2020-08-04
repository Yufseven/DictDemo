package god.dictdemo

import android.media.AudioManager
import android.media.MediaPlayer
import java.lang.Exception

class MediaPlayerManager : MediaPlayer() {
    private var isLoopPlay = false

    fun play(path: String, loopPlay: Boolean = false, listener: OnPlayListener) {
        if (isPlaying) {
            release()
            play(path, loopPlay, listener)
        }
        //设置音频流的类型
        setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            setDataSource(path)
            prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 设置监听
        setOnPreparedListener {
            listener.onPrepare(it)
            it.start()
        }
        setOnCompletionListener {
            listener.onCompleted(it)
            if (isLoopPlay) {
                it.start()
            } else {
                it.release()
            }
        }
        setOnErrorListener { mp, what, extra ->
            listener.onError(mp, what, extra)
            mp.release()
            false
        }
    }

    interface OnPlayListener {
        fun onPrepare(mp: MediaPlayer)
        fun onCompleted(mp: MediaPlayer)
        fun onError(mp: MediaPlayer, what: Int, extra: Int)
    }
}