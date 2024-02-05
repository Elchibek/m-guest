package com.guest.app.service;

import com.guest.app.domain.Guest;
import com.guest.app.service.dto.docs.Doc;
import com.guest.app.service.dto.docs.TabKey;
import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;
import jakarta.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WordTableService {

    private final HttpServletRequest request;

    public WordTableService(HttpServletRequest request) {
        this.request = request;
    }

    public void saveToWord(Doc doc) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        List<TabKey> headers = doc.getTabKeys().stream().filter(h -> h.getSelected()).toList();
        List<Guest> guests = null;

        String realPathtoUploads = request.getServletContext().getRealPath("/content");

        // Create a Document object
        Document document = new Document();
        // Add a section
        Section section = document.addSection();

        // Add a table
        Table table = section.addTable(true);
        table.resetCells(guests.size() + 1, headers.size());

        // Установите первую строку в качестве заголовка таблицы
        TableRow row = table.getRows().get(0);
        row.isHeader(true);
        row.setHeight(25);
        row.setHeightType(TableRowHeightType.Exactly);
        row.getRowFormat().setBackColor(Color.DARK_GRAY);

        for (int i = 0; i < headers.size(); i++) {
            row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
            Paragraph p = row.getCells().get(i).addParagraph();
            p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
            TextRange txtRange = p.appendText(headers.get(i).getName());
            txtRange.getCharacterFormat().setBold(true);
            txtRange.getCharacterFormat().setTextColor(Color.WHITE);
        }

        // Добавьте данные в остальные строки
        for (int r = 0; r < guests.size(); r++) {
            TableRow dataRow = table.getRows().get(r + 1);
            dataRow.setHeight(22);
            dataRow.setHeightType(TableRowHeightType.Exactly);
            dataRow.getRowFormat().setBackColor(Color.white);
            for (int c = 0; c < headers.size(); c++) {
                Paragraph p = dataRow.getCells().get(c).addParagraph();
                dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                switch (headers.get(c).getFieldName()) {
                    case "guestFrom":
                        p.appendText(guests.get(r).getGuestFrom().getName());
                        break;
                    case "guestBlock":
                        p.appendText(guests.get(r).getGuestBlock().getName());
                        break;
                    case "entrance":
                        p.appendText(guests.get(r).getEntrance().getName() + guests.get(r).getEntrance().getNumEntrance());
                        break;
                    case "floor":
                        p.appendText(guests.get(r).getEntrance().getNumEntrance() + "");
                        break;
                    case "guestHouse":
                        p.appendText(guests.get(r).getGuestHouse().getName() + guests.get(r).getGuestHouse().getNumHouse());
                        break;
                    case "name":
                        p.appendText(guests.get(r).getName());
                        break;
                    case "guestInstitution":
                        p.appendText(guests.get(r).getGuestInstitution());
                        break;
                    case "responsible":
                        p.appendText(guests.get(r).getResponsible());
                        break;
                    case "explanation":
                        p.appendText(guests.get(r).getExplanation());
                        break;
                    case "startDate":
                        p.appendText(guests.get(r).getStartDate().toString());
                        break;
                    case "endDate":
                        p.appendText(guests.get(r).getEndDate().toString());
                        break;
                    case "restOfTheDay":
                        break;
                    case "countPerson":
                        p.appendText(String.valueOf(guests.get(r).getCountPerson()));
                        break;
                    case "price":
                        p.appendText(String.valueOf(guests.get(r).getPrice()));
                        break;
                    case "totalPrice":
                        p.appendText(String.valueOf(guests.get(r).getTotalPrice()));
                        break;
                }
            }
        }

        // Set background color for cells
        for (int j = 1; j < table.getRows().getCount(); j++) {
            if (j % 2 == 0) {
                TableRow row2 = table.getRows().get(j);
                for (int f = 0; f < row2.getCells().getCount(); f++) {
                    row2.getCells().get(f).getCellFormat().setBackColor(new Color(191, 191, 191));
                }
            }
        }
        // Save to file
        document.saveToFile(realPathtoUploads + "/FillTableWithData.docx", FileFormat.Docx_2013);
    }
}
