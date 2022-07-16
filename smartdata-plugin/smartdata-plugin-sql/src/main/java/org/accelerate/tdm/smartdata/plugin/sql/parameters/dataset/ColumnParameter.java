package org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnParameter {
    String name;
    String value;
}
