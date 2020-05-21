package com.ooftf.log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ooftf.log.StringUtils.split
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * @author ooftf
 * @date 2018/9/20 0020
 * @desc
 */
object JLog {


    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private const val FRAME_LINE_HEADER = "║ "
    private const val FRAME_START =
        "╔══════════════════════════════════════════════════════════════════════════════════════════════════════"
    private const val FRAME_END =
        "╚══════════════════════════════════════════════════════════════════════════════════════════════════════"
    private const val BRACKET_START = "["
    private const val BRACKET_END = "]"
    private const val SQUARE_BRACKETS_END = "】"
    private const val SQUARE_BRACKETS_START = "【"
    private const val SPACE = " "

    /**
     * Priority constant for the println method; use Log.v.
     */
    private const val VERBOSE = 2

    /**
     * Priority constant for the println method; use Log.d.
     */
    const val DEBUG = 3

    /**
     * Priority constant for the println method; use Log.i.
     */
    private const val INFO = 4

    /**
     * Priority constant for the println method; use Log.w.
     */
    private const val WARN = 5

    /**
     * Priority constant for the println method; use Log.e.
     */
    private const val ERROR = 6
    private const val MAX_LENGTH = 3000
    private const val TAG_EMPTY = "JLog-TAG-Empty"
    private const val TAG_NULL = "JLog-TAG-Null"
    @JvmStatic
    fun v(info: Any?) {
        v(null, info)
    }
    @JvmStatic
    fun d(info: Any?) {
        d(null, info)
    }
    @JvmStatic
    fun i(info: Any?) {
        i(null, info)
    }
    @JvmStatic
    fun w(info: Any) {
        w(null, info)
    }
    @JvmStatic
    fun e(info: Any?) {
        e(null, info)
    }

    @JvmStatic
    fun v(tag: Any?, info: Any?) {
        logObject(VERBOSE, 0, tag, info)
    }

    @JvmStatic
    fun d(tag: Any?, info: Any?) {
        logObject(DEBUG, 0, tag, info)
    }

    @JvmStatic
    fun i(tag: Any?, info: Any?) {
        logObject(INFO, 0, tag, info)
    }

    @JvmStatic
    private fun w(tag: Any?, info: Any) {
        logObject(WARN, 0, tag, info)
    }

    @JvmStatic
    fun e(tag: Any?, info: Any?) {
        logObject(ERROR, 0, tag, info)
    }

    @JvmStatic
    fun vJson(tag: Any, msg: Any?) {
        logJson(VERBOSE, 0, tag, msg)
    }

    @JvmStatic
    fun dJson(tag: Any, msg: Any?) {
        logJson(DEBUG, 0, tag, msg)
    }

    @JvmStatic
    fun iJson(tag: Any, msg: Any?) {
        logJson(INFO, 0, tag, msg)
    }

    @JvmStatic
    fun wJson(tag: Any, msg: Any?) {
        logJson(WARN, 0, tag, msg)
    }

    @JvmStatic
    fun eJson(tag: Any, msg: Any?) {
        logJson(ERROR, 0, tag, msg)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    @JvmStatic
    fun v(tag: String?, i: Int, vararg message: Any?) {
        bamboo(VERBOSE, tag, i, *message)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    @JvmStatic
    fun d(tag: String?, i: Int, vararg message: Any?) {
        bamboo(DEBUG, tag, i, *message)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    @JvmStatic
    fun i(tag: String?, i: Int, vararg message: Any?) {
        bamboo(INFO, tag, i, *message)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    @JvmStatic
    fun w(tag: String?, i: Int, vararg message: Any?) {
        bamboo(WARN, tag, i, *message)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    @JvmStatic
    fun e(tag: String?, i: Int, vararg message: Any?) {
        bamboo(ERROR, tag, i, *message)
    }

    /**
     * 每个 message 固定长度，长度不够的用 [JLog.SPACE] 填补
     *
     * @param tag
     * @param i
     * @param message
     */
    private fun bamboo(
        level: Int,
        tag: String?,
        i: Int,
        vararg message: Any?
    ) {
        if (intercept(level)) {
            return
        }
        val result = StringBuffer()
        for (s in message) {
            val per = StringBuffer()
            per.append(BRACKET_START)
            per.append(s)
            per.append(BRACKET_END)
            while (per.length < i) {
                per.append(SPACE)
            }
            result.append(per)
        }
        logObject(level, 0, tag, result.toString())
    }

    private fun logArray(
        level: Int, depthSrc: Int,
        tag: Any?,
        list: Array<*>
    ) {
        var depth = depthSrc + 1
        logObject(level, depth, tag, FRAME_START)
        for (per in list) {
            logObject(level, depth, tag, FRAME_LINE_HEADER + per)
        }
        logObject(level, depth, tag, FRAME_END)
    }

    private fun logList(
        level: Int, depthSrc: Int,
        tag: Any?,
        list: List<*>
    ) {
        var depth = depthSrc + 1
        logObject(level, depth, tag, FRAME_START)
        for (per in list) {
            logObject(level, depth, tag, FRAME_LINE_HEADER + per)
        }
        logObject(level, depth, tag, FRAME_END)
    }

    private fun logJson(level: Int, depthSrc: Int, tag: Any, msg: Any?) {
        if (intercept(level)) {
            return
        }
        var depth = depthSrc + 1
        if (msg == null) {
            logObject(level, depth, tag, msg)
            return
        }
        var message = if (msg is String) {
            msg
        } else {
            jsonParser?.object2Json(msg) ?: "JsonParse is null"
        }
        try {
            if (message.startsWith("{")) {
                val jsonObject = JSONObject(message)
                //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                message = jsonObject.toString(4)
            } else if (message.startsWith("[")) {
                val jsonArray = JSONArray(message)
                message = jsonArray.toString(4)
            }
        } catch (ignored: JSONException) {
            ignored.printStackTrace()
        }
        logArray(
            level, depth,
            tag,
            message.split(LINE_SEPARATOR!!).toTypedArray()
        )
    }

    /**
     * 最基本的打印方法，所有打印封装都应该基于这个方法
     *
     * @param level
     * @param tag
     * @param message
     */
    private fun logObject(level: Int, depthSrc: Int, tag: Any?, message: Any?) {
        if (intercept(level)) {
            return
        }
        var depth = depthSrc + 1
        if (message is List<*>) {
            logList(level, depth, tag, message)
        } else if (message is Intent) {
            logIntent(level, depth, tag, message)
        } else if (message is Bundle) {
            logBundle(level, depth, tag, message)
        } else if (message is Array<*>) {
            logArray(level, depth, tag, message)
        } else {
            val msgString = message.toString()
            if (msgString.length > MAX_LENGTH) {
                logObject(
                    level, depth,
                    tag,
                    split(msgString, MAX_LENGTH - 5)
                )
            } else {
                logString(level, depth, parseTag(tag), msgString)
            }
        }
    }

    fun register(function: LogObserver) {
        logObserver.add(function)
    }

    private val logObserver =
        ArrayList<LogObserver>()

    private fun notice(level: Int, depth: Int, tag: Any?, msg: String) {
        val bean = LogMessage(level, depth, tag, msg)
        for (item in logObserver) {
            item.accept(bean)
        }
    }

    /**
     * 这个方法应该只被 logObject 所调用
     *
     * @param level
     * @param tag
     * @param msg
     */
    private fun logString(level: Int, depth: Int, tag: Any?, msgSrc: String) {
        notice(level, depth, tag, msgSrc)
        var tagString = parseTag(tag)
        val sb = StringBuilder()
        for (i in 0 until depth) {
            sb.append(SPACE)
            sb.append(SPACE)
        }
        sb.append(msgSrc)
        val msg = sb.toString()
        when (level) {
            VERBOSE -> Log.v(tagString, msg)
            DEBUG -> Log.d(tagString, msg)
            INFO -> Log.i(tagString, msg)
            WARN -> Log.w(tagString, msg)
            ERROR -> Log.e(tagString, msg)
            else -> Log.d(tagString, msg)
        }
    }

    private var interceptor: Interceptor? = null
    private var jsonParser: JsonParser? = null

    /**
     * @param level
     * @return 是否拦截
     */
    private fun intercept(level: Int): Boolean {
        return if (interceptor == null) {
            false
        } else {
            !interceptor!!.process(level)
        }
    }

    private fun parseTag(tag: Any?): String {
        return if (tag == null) {
            TAG_NULL
        } else if (tag is String) {
            if (tag.length == 0) {
                TAG_EMPTY
            } else tag
        } else {
            tag.javaClass.simpleName
        }
    }

    fun setInterceptor(interceptor: Interceptor?) {
        JLog.interceptor = interceptor
    }

    fun setJsonParser(jsonParser: JsonParser?) {
        JLog.jsonParser = jsonParser
    }

    private fun logBundle(level: Int, depthSrc: Int, tag: Any?, bundle: Bundle) {
        var depth = depthSrc + 1
        logObject(level, depth, tag, genObjectTypeString(bundle))
        logString(level, depth, tag, FRAME_START)
        bundle.keySet().forEach {
            logString(level, depth, tag, "$it=")
            logObject(level, depth, tag, bundle.get(it))
        }
        logString(level, depth, tag, FRAME_END)
    }

    private fun logIntent(level: Int, depthSrc: Int, tag: Any?, intent: Intent) {
        var depth = depthSrc + 1
        logObject(level, depth, tag, genObjectTypeString(intent))
        logString(level, depth, tag, FRAME_START)
        val mAction = intent.action
        logString(level, depth, tag, "action= $mAction")
        val mCategories = intent.categories
        logString(level, depth, tag, "categories= ")
        logObject(level, depth, tag, mCategories)
        val mData = intent.data
        logString(level, depth, tag, "data= ")
        logObject(level, depth, tag, mData)
        val mType = intent.type
        logString(level, depth, tag, "type= ")
        logObject(level, depth, tag, mType)
        logString(level, depth, tag, "flags= ")
        logObject(level, depth, tag, intent.flags)
        logString(level, depth, tag, "package= ")
        logObject(level, depth, tag, intent.getPackage())
        logString(level, depth, tag, "extras= ")
        logObject(level, depth, tag, intent.extras)
        logString(level, depth, tag, "selector= ")
        logObject(level, depth, tag, intent.selector)
        logString(level, depth, tag, FRAME_END)
    }

    private fun genObjectTypeString(obj: Any): String {
        return SQUARE_BRACKETS_START + obj.javaClass.simpleName + "@" + obj.hashCode() + SQUARE_BRACKETS_END
    }
}