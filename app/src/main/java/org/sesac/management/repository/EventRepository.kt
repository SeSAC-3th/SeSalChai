package org.sesac.management.repository

import androidx.lifecycle.LiveData
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Manager
import org.sesac.management.data.local.dao.ArtistDAO
import org.sesac.management.data.local.dao.EventDAO
import org.sesac.management.data.local.dao.ManagerDAO

/**
 * Event repository
 *
 * @property eventDAO
 * @property manageDAO
 * @property artistDAO
 * @constructor Create empty Event repository
 * @author 진혁, 혜원
 */
class EventRepository(
    private val eventDAO: EventDAO,
    private val manageDAO: ManagerDAO,
    private val artistDAO: ArtistDAO,
) {
    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(eventDAO: EventDAO, manageDAO: ManagerDAO, artistDAO: ArtistDAO) =
            instance ?: synchronized(this) {
                instance ?: EventRepository(eventDAO, manageDAO, artistDAO).also { instance = it }
            }
    }

    /* C : 이벤트 등록 메서드 */
    suspend fun insertEvent(event: Event) {
        eventDAO.insertEvent(event)
    }

    // 매니저에 하나로 mapping된 행사 정보와 아티스트 정보를 저장할 때 사용
    suspend fun insertManager(manager: Manager) {
        manageDAO.insertManager(manager)
    }

    // 모든 행사 정보를 불러올 때 사용
    /**
     * Get event data
     * stateflow - flow
     * livedata - flow
     * livedata - livedata
     * 3가지 버전으로 구현
     * @author 진혁
     */
    // flow - flow

    // livedata - flow

    // livedata - livedata

    /* R : 이벤트 전체 조회 메서드 */
    fun getAllEvent() = eventDAO.getAllEvent()

    /* R : 해당하는 이벤트 ID로 조회하는 메서드 */
    fun getSearchByEventID(eventId: Int) = eventDAO.getSearchByEventID(eventId)

    /* R : 해당하는 이벤트 Name으로 조회하는 메서드 */
    fun getSearchEvent(eventName: String): LiveData<List<Event>> =
        eventDAO.getSearchEvent(eventName)

    /* R: EventDetail에서 보여주는 참여 아티스트 */
    suspend fun getArtistFromEvent(eventId: Int) = eventDAO.getEventsFromArtist(eventId)

    /* U : 이벤트 수정 메서드 */
    suspend fun updateEvent(event: Event) {
        eventDAO.updateEvent(event)
    }

    /* D : 이벤트 삭제 메서드 */
    suspend fun deleteEvent(eventId: Int) {
        eventDAO.deleteEvent(eventId)
    }
}