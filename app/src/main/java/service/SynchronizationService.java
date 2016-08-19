package service;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQADAO;
import dto.CoacheeHistoryDTO;
import dto.CoachingActivityDTO;
import entity.CoachingQA;

/**
 * Created by adrianch on 15/08/2016.
 */
public class SynchronizationService {

    public static void syncCoachingSession(final String coachingGUID, String coacheeID, String coachName,
                                           String coachID, int coachingGuideline, String action,
                                           String distributor, String store, String area,
                                           final SyncCoachingListener listener) {

        CoacheeHistoryDTO coacheeHistoryDTO = new CoacheeHistoryDTO(coachID, coachName, distributor,
                store, area, action, coachingGuideline);

        CoachingSessionService.insertCoacheeHistory(coacheeID, coacheeHistoryDTO,
                new CoachingSessionService.InsertCoacheeHistoryServiceListener() {
                    @Override
                    public void onInsertSessionCompleted(final String coacheeHistoryID) {
                        CoachingQADAO.getCoachingQA(coachingGUID, new CoachingQADAO.GetCoachingQAListener() {
                            @Override
                            public void onReceived(List<CoachingQA> coachingQAList) {
                                List<CoachingActivityDTO> coachingActivityDTOs = new ArrayList<>();

                                for (CoachingQA coachingQA : coachingQAList) {
                                    CoachingActivityDTO dto = new CoachingActivityDTO();
                                    dto.setTextAnswer(coachingQA.getTextAnswer());
                                    dto.setColumnID(coachingQA.getColumnID());
                                    dto.setQuestionID(coachingQA.getQuestionID());
                                    dto.setTickAnswer(coachingQA.isTickAnswer());
                                    coachingActivityDTOs.add(dto);
                                }

                                CoachingQuestionAnswerService.insertCoachingActivity(coacheeHistoryID,
                                        coachingActivityDTOs,
                                        new CoachingQuestionAnswerService.InsertCoachingQuestionAnswerListener() {
                                            @Override
                                            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                                                listener.onCompleted(true);
                                            }
                                        });
                            }
                        });
                    }
                });
    }

    public interface SyncCoachingListener {
        void onCompleted(boolean isSucceed);
    }
}
