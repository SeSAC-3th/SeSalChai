package org.sesac.management.view.notice

import android.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.NoticeRecyclerAdapter
import org.sesac.management.view.notice.detail.NoticeDetailFragment
import org.sesac.management.view.notice.enroll.NoticeEnrollFragment

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {

    private val noticeViewModel: NoticeViewModel by activityViewModels()

    private var adapter: NoticeRecyclerAdapter = NoticeRecyclerAdapter(emptyList(), { }) { }

    override fun onViewCreated() {
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)

            // FAB 버튼 Click 시 등록 화면으로 전환
            btnNoticeEnrollNavigation.setOnAvoidDuplicateClick {
                noticeLayout.changeFragment(this@NoticeFragment, NoticeEnrollFragment())
            }

            rvEvent.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                observerSetup()
            }
        }
    }

    /**
     * Observer setup
     * 모든 공지 관찰
     */
    private fun observerSetup() {
        noticeViewModel.getAllNotice()?.observe(viewLifecycleOwner) { notices ->
            notices?.let {
                updateUI(notices)
            }
        }
    }

    /**
     * Update Ui
     * RecyclerView의 Apdater 설정을 통해 화면 갱신 (Dialog로 삭제 로직 구현)
     * @param notices
     */
    private fun updateUI(notices: List<Notice>) {
        adapter = NoticeRecyclerAdapter(notices, onItemClick()) { noticeId ->
            AlertDialog.Builder(requireContext()).run {
                setMessage("정말로 삭제하시겠습니까?")
                setNegativeButton("cancel", null)
                setPositiveButton(
                    "ok",
                ) { _, _ ->
                    noticeViewModel.deleteNotice(noticeId)
                }
                create()
                show()
            }
        }
        binding.rvEvent.adapter = adapter
    }

    /**
     * OnItemClick
     * RecyclerView의 목록을 선택 하면 선택된 공지의 ID 값을 ViewModel에 보존
     * getNotice를 호출 하여 상세 화면으로 넘어 갔을 때 검색하지 않도록 getNoitce를 호출
     * @return : 람다 함수 반환
     */
    private fun onItemClick(): (Int) -> Unit = { noticeId ->
        noticeViewModel.selectedNoticeId = noticeId
        noticeViewModel.getNotice(noticeId)
        binding.noticeLayout.changeFragment(this@NoticeFragment, NoticeDetailFragment())
    }
}