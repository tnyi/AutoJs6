package org.autojs.autojs.model.autocomplete

/**
 * Created by Stardust on Feb 3, 2018.
 * Transformed by 抠脚本人 (https://github.com/little-alei) on Jul 11, 2023.
 */
class CodeCompletion {
    val hint: String
    val url: String?
    private val mInsertText: String?
    private val mInsertPos: Int

    constructor(hint: String, url: String?, insertPos: Int) {
        this.hint = hint
        this.url = url
        mInsertPos = insertPos
        mInsertText = null
    }

    constructor(hint: String, url: String, insertText: String?) {
        this.hint = hint
        this.url = url
        mInsertText = insertText
        mInsertPos = -1
    }

    val insertText: String
        get() {
            if (mInsertText != null) return mInsertText
            return if (mInsertPos == 0) {
                hint
            } else hint.substring(mInsertPos)
        }
}