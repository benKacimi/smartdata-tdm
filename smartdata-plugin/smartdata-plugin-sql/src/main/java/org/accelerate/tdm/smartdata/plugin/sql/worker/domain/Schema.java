package org.accelerate.tdm.smartdata.plugin.sql.worker.domain;


import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class Schema {
    private List<String> tableList;
}
