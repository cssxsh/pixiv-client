package xyz.cssxsh.pixiv.gif

import java.util.*
import kotlin.experimental.or

class LZWEncoder(private val colorTable: ColorTable, private val image: IntArray) {
    companion object {
        private val CLEAR_CODE = listOf(-1)
        private val END_OF_INFO = listOf(-2)

        private const val MAX_CODE_TABLE_SIZE = 1 shl 12
    }

    private val minimumCodeSize = colorTable.size() + 1
    private val outputBits = BitSet()
    private var position = 0
    private val codeTable: MutableMap<List<Int>, Int> = mutableMapOf()
    private var codeSize = 0
    private var indexBuffer: List<Int> = ArrayList()

    init {
        resetCodeTableAndCodeSize()
    }

    fun encode(): Pair<Int, ByteArray> {
        writeCode(codeTable.getValue(CLEAR_CODE))
        for (rgb in image) {
            val index = colorTable.colors.indexOf(rgb)
            processIndex(index)
        }
        writeCode(codeTable.getValue(indexBuffer))
        writeCode(codeTable.getValue(END_OF_INFO))
        return minimumCodeSize to toBytes()
    }

    private fun processIndex(index: Int) {
        val extendedIndexBuffer = indexBuffer + index
        indexBuffer = if (extendedIndexBuffer in codeTable) {
            extendedIndexBuffer
        } else {
            writeCode(codeTable.getValue(indexBuffer))
            if (codeTable.size == MAX_CODE_TABLE_SIZE) {
                writeCode(codeTable.getValue(CLEAR_CODE))
                resetCodeTableAndCodeSize()
            } else {
                addCodeToTable(extendedIndexBuffer)
            }
            listOf(index)
        }
    }

    private fun writeCode(code: Int) {
        for (shift in 0 until codeSize) {
            val bit = code ushr shift and 1 != 0
            outputBits.set(position++, bit)
        }
    }

    private fun toBytes(): ByteArray {
        val bitCount: Int = position
        val result = ByteArray((bitCount + 7) / 8)
        for (i in 0 until bitCount) {
            val byteIndex = i / 8
            val bitIndex = i % 8
            result[byteIndex] = result[byteIndex] or ((if (outputBits.get(i)) 1 else 0) shl bitIndex).toByte()
        }
        return result
    }

    private fun addCodeToTable(indices: List<Int>) {
        val newCode: Int = codeTable.size
        codeTable[indices] = newCode
        if (newCode == 1 shl codeSize) {
            ++codeSize
        }
    }

    private fun resetCodeTableAndCodeSize() {
        codeTable.clear()

        val colorsInCodeTable = 1 shl minimumCodeSize
        for (i in 0 until colorsInCodeTable) {
            codeTable[listOf(i)] = i
        }
        codeTable[CLEAR_CODE] = codeTable.size
        codeTable[END_OF_INFO] = codeTable.size

        codeSize = minimumCodeSize + 1
    }
}