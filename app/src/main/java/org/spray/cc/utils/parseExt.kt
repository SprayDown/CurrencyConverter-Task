package org.spray.cc.utils

import org.w3c.dom.Element
import org.w3c.dom.Node

fun Element.getNodeValue(key: String) : String {
    val nodeList = getElementsByTagName(key)
    val node = nodeList.item(0)
    if (node != null) {
        if (node.hasChildNodes()) {
            val child = node.firstChild
            while (child != null) {
                if (child.nodeType === Node.TEXT_NODE) {
                    return child.nodeValue
                }
            }
        }
    }
    return String()
}