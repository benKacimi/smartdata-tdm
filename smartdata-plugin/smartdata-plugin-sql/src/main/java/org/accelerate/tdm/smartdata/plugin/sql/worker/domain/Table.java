package org.accelerate.tdm.smartdata.plugin.sql.worker.domain;

import java.util.List;
import java.util.Map;

import org.dbunit.dataset.Column;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class Table {
    private List<Map<Column,String>> rows;
}
