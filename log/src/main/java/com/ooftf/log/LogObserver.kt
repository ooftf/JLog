package com.ooftf.log

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/7
 */
interface LogObserver {
    fun accept(data: LogMessage)
}