package god.dictdemo

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer

class MediaPlayerManager : MediaPlayer() {

    @Throws(Exception::class)
    fun play(context: Context, resid: Int) {
        reset()
        val afd: AssetFileDescriptor = context.getResources().openRawResourceFd(resid) ?: return

        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        prepareAsync()

        initEvent()
    }

    @Throws(Exception::class)
    fun play(path: String, loopPlay: Boolean = false, listener: OnPlayListener) {
        reset()
        if (isPlaying) {
            release()
            play(path, loopPlay, listener)
        }
        //设置音频流的类型
        setAudioStreamType(AudioManager.STREAM_MUSIC)

        setDataSource(path)
        prepareAsync()

        // 设置监听
        initEvent(listener, loopPlay)
    }

    private fun initEvent(loopPlay: Boolean = false) {
        setOnPreparedListener {
            it.start()
        }
        setOnCompletionListener {
            if (loopPlay) {
                it.start()
            } else {
                it.release()
            }
        }
        setOnErrorListener { mp, what, extra ->
            mp.release()
            false
        }
    }

    private fun initEvent(
        listener: OnPlayListener,
        loopPlay: Boolean
    ) {
        setOnPreparedListener {
            listener.onPrepare(it)
            it.start()
        }
        setOnCompletionListener {
            listener.onCompleted(it)
            if (loopPlay) {
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