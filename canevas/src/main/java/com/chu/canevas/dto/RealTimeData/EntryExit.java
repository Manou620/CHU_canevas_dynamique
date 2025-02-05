package com.chu.canevas.dto.RealTimeData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryExit {
    private long entriesOfToday;
    private long exitsOfToday;
    private long lateEntriesOfToday;
    private long earlyExitsOfToday;
}
