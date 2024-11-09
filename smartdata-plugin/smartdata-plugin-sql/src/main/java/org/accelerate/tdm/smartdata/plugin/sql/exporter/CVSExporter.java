package org.accelerate.tdm.smartdata.plugin.sql.exporter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.core.exporter.IExporter;
import org.accelerate.tdm.smartdata.core.exporter.IExporterDataWrapper;
import org.dbunit.dataset.Column;

import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component("csv")
public class CVSExporter implements IExporter{
    
    protected static final String SEPARATOR = ";";

    @Override
    public void export(IExporterDataWrapper wrapper) {

       
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        System.out.println("dddddddddddddddddddddddddddd");
        List<Map<Column,String>> rows = ((CVSDataMap) wrapper).getRows();
        try (PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8")) {
            rows.forEach(aRow ->{
                StringBuilder lineToWrite = new StringBuilder();
                aRow.forEach((columnKey, columnValue) -> {
                    lineToWrite.append(columnValue);
                    lineToWrite.append(SEPARATOR);
                    writer.println(lineToWrite);
                });
            });
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
