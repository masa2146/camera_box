package io.hubbox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author fatih
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WhiteListData {
    private String plateNumber;
    private String masterName;
    private Date startTime;
    private Date endTime;
}
