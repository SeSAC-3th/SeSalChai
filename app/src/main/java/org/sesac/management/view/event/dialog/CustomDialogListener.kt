package org.sesac.management.view.event.dialog

/**
 * RecyclerView에서 ArtistAddDialogFragment로 체크박스에 체크한 아티스트 List를
 * 넘겨주는 interface
 *
 * @author 혜원
 */
interface CustomDialogListener {
    fun onItemSelected(artistName: String, artistId: Int, isChecked: Boolean)
}