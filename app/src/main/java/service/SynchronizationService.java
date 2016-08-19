package service;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import dto.CoachingSessionDTO;
import dto.CoachingQuestionAnswerDTO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import model.Coaching;

/**
 * Created by adrianch on 15/08/2016.
 */
public class SynchronizationService {

    public static void syncCoachingSession(final String coachingSessionID,
                                           final SyncCoachingListener listener) {

        CoachingSessionDAO.getCoachingSession(coachingSessionID, new CoachingSessionDAO.GetCoachingListener() {
            @Override
            public void onCoachingReceived(CoachingSessionEntity entity) {
                if (entity != null) {
                    CoachingSessionDTO coachingSessionDTO = entity.toDTO();
                    CoachingSessionService.insertCoachingSession(coachingSessionID, coachingSessionDTO,
                            new CoachingSessionService.InsertCoachingSessionListener() {
                                @Override
                                public void onInsertSessionCompleted(boolean isSuccess) {
                                    if(isSuccess){
                                        CoachingQuestionAnswerDAO.getCoachingQA(coachingSessionID, new CoachingQuestionAnswerDAO.GetCoachingQAListener() {
                                            @Override
                                            public void onQuestionAnswerReceived(List<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntityList) {
                                                List<CoachingQuestionAnswerDTO> coachingQuestionAnswerDTOs = CoachingQuestionAnswerEntity.toDTOs(coachingQuestionAnswerEntityList);
                                                CoachingQuestionAnswerService.insertCoachingQuestionAnswer(coachingSessionID,
                                                        coachingQuestionAnswerDTOs,
                                                        new CoachingQuestionAnswerService.InsertCoachingQuestionAnswerListener() {
                                                            @Override
                                                            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                                                                listener.onSyncCoachingCompleted(isSuccess);
                                                            }
                                                        });
                                            }
                                        });
                                    } else {
                                        listener.onSyncCoachingCompleted(false);
                                    }
                                }
                            });

                } else {
                    listener.onSyncCoachingCompleted(false);
                }
            }
        });
    }

    public interface SyncCoachingListener {
        void onSyncCoachingCompleted(boolean isSucceed);
    }
}
