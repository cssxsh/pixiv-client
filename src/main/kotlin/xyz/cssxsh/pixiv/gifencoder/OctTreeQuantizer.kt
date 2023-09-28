/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.cssxsh.pixiv.gifencoder

import com.squareup.gifencoder.Color
import com.squareup.gifencoder.ColorQuantizer
import com.squareup.gifencoder.Multiset

/**
 * Implements qct-tree quantization.
 *
 *
 * The principle of algorithm: http://www.microsoft.com/msj/archive/S3F1.aspx
 *
 */
public class OctTreeQuantizer private constructor() : ColorQuantizer {
    private var leafCount = 0
    private var inIndex = 0
    private val nodeList = arrayOfNulls<Node>(8)
    override fun quantize(originalColors: Multiset<Color>, maxColorCount: Int): Set<Color> {
        val node = createNode(0)
        val distinctElements = originalColors.distinctElements
        for (color in distinctElements) {
            addColor(node, color, 0)
            while (leafCount > maxColorCount) {
                reduceTree()
            }
        }
        val colors = HashSet<Color>()
        getColorPalette(node, colors)
        leafCount = 0
        inIndex = 0
        for (i in 0..7) {
            nodeList[i] = null
        }
        return colors
    }

    private fun addColor(node: Node?, color: Color, inLevel: Int): Boolean {
        var node = node
        val nIndex: Int
        val shift: Int
        if (node == null) {
            node = createNode(inLevel)
        }
        val red = (color.getComponent(0) * 255).toInt()
        val green = (color.getComponent(1) * 255).toInt()
        val blue = (color.getComponent(2) * 255).toInt()
        if (node.isLeaf) {
            node.pixelCount++
            node.redSum += red
            node.greenSum += green
            node.blueSum += blue
        } else {
            shift = 7 - inLevel
            nIndex = (red and mask[inLevel].code shr shift shl 2
                    or (green and mask[inLevel].code shr shift shl 1)
                    or (blue and mask[inLevel].code shr shift))
            var tmpNode = node.child[nIndex]
            if (tmpNode == null) {
                tmpNode = createNode(inLevel + 1)
            }
            node.child[nIndex] = tmpNode
            return addColor(node.child[nIndex], color, inLevel + 1)
        }
        return true
    }

    private fun createNode(level: Int): Node {
        val node = Node()
        node.level = level
        node.isLeaf = level == 8
        if (node.isLeaf) {
            leafCount++
        } else {
            node.next = nodeList[level]
            nodeList[level] = node
        }
        return node
    }

    private fun reduceTree() {
        var redSum = 0
        var greenSum = 0
        var blueSum = 0
        var count = 0
        var i = 7
        while (i > 0) {
            if (nodeList[i] != null) break
            i--
        }
        val tmpNode = nodeList[i]
        nodeList[i] = tmpNode!!.next
        i = 0
        while (i < 8) {
            if (tmpNode.child[i] != null) {
                redSum += tmpNode.child[i]!!.redSum
                greenSum += tmpNode.child[i]!!.greenSum
                blueSum += tmpNode.child[i]!!.blueSum
                count += tmpNode.child[i]!!.pixelCount
                tmpNode.child[i] = null
                leafCount--
            }
            i++
        }
        tmpNode.isLeaf = true
        tmpNode.redSum = redSum
        tmpNode.greenSum = greenSum
        tmpNode.blueSum = blueSum
        tmpNode.pixelCount = count
        leafCount++
    }

    private fun getColorPalette(node: Node?, colors: MutableSet<Color>) {
        if (node!!.isLeaf) {
            node.colorIndex = inIndex
            node.redSum /= node.pixelCount
            node.greenSum /= node.pixelCount
            node.blueSum /= node.pixelCount
            node.pixelCount = 1
            inIndex++
            val red = node.redSum.toDouble() / 255
            val green = node.greenSum.toDouble() / 255
            val blue = node.blueSum.toDouble() / 255
            colors.add(Color(red, green, blue))
        } else {
            for (i in 0..7) {
                if (node.child[i] != null) {
                    getColorPalette(node.child[i], colors)
                }
            }
        }
    }

    private class Node {
        var isLeaf = false
        var level = 0
        var colorIndex = 0
        var redSum = 0
        var greenSum = 0
        var blueSum = 0
        var pixelCount = 0
        var child = arrayOfNulls<Node>(8)
        var next: Node? = null
    }

    public companion object {
        public val INSTANCE: OctTreeQuantizer = OctTreeQuantizer()
        private val mask = charArrayOf(
            0x80.toChar(),
            0x40.toChar(),
            0x20.toChar(),
            0x10.toChar(),
            0x08.toChar(),
            0x04.toChar(),
            0x02.toChar(),
            0x01.toChar()
        )
    }
}