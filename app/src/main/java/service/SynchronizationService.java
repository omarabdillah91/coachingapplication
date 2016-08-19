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

    public static void syncCoachingSession(final String coachingGUID, String coacheeID, String coachName,
                                           String coachID, int coachingGuideline, String action,
                                           String distributor, String store, String area,
                                           final SyncCoachingListener listener) {

        CoachingSessionDTO coachingSessionDTO = new CoachingSessionDTO(coachID, coachName, distributor,
                store, area, action, coachingGuideline);

        CoachingSessionService.insertCoachingSession(coacheeID, coachingSessionDTO,
                new CoachingSessionService.InsertCoachingSessionListener() {
                    @Override
                    public void onInsertSessionCompleted(final boolean isSuccess) {
                        CoachingQuestionAnswerDAO.getCoachingQA(coachingGUID, new CoachingQuestionAnswerDAO.GetCoachingQAListener() {
                            @Override
                            public void onQuestionAnswerReceived(List<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntityList) {
                                if (isSuccess) {
                                    List<CoachingQuestionAnswerDTO> coachingQuestionAnswerDTOs = new ArrayList<>();

                                    for (CoachingQuestionAnswerEntity coachingQuestionAnswerEntity : coachingQuestionAnswerEntityList) {
                                        CoachingQuestionAnswerDTO dto = new CoachingQuestionAnswerDTO();
                                        dto.setTextAnswer(coachingQuestionAnswerEntity.getTextAnswer());
                                        dto.setColumnID(coachingQuestionAnswerEntity.getColumnID());
                                        dto.setQuestionID(coachingQuestionAnswerEntity.getQuestionID());
                                        dto.setTickAnswer(coachingQuestionAnswerEntity.isTickAnswer());
                                        coachingQuestionAnswerDTOs.add(dto);
                                    }

                                    CoachingQuestionAnswerService.insertCoachingQuestionAnswer(coachingGUID,
                                            coachingQuestionAnswerDTOs,
                                            new CoachingQuestionAnswerService.InsertCoachingQuestionAnswerListener() {
                                                @Override
                                                public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                                                    listener.onSyncCoachingCompleted(isSuccess);
                                                }
                                            });
                                } else {
                                    listener.onSyncCoachingCompleted(false);
                                }
                            }
                        });
                    }
                });
    }

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
