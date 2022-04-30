package ru.gazprom.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Dob {
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime date;
    private Integer age;
}
