package org.accelerate.tdm.smartdata.plugin.sql.exporter;

import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.core.exporter.IExporterDataWrapper;
import org.dbunit.dataset.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CVSDataMap implements IExporterDataWrapper{
    List<Map<Column,String>> rows;
    
}
