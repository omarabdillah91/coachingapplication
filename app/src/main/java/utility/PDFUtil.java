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
                                        } else {
                                            createDSRPDF(coachingSessionEntity, qaMap, listener);
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

            chapter.add(new Paragraph("FA/SA/MD yang mendapat Coaching \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
            cell.addElement(new Paragraph(getString("summary_1", lang), normalFont));
            CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>("fa_summary_1", ""));
            String value = String.valueOf(answerEntity.getTextAnswer());
            cell.addElement(new Paragraph(value, normalFont));
            cell.addElement(new Paragraph(getString("summary_2", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("fa_summary_2", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            cell.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(new Paragraph("NEXT ACTION PLAN", heading3Font));
            cell2.addElement(new Paragraph(getString("summary_3", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("fa_summary_3", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            cell2.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell2);

            chapter.add(table1);

            doc.add(chapter);
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

            String[] saatID = {"1", "2","3", "3", "4", "5", "6", "7", "8", "9", "10"};
            String[] text_saatID = {"1", "2","3", "3a", "3b", "3c", "3d", "3e", "4", "5", "6"};
            int in = 0;
            for (String id : saatID) {
                String temp = "dsr_saat_" + id;
                String temp_1 = "dsr_saat_" + text_saatID[in];
                tableSaat.addCell(createNormalCell(getString(temp_1, lang)));
                for (int i = 1; i <= 10; i++) {
                    if(text_saatID[in].equals("3")){
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
            doc.newPage();
            //doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("DSR yang mendapat Coaching \n\n", heading3Font));

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph("COACHING SUMMARY", heading3Font));
            cell.addElement(new Paragraph(getString("summary_1", lang), normalFont));
            CoachingQuestionAnswerEntity answerEntity = qaMap.get(new Pair<>("dsr_summary_1", ""));
            String value = String.valueOf(answerEntity.getTextAnswer());
            cell.addElement(new Paragraph(value, normalFont));
            cell.addElement(new Paragraph(getString("summary_2", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("dsr_summary_2", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
            cell.addElement(new Paragraph(value, normalFont));
            table1.addCell(cell);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(new Paragraph("NEXT ACTION PLAN", heading3Font));
            cell2.addElement(new Paragraph(getString("summary_3", lang), normalFont));
            answerEntity = qaMap.get(new Pair<>("dsr_summary_3", ""));
            value = String.valueOf(answerEntity.getTextAnswer());
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
