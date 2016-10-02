package utility;

import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import model.Coaching;
import unilever.coachingform.MainApp;

/**
 * Created by adrian on 8/20/2016.
 */
public class PDFUtil {

    private static final String TAG = "PDFUtil";
    private static Font heading1Font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
    private static Font heading2Font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
    private static Font heading3Font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD);

    private static Font normalFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
    private static Font smallFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL);

    private static final int BAHASA = 0;
    private static final int ENGLISH = 1;

    public static void createPDF(final String coachingSessionID, final GeneratePDFListener listener) {
        CoachingSessionDAO.getCoachingSession(coachingSessionID,
                new CoachingSessionDAO.GetCoachingListener() {
                    @Override
                    public void onCoachingReceived(final CoachingSessionEntity coachingSessionEntity) {
                        if (coachingSessionEntity != null) {

                            CoachingQuestionAnswerDAO.getCoachingQA(coachingSessionID, new CoachingQuestionAnswerDAO.GetCoachingQAMapListener() {
                                @Override
                                public void onQAMapReceived(Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap) {
                                    if (qaMap.size() > 0) {
                                        if (coachingSessionEntity.getCoachingGuideline() == ConstantUtil.GUIDELINE_FASA) {
                                            createFASAPDF(coachingSessionEntity, qaMap, listener);
                                        } else if (coachingSessionEntity.getCoachingGuideline() == ConstantUtil.GUIDELINE_DSR) {
                                            createDSRPDF(coachingSessionEntity, qaMap, listener);
                                        } else if (coachingSessionEntity.getCoachingGuideline() == ConstantUtil.GUIDELINE_ASM_PUSH){
                                            createASMPUSHPDF(coachingSessionEntity, qaMap, listener);
                                        } else if (coachingSessionEntity.getCoachingGuideline() == ConstantUtil.GUIDELINE_DTS_PULL){
                                            createDTSPULLPDF(coachingSessionEntity, qaMap, listener);
                                        } else if (coachingSessionEntity.getCoachingGuideline() == ConstantUtil.GUIDELINE_ASM_PULL){
                                            createASMPULLPDF(coachingSessionEntity, qaMap, listener);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

    }

    public static void createFASAPDF(CoachingSessionEntity coachingSession,
                                     Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap,
                                     GeneratePDFListener listener) {
        Document doc = new Document();
        /*String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getId()
                + ".pdf";*/

        /*String path = Environment.getExternalStorageDirectory() + "/" + "test"
                + ".pdf";*/

        String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getPdfFileName();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            CoachingQuestionAnswerEntity ent = (CoachingQuestionAnswerEntity) qaMap.values().toArray()[0];
            int lang = ent.getQuestionID().contains("bahasa") ? BAHASA : ENGLISH;
            String langS = lang == BAHASA ? "bahasa_" : "english_";

            Chunk chunk = new Chunk("COACHING FORM - MODERN TRADE \n\n", heading1Font);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

            chapter.add(createLeftRight("Coach : " + coachingSession.getCoachName(),
                    "Store : " + coachingSession.getStore()));
            chapter.add(createLeftRight("Coachee : " + coachingSession.getCoacheeName(),
                    "Tanggal : " + coachingSession.getFormattedDate()));

            chapter.add(new Paragraph("\n"));

            String[] columns = CoachingQuestionAnswerDAO.uniqueColumnID(qaMap.values());
            int nProduct = columns.length;
            float[] columnWidths = new float[nProduct + 2];

            columnWidths[0] = 1;
            columnWidths[1] = 6;

            for (int i = 2; i < columnWidths.length; i++) {
                columnWidths[i] = 2;
            }

            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);

            table.addCell(createTableHeader("NO"));
            table.addCell(createTableHeader("DETAIL"));

            for (int i = 0; i < nProduct; i++) {
                Log.d("Procuct " + i, columns[i]);
                table.addCell(createTableHeader(columns[i]));
            }

            table.addCell(createRowSpanCell("1", 3));
            table.addCell(createColSpanCell(getString("fa_title_1", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_1a", lang)));

            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_1a", columns[i]));
                Log.d(TAG, columns[i]);
                Log.d(TAG, entity == null ? "NULL" : entity.toString());
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createNormalCell(getString("fa_1b", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_1b", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createRowSpanCell("2", 3));
            table.addCell(createColSpanCell(getString("fa_title_2", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_2a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_2a", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createNormalCell(getString("fa_2b", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_2b", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createRowSpanCell("3", 2));
            table.addCell(createColSpanCell(getString("fa_title_3", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_3a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_3a", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createRowSpanCell("4", 2));
            table.addCell(createColSpanCell(getString("fa_title_4", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_4a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_4a", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createRowSpanCell("5", 2));
            table.addCell(createColSpanCell(getString("fa_title_5", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_5a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_5a", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createRowSpanCell("6", 7));
            table.addCell(createColSpanCell(getString("fa_title_6", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_6a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_6a", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            table.addCell(createNormalCell(getString("fa_6b", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_6b", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            //table.addCell(createColSpanCell(getString("fa_title_6_1", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_title_6_1", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_kompetitor", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }


            table.addCell(createNormalCell(getString("fa_6c", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_6c", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            table.addCell(createNormalCell(getString("fa_6d", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_6d", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }


            //table.addCell(createColSpanCell(getString("fa_title_6_2", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_title_6_2", lang) + "\n" +
                    getString("rpi", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_rpi", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            table.addCell(createRowSpanCell("7", 5));
            table.addCell(createColSpanCell(getString("fa_title_7", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_7a", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_7a", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            table.addCell(createNormalCell(getString("fa_title_6_1", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_kompetitor", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            //table.addCell(createColSpanCell(getString("fa_title_7_1", lang), nProduct + 1));
            table.addCell(createNormalCell(getString("fa_7b", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_7b", columns[i]));
                table.addCell(createNormalCell(entity.getTextAnswer()));
            }

            table.addCell(createNormalCell(getString("fa_7c", lang)));
            for (int i = 0; i < nProduct; i++) {
                CoachingQuestionAnswerEntity entity =
                        qaMap.get(new Pair<>("fa_7c", columns[i]));
                table.addCell(createNormalCell(entity.getTickStringRep()));
            }

            chapter.add(table);
            chapter.add(Chunk.NEWLINE);

            /*chapter.add(new Paragraph("FA/SA/MD yang mendapat Coaching \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
            cell.addElement(new Paragraph(getString("summary_1", lang), normalFont));
            CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>("fa_summary_1", ""));
            String value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell.addElement(new Paragraph(value, normalFont));
            cell.addElement(new Paragraph(getString("summary_2", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("fa_summary_2", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(new Paragraph("NEXT ACTION PLAN", heading3Font));
            cell2.addElement(new Paragraph(getString("summary_3", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("fa_summary_3", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell2.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell2);

            chapter.add(table1);*/


            doc.add(chapter);

            doc.add(new Paragraph("\n COACHING SUMMARY \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            //cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
            cell.addElement(new Paragraph(getString("summary_1", lang), heading3Font));
            CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>("fa_summary_1", ""));
            String value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell);

            PdfPCell cell3 = new PdfPCell();
            cell3.addElement(new Paragraph(getString("summary_2", lang), heading3Font));
            answerEntity = qaMap.get(new Pair<>("fa_summary_2", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell3.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell3);

            PdfPCell cell2 = new PdfPCell();
            //cell2.addElement(new Paragraph("Next Action Plan", heading3Font));
            cell2.addElement(new Paragraph(getString("summary_3", lang), heading3Font));
            answerEntity = qaMap.get(new Pair<>("fa_summary_3", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell2.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell2);

            doc.add(table1);

            doc.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listener.onPDFGenerated(true);
    }

    public static void createDSRPDF(CoachingSessionEntity coachingSession,
                                    Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap,
                                    GeneratePDFListener listener) {

        Document doc = new Document();
        /*String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getId()
                + ".pdf";*/
        String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getPdfFileName();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            CoachingQuestionAnswerEntity ent = (CoachingQuestionAnswerEntity) qaMap.values()
                    .toArray()[0];

            int lang = ent.getQuestionID().contains("bahasa") ? BAHASA : ENGLISH;

            Chunk chunk = new Chunk("DSR Assessment form\n\n", heading1Font);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

            chapter.add(createLeftRight("Coach : " + coachingSession.getCoachName(),
                    "Distributor : " + coachingSession.getDistributor()));
            chapter.add(createLeftRight("Coachee : " + coachingSession.getCoacheeName(),
                    "Area : " + coachingSession.getArea()));
            chapter.add(createLeftRight("Tanggal : " + coachingSession.getFormattedDate(), ""));

            chapter.add(new Paragraph("\n"));

            float[] columnSebelum = {4, 1, 3};
            PdfPTable tableSebelum = new PdfPTable(columnSebelum);
            tableSebelum.setWidthPercentage(100);

            tableSebelum.addCell(createTableHeader(getString("sebelum_kunjungan", "title", lang)));
            tableSebelum.addCell(createTableHeader("Tick if \n Done/Know"));
            tableSebelum.addCell(createTableHeader("Remarks"));


            String[] sebelumID = {"1", "2", "3", "4", "4a", "4b", "4c", "4d", "4e"};

            for (String id : sebelumID) {
                String temp = "dsr_sebelum_" + id;
                tableSebelum.addCell(createNormalCell(getString(temp, lang)));
                String questionID = temp;
                String columnID = "";

                if (id.equals("4")) {
                    tableSebelum.addCell("");
                    tableSebelum.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableSebelum.addCell(createNormalCell(value));
                    tableSebelum.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            chapter.add(tableSebelum);
            doc.add(chapter);
            doc.add(new Paragraph("\n"));
            // doc.newPage();

            float[] columnSaat = {8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            PdfPTable tableSaat = new PdfPTable(columnSaat);
            tableSaat.setWidthPercentage(100);

            tableSaat.addCell(createRowColSpanCell(getString("saat_kunjungan", "title", lang), 2, 1));
            tableSaat.addCell(createColSpanCell("Customer ke-", 10));

            for (int i = 1; i <= 10; i++) {
                tableSaat.addCell(createTableHeader(String.valueOf(i)));
            }

            String[] saatID = {"1", "2", "3", "3", "4", "5", "6", "7", "8", "9", "10"};
            String[] text_saatID = {"1", "2", "3", "3a", "3b", "3c", "3d", "3e", "4", "5", "6"};
            int in = 0;
            for (String id : saatID) {
                String temp = "dsr_saat_" + id;
                String temp_1 = "dsr_saat_" + text_saatID[in];
                tableSaat.addCell(createNormalCell(getString(temp_1, lang)));
                for (int i = 1; i <= 10; i++) {
                    if (text_saatID[in].equals("3")) {
                        tableSaat.addCell("");
                    } else {
                        String questionID = temp;
                        String columnID = "customer_" + i;
                        String value = qaMap.get(new Pair<>(questionID, columnID)).getTickStringRep();
                        tableSaat.addCell(createNormalCell(value));
                    }
                }
                in++;
            }

            doc.add(tableSaat);
            // doc.newPage();
            doc.add(new Paragraph("\n"));
            float[] columnSetelah = {4, 1, 3};
            PdfPTable tableSetelah = new PdfPTable(columnSetelah);
            tableSetelah.setWidthPercentage(100);

            tableSetelah.addCell(createTableHeader(getString("setelah_kunjungan", "title", lang)));
            tableSetelah.addCell(createTableHeader("Tick if \n Done/Know"));
            tableSetelah.addCell(createTableHeader("Remarks"));


            String[] setelahID = {"1", "2"};

            for (String id : setelahID) {
                String temp = "dsr_setelah_" + id;
                tableSetelah.addCell(createNormalCell(getString(temp, lang)));
                String questionID = temp;
                String columnID = "";
                CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                String value = answerEntity.getTickStringRep();
                tableSetelah.addCell(createNormalCell(value));
                tableSetelah.addCell(createNormalCell(answerEntity.getTextAnswer()));
            }

            doc.add(tableSetelah);
            //doc.newPage();
            //doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("\n COACHING SUMMARY \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            //cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
            cell.addElement(new Paragraph(getString("summary_1", lang), heading3Font));
            CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>("dsr_summary_1", ""));
            String value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell);

            PdfPCell cell3 = new PdfPCell();
            cell3.addElement(new Paragraph(getString("summary_2", lang), heading3Font));
            answerEntity = qaMap.get(new Pair<>("dsr_summary_2", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell3.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell3);

            PdfPCell cell2 = new PdfPCell();
            //cell2.addElement(new Paragraph("Next Action Plan", heading3Font));
            cell2.addElement(new Paragraph(getString("summary_3", lang), heading3Font));
            answerEntity = qaMap.get(new Pair<>("dsr_summary_3", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            value = "".equals(value) ? "\n\n" : value;
            cell2.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell2);

            doc.add(table1);
            doc.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listener.onPDFGenerated(true);
    }

    public static void createASMPUSHPDF(CoachingSessionEntity coachingSession,
                                        Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap,
                                        GeneratePDFListener listener) {

        Document doc = new Document();
        /*String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getId()
                + ".pdf";*/
        String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getPdfFileName();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            CoachingQuestionAnswerEntity ent = (CoachingQuestionAnswerEntity) qaMap.values()
                    .toArray()[0];

            //int lang = ent.getQuestionID().contains("bahasa") ? BAHASA : ENGLISH;
            int lang = BAHASA;

            Chunk chunk = new Chunk("ASM PUSH COACHING GUIDELINES\n\n", heading1Font);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

            chapter.add(createLeftRight("Coach : " + coachingSession.getCoachName(),
                    "Distributor : " + coachingSession.getDistributor()));
            chapter.add(createLeftRight("Coachee : " + coachingSession.getCoacheeName(),
                    "Area : " + coachingSession.getArea()));
            chapter.add(createLeftRight("Tanggal : " + coachingSession.getFormattedDate(), ""));

            chapter.add(new Paragraph("\n"));

            float[] columnSebelum = {4, 1, 3};
            PdfPTable tableSebelum = new PdfPTable(columnSebelum);
            tableSebelum.setWidthPercentage(100);

            Log.d(TAG, "MAP SIZE: " + qaMap.size());

            tableSebelum.addCell(createTableHeader(getString("asm_push_title_sebelum", lang)));
            tableSebelum.addCell(createTableHeader("Tick if \n Done/Know"));
            tableSebelum.addCell(createTableHeader("Remarks"));

            String[] asmSebelum = {"title_1", "1_1", "1_1_a", "1_1_b", "1_1_c", "1_1_d", "1_1_e"
                    ,"1_2", "1_2_a", "1_2_b", "1_2_c"
                    ,"1_3", "1_3_a", "1_3_b"
                    ,"1_4", "1_4_a", "1_4_b"
                    ,"1_5", "1_5_a", "1_5_b", "1_5_c", "1_5_d", "1_5_e"
                    ,"1_6", "1_6_a", "1_6_b"
                    ,"title_2","2_1"
                    ,"2_2", "2_2_a", "2_2_b", "2_2_c", "2_2_d", "2_2_e"};


            for (String id : asmSebelum) {
                String temp = "asm_push_sebelum_" + id;
                tableSebelum.addCell(createNormalCell(getString(temp, lang)));
                String questionID = temp;
                String columnID = "";

                if (id.equals("title_1") || id.equals("1_1") || id.equals("1_2")
                        || id.equals("1_3") || id.equals("1_4") || id.equals("1_5")
                        || id.equals("1_6") || id.equals("title_2") || id.equals("2_2")) {
                    tableSebelum.addCell("");
                    tableSebelum.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableSebelum.addCell(createNormalCell(value));
                    tableSebelum.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            chapter.add(tableSebelum);
            doc.add(chapter);
            doc.add(new Paragraph("\n"));

            float[] columnDistributor = {4, 1, 3};
            PdfPTable tableDistributor = new PdfPTable(columnDistributor);
            tableDistributor.setWidthPercentage(100);

            tableDistributor.addCell(createTableHeader(getString("asm_push_title_di_distributor_report", lang)));
            tableDistributor.addCell(createTableHeader("Tick if \n Done/Know"));
            tableDistributor.addCell(createTableHeader("Remarks"));

            String[] asmDistributor = {"1","1_a","1_b"
                    ,"2","2_a","2_b"
                    ,"3","3_a","3_b","3_c","3_d"};


            for (String id : asmDistributor) {
                String temp = "asm_push_distributor_report_" + id;
                tableDistributor.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "asm_push_report_" + id;
                String columnID = "";

                if (id.equals("1") || id.equals("2") || id.equals("3")) {
                    tableDistributor.addCell("");
                    tableDistributor.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableDistributor.addCell(createNormalCell(value));
                    tableDistributor.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            doc.add(tableDistributor);
            doc.add(new Paragraph("\n"));

            float[] columnInfra = {4, 1, 3};
            PdfPTable tableInfra = new PdfPTable(columnInfra);
            tableInfra.setWidthPercentage(100);

            tableInfra.addCell(createTableHeader(getString("asm_push_title_di_distributor_infra", lang)));
            tableInfra.addCell(createTableHeader("Tick if \n Done/Know"));
            tableInfra.addCell(createTableHeader("Remarks"));

            String[] asmInfra = {"title","1","1_a","1_b","1_c","1_d","1_e","1_f","1_g"
                    ,"2","2_a","2_b","2_c","2_d","2_e"
                    ,"3"};

            for (String id : asmInfra) {
                String temp = "asm_push_distributor_infra_" + id;
                tableInfra.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "asm_push_infra_" + id;
                String columnID = "";

                if (id.equals("title") || id.equals("1") || id.equals("2")) {
                    tableInfra.addCell("");
                    tableInfra.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableInfra.addCell(createNormalCell(value));
                    tableInfra.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            doc.add(tableInfra);
            doc.add(new Paragraph("\n"));

            float[] columnPasar = {4, 1, 3};
            PdfPTable tablePasar = new PdfPTable(columnPasar);
            tablePasar.setWidthPercentage(100);

            tablePasar.addCell(createTableHeader(getString("asm_push_title_di_pasar", lang)));
            tablePasar.addCell(createTableHeader("Tick if \n Done/Know"));
            tablePasar.addCell(createTableHeader("Remarks"));

            String[] asmPasar = {"1","2","3","4","5"};

            for (String id : asmPasar) {
                String temp = "asm_push_di_pasar_" + id;
                tablePasar.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "asm_push_market_" + id;
                String columnID = "";

                CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                String value = answerEntity.getTickStringRep();
                tablePasar.addCell(createNormalCell(value));
                tablePasar.addCell(createNormalCell(answerEntity.getTextAnswer()));
            }

            doc.add(tablePasar);

            doc.add(new Paragraph("\n COACHING SUMMARY \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            for(int i = 1; i <= 3; i++){
                PdfPCell cell = new PdfPCell();
                //cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
                cell.addElement(new Paragraph(getString("summary_" + i, lang), heading3Font));
                CoachingQuestionAnswerEntity answerEntity = qaMap.get(
                        new Pair<>("asm_push_summary_" + i, ""));
                String value = String.valueOf(answerEntity.getTextAnswer());
                value = "".equals(value) ? "\n\n" : value;
                cell.addElement(new Paragraph(value, normalFont));
                table1.addCell(cell);

            }

            doc.add(table1);
            doc.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listener.onPDFGenerated(true);

    }

    public static void createDTSPULLPDF(CoachingSessionEntity coachingSession,
                                        Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap,
                                        GeneratePDFListener listener) {

        Document doc = new Document();
        /*String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getId()
                + ".pdf";*/
        String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getPdfFileName();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            CoachingQuestionAnswerEntity ent = (CoachingQuestionAnswerEntity) qaMap.values()
                    .toArray()[0];

            //int lang = ent.getQuestionID().contains("bahasa") ? BAHASA : ENGLISH;
            int lang = BAHASA;

            Chunk chunk = new Chunk("DTS COACHING GUIDELINES\n\n", heading1Font);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

            chapter.add(createLeftRight("Coach : " + coachingSession.getCoachName(),
                    "Distributor : " + coachingSession.getDistributor()));
            chapter.add(createLeftRight("Coachee : " + coachingSession.getCoacheeName(),
                    "Area : " + coachingSession.getArea()));
            chapter.add(createLeftRight("Tanggal : " + coachingSession.getFormattedDate(), ""));

            chapter.add(new Paragraph("\n"));

            float[] columnSebelum = {4, 1, 3};
            PdfPTable tableSebelum = new PdfPTable(columnSebelum);
            tableSebelum.setWidthPercentage(100);

            Log.d(TAG, "MAP SIZE: " + qaMap.size());

            tableSebelum.addCell(createTableHeader(getString("dts_title_sebelum", lang)));
            tableSebelum.addCell(createTableHeader("Tick if \n Done/Know"));
            tableSebelum.addCell(createTableHeader("Remarks"));

            String[] dtsSebelum = {"title_1","1_1","1_1_a", "1_1_b", "1_1_c", "1_1_d", "1_1_e"
                    ,"1_2","1_2_a", "1_2_b", "1_2_c"
                    ,"title_2","2_1"
                    ,"2_2","2_2_a", "2_2_b", "2_2_c", "2_2_d", "2_2_e"};


            for (String id : dtsSebelum) {
                String temp = "dts_sebelum_" + id;
                tableSebelum.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "dts_pull_sebelum_" + id;
                String columnID = "";

                if (id.equals("title_1") || id.equals("1_1") || id.equals("1_2") ||
                        id.equals("title_2") || id.equals("2_2")) {
                    tableSebelum.addCell("");
                    tableSebelum.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableSebelum.addCell(createNormalCell(value));
                    tableSebelum.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            chapter.add(tableSebelum);
            doc.add(chapter);
            doc.add(new Paragraph("\n"));

            float[] columnDistributor = {4, 1, 3};
            PdfPTable tableDistributor = new PdfPTable(columnDistributor);
            tableDistributor.setWidthPercentage(100);

            tableDistributor.addCell(createTableHeader(getString("dts_title_di_distributor_report", lang)));
            tableDistributor.addCell(createTableHeader("Tick if \n Done/Know"));
            tableDistributor.addCell(createTableHeader("Remarks"));

            String[] dtsDistributor = {"1","1_a","1_b"
                    ,"2","2_a","2_b"
                    ,"3","3_a","3_b","3_c"};


            for (String id : dtsDistributor) {
                String temp = "dts_distributor_report_" + id;
                tableDistributor.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "dts_pull_report_" + id;
                String columnID = "";

                if (id.equals("1") || id.equals("2") || id.equals("3")) {
                    tableDistributor.addCell("");
                    tableDistributor.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableDistributor.addCell(createNormalCell(value));
                    tableDistributor.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            doc.add(tableDistributor);
            doc.add(new Paragraph("\n"));

            float[] columnInfra = {4, 1, 3};
            PdfPTable tableInfra = new PdfPTable(columnInfra);
            tableInfra.setWidthPercentage(100);

            tableInfra.addCell(createTableHeader(getString("dts_title_di_distributor_infra", lang)));
            tableInfra.addCell(createTableHeader("Tick if \n Done/Know"));
            tableInfra.addCell(createTableHeader("Remarks"));

            String[] dtsInfra = {"title","1","1_a","1_b","1_c","1_d","1_e","1_f","1_g"
                    ,"2","2_a","2_b","2_c","2_d","2_e"
                    ,"3"};

            for (String id : dtsInfra) {
                String temp = "dts_distributor_infra_" + id;
                tableInfra.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "dts_pull_infra_" + id;
                String columnID = "";

                if (id.equals("title") || id.equals("1") || id.equals("2")) {
                    tableInfra.addCell("");
                    tableInfra.addCell("");
                } else {
                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableInfra.addCell(createNormalCell(value));
                    tableInfra.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            doc.add(tableInfra);
            doc.add(new Paragraph("\n"));

            float[] columnPasar = {4, 1, 3};
            PdfPTable tablePasar = new PdfPTable(columnPasar);
            tablePasar.setWidthPercentage(100);

            tablePasar.addCell(createTableHeader(getString("dts_title_di_pasar", lang)));
            tablePasar.addCell(createTableHeader("Tick if \n Done/Know"));
            tablePasar.addCell(createTableHeader("Remarks"));

            String[] dtsPasar = {"1","2","3"};

            for (String id : dtsPasar) {
                String temp = "dts_di_pasar_" + id;
                tablePasar.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "dts_pull_market_" + id;
                String columnID = "";

                CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                String value = answerEntity.getTickStringRep();
                tablePasar.addCell(createNormalCell(value));
                tablePasar.addCell(createNormalCell(answerEntity.getTextAnswer()));
            }

            doc.add(tablePasar);

            doc.add(new Paragraph("\n COACHING SUMMARY \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            for(int i = 1; i <= 3; i++){
                PdfPCell cell = new PdfPCell();
                //cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
                cell.addElement(new Paragraph(getString("summary_" + i, lang), heading3Font));
                CoachingQuestionAnswerEntity answerEntity = qaMap.get(
                        new Pair<>("dts_pull_summary_" + i, ""));
                String value = String.valueOf(answerEntity.getTextAnswer());
                value = "".equals(value) ? "\n\n" : value;
                cell.addElement(new Paragraph(value, normalFont));
                table1.addCell(cell);

            }

            doc.add(table1);
            doc.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listener.onPDFGenerated(true);

    }

    public static void createASMPULLPDF(CoachingSessionEntity coachingSession,
                                        Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap,
                                        GeneratePDFListener listener) {

        Document doc = new Document();
        /*String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getId()
                + ".pdf";*/
        String path = Environment.getExternalStorageDirectory() + "/" + coachingSession.getPdfFileName();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            CoachingQuestionAnswerEntity ent = (CoachingQuestionAnswerEntity) qaMap.values()
                    .toArray()[0];

            //int lang = ent.getQuestionID().contains("bahasa") ? BAHASA : ENGLISH;
            int lang = BAHASA;

            Chunk chunk = new Chunk("ASM PULL COACHING GUIDELINES\n\n", heading1Font);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

            chapter.add(createLeftRight("Coach : " + coachingSession.getCoachName(),
                    "Area : " + coachingSession.getArea()));
            chapter.add(createLeftRight("Coachee : " + coachingSession.getCoacheeName(),
                    "Tanggal : " + coachingSession.getFormattedDate()));

            chapter.add(new Paragraph("\n"));

            float[] columnReport = {4, 1, 3};
            PdfPTable tableReport = new PdfPTable(columnReport);
            tableReport.setWidthPercentage(100);

            String index[] = new String[]{"","a","b","c","d"};

            Log.d(TAG, "MAP SIZE: " + qaMap.size());

            tableReport.addCell(createTableHeader(getString("asm_pull_title_report", lang)));
            tableReport.addCell(createTableHeader("Tick if \n Done/Know"));
            tableReport.addCell(createTableHeader("Remarks"));


            tableReport.addCell(createNormalCell(getString("asm_pull_report_title_1", lang)));
            tableReport.addCell("");
            tableReport.addCell("");

            tableReport.addCell(createNormalCell(getString("asm_pull_report_how_1", lang)));
            tableReport.addCell("");
            tableReport.addCell("");

            for (int i = 1; i <= 8; i++) {
                String temp = "asm_pull_report_1_" + i;
                tableReport.addCell(createNormalCell(getString(temp, lang)));

                tableReport.addCell("");
                tableReport.addCell("");

                for (int j = 1; j <= 4; j++) {
                    String questionID = "asm_pull_report_1_" + i + "_" + index[j];
                    String columnID = "";

                    tableReport.addCell(createNormalCell(getString(questionID, lang)));

                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableReport.addCell(createNormalCell(value));
                    tableReport.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            tableReport.addCell(createNormalCell(getString("asm_pull_report_title_2", lang)));
            tableReport.addCell("");
            tableReport.addCell("");

            tableReport.addCell(createNormalCell(getString("asm_pull_report_how_2", lang)));
            tableReport.addCell("");
            tableReport.addCell("");

            for (int i = 1; i <= 19; i++) {
                String temp = "asm_pull_report_2_" + i;
                tableReport.addCell(createNormalCell(getString(temp, lang)));

                tableReport.addCell("");
                tableReport.addCell("");

                for (int j = 1; j <= 2; j++) {
                    String questionID = "asm_pull_report_2_" + i + "_" + index[j];
                    String columnID = "";

                    tableReport.addCell(createNormalCell(getString(questionID, lang)));

                    CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                    String value = answerEntity.getTickStringRep();
                    tableReport.addCell(createNormalCell(value));
                    tableReport.addCell(createNormalCell(answerEntity.getTextAnswer()));
                }
            }

            chapter.add(tableReport);
            doc.add(chapter);
            doc.add(new Paragraph("\n"));

            float[] columnHabit = {4, 1, 3};
            PdfPTable tableHabbit = new PdfPTable(columnHabit);
            tableHabbit.setWidthPercentage(100);

            tableHabbit.addCell(createTableHeader(getString("asm_pull_title_habit", lang)));
            tableHabbit.addCell(createTableHeader("Tick if \n Done/Know"));
            tableHabbit.addCell(createTableHeader("Remarks"));

            String[] dtsDistributor = {"1", "2"};


            for (String id : dtsDistributor) {
                String temp = "asm_pull_habit_" + id;
                tableHabbit.addCell(createNormalCell(getString(temp, lang)));
                String questionID = "asm_pull_habbit_" + id;
                String columnID = "";

                CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>(questionID, columnID));
                String value = answerEntity.getTickStringRep();
                tableHabbit.addCell(createNormalCell(value));
                tableHabbit.addCell(createNormalCell(answerEntity.getTextAnswer()));
            }

            doc.add(tableHabbit);

            doc.add(new Paragraph("\n COACHING SUMMARY \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            for(int i = 1; i <= 3; i++){
                PdfPCell cell = new PdfPCell();
                //cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
                cell.addElement(new Paragraph(getString("summary_" + i, lang), heading3Font));
                CoachingQuestionAnswerEntity answerEntity = qaMap.get(
                        new Pair<>("asm_pull_summary_" + i, ""));
                String value = String.valueOf(answerEntity.getTextAnswer());
                value = "".equals(value) ? "\n\n" : value;
                cell.addElement(new Paragraph(value, normalFont));
                table1.addCell(cell);

            }

            doc.add(table1);
            doc.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listener.onPDFGenerated(true);

    }

    private static Paragraph createLeftRight(String left, String right) {
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p = new Paragraph(left, heading2Font);
        p.add(new Chunk(glue));
        p.add(right);
        return p;
    }

    private static PdfPCell createTableHeader(String header) {
        PdfPCell cell = new PdfPCell(new Phrase(header, heading3Font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        return cell;
    }

    private static PdfPCell createColSpanCell(String text, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, heading3Font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        cell.setColspan(colspan);

        return cell;
    }

    private static PdfPCell createRowSpanCell(String text, int rowspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, heading3Font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(rowspan);
        return cell;
    }

    private static PdfPCell createRowColSpanCell(String text, int rowspan, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, heading3Font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(rowspan);
        cell.setColspan(colspan);
        return cell;
    }

    private static PdfPCell createNormalCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, normalFont));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        cell.setPaddingLeft(5);

        return cell;
    }

    private static String getString(String id, int lang) {
        String name;
        if (lang == BAHASA) {
            name = "bahasa_" + id;
        } else {
            name = "english_" + id;
        }
        Resources res = MainApp.getContext().getResources();
        return res.getString(res.getIdentifier(name, "string", MainApp.getContext().getPackageName()));
    }

    private static String getString(String id, String prefix, int lang) {
        String name;
        if (lang == BAHASA) {
            name = prefix + "_bahasa_" + id;
        } else {
            name = prefix + "_english_" + id;
        }
        Resources res = MainApp.getContext().getResources();
        return res.getString(res.getIdentifier(name, "string", MainApp.getContext().getPackageName()));
    }

    public interface GeneratePDFListener {
        void onPDFGenerated(boolean isSuccess);
    }
}
