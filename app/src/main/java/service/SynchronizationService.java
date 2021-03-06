package service;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import dto.CoachingSessionDTO;
import dto.CoachingQuestionAnswerDTO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import model.Coaching;
import unilever.coachingform.CoachingSummaryASMPullActivity;
import unilever.coachingform.CoachingSummaryASMPushActivity;
import unilever.coachingform.CoachingSummaryDSRActivity;
import unilever.coachingform.CoachingSummaryDTSPullActivity;
import unilever.coachingform.CoachingSummaryMerchandiserActivity;
import unilever.coachingform.CoachingSummarySRPullActivity;
import unilever.coachingform.RSMCoachingActivity;
import utility.ConstantUtil;

/**
 * Created by adrianch on 15/08/2016.
 */
public class SynchronizationService {

    private static final String TAG = "SynchronizationService";

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

    public static void sendEmail(String coachingSessionID, final Activity activity){
        CoachingSessionDAO.getCoachingSession(coachingSessionID, new CoachingSessionDAO.GetCoachingListener() {
            @Override
            public void onCoachingReceived(CoachingSessionEntity coachingSessionEntity) {
                Intent email = new Intent(Intent.ACTION_SEND);
                List<String> receiverList = new ArrayList<>();
                
                String path = Environment.getExternalStorageDirectory() + "/" +
                        coachingSessionEntity.getPdfFileName();

                receiverList.add(coachingSessionEntity.getCoacheeEmail());

                if(coachingSessionEntity.getFirstManagerEmail() != null &&
                        coachingSessionEntity.getFirstManagerEmail() != ""){
                    receiverList.add(coachingSessionEntity.getFirstManagerEmail());
                }

                /*
                if(coachingSessionEntity.getSecondManagerEmail() != null &&
                        coachingSessionEntity.getSecondManagerEmail() != ""){
                    receiverList.add(coachingSessionEntity.getSecondManagerEmail());
                }*/

                if(coachingSessionEntity.getCdCapabilityTeamEmail() != null &&
                        coachingSessionEntity.getCdCapabilityTeamEmail() != ""){
                    receiverList.add(coachingSessionEntity.getCdCapabilityTeamEmail());
                }



                String[] receiverArray = Arrays.copyOf(receiverList.toArray(), receiverList.size(), String[].class);

                Log.d(TAG, receiverArray.toString());

                if(receiverList.size() > 0) {
                    String subject = coachingSessionEntity.getCoachName() + " - " +
                            coachingSessionEntity.getCoacheeName() + " - " +
                            coachingSessionEntity.getFormattedDate();

                    email.putExtra(Intent.EXTRA_EMAIL, receiverArray);
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, "");
                    Uri uri = Uri.fromFile(new File(path));
                    email.putExtra(Intent.EXTRA_STREAM, uri);
                    email.setType("application/pdf");
                    if(activity instanceof CoachingSummaryDSRActivity ||
                            activity instanceof CoachingSummaryMerchandiserActivity ||
                            activity instanceof CoachingSummaryASMPullActivity ||
                            activity instanceof CoachingSummaryDTSPullActivity ||
                            activity instanceof CoachingSummaryASMPushActivity ||
                            activity instanceof CoachingSummarySRPullActivity ||
                            activity instanceof RSMCoachingActivity){
                        Log.d(TAG, "Start activity for result");
                        activity.startActivityForResult(email, ConstantUtil.REQ_SEND_EMAIL );
                   } else {
                        Log.d(TAG, "Start activity with no result");
                        activity.startActivity(email);
                   }
                }
            }
        });

    }

    public interface SyncCoachingListener {
        void onSyncCoachingCompleted(boolean isSucceed);
    }
}
