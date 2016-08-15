package service;

import com.google.firebase.database.DatabaseReference;

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

        CoacheeHistoryService.insertCoacheeHistory(coacheeID, coacheeHistoryDTO,
                new CoacheeHistoryService.InsertCoacheeHistoryServiceListener() {
                    @Override
                    public void onCompleted(final String coacheeHistoryID) {
                        CoachingQADAO.getCoachingQA(coachingGUID, new CoachingQADAO.GetCoachingQAListener() {
                            @Override
                            public void onReceived(List<CoachingQA> coachingQAList) {
                                List<CoachingActivityDTO> coachingActivityDTOs = new ArrayList<>();

                                for(CoachingQA coachingQA : coachingQAList){
                                    CoachingActivityDTO dto = new CoachingActivityDTO();
                                    dto.setTextAnswer(coachingQA.getTextAnswer());
                                    dto.setColumnID(coachingQA.getColumnID());
                                    dto.setQuestionID(coachingQA.getQuestionID());
                                    dto.setTickAnswer(coachingQA.isTickAnswer());
                                    coachingActivityDTOs.add(dto);
                                }

                                CoachingActivityService.insertCoachingActivity(coacheeHistoryID,
                                        coachingActivityDTOs, new CoachingQADAO.InsertCoachingQAListener() {
                                            @Override
                                            public void onCompleted(boolean isSuccess) {
                                                listener.onCompleted(isSuccess);
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
