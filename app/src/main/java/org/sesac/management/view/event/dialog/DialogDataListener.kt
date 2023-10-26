package org.sesac.management.view.event.dialog

import kotlinx.coroutines.flow.MutableStateFlow
import org.sesac.management.data.model.DialogItem

/**
 * EventEnroll & EventEdit에 데이터를 넘겨주기 위한 interface
 *
 * @constructor Create empty Dialog data listener
 * @author 혜원
 */
interface DialogDataListener {
    fun onDialogDataSelected(dataList: MutableList<DialogItem>)
}