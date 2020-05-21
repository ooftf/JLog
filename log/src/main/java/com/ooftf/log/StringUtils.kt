package com.ooftf.log

import java.util.ArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/7
 */
internal object StringUtils {
    @JvmStatic
    fun split(src: String, perLength: Int): List<String> {
        val list: MutableList<String> =
            ArrayList()
        var i = 0
        while (i < src.length) {
            val end = Math.min(src.length, i + perLength)
            list.add(src.substring(i, end))
            i = end
        }
        return list
    }
}