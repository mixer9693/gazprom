package ru.gazprom.test.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserCsvDto {
    private String gender;
    private String nameTitle;
    private String nameFirst;
    private String nameLast;

    private String locationStreetNumber;
    private String locationStreetName;

    private String locationCity;
    private String locationState;
    private String locationCountry;
    private String locationPostcode;

    private Float locationCoordinatesLatitude;
    private Float locationCoordinatesLongitude;

    private String locationTimezoneOffset;
    private String locationTimezoneDescription;

    private String email;

    private String loginUuid;
    private String loginUsername;
    private String loginPassword;
    private String loginSalt;
    private String loginMd5;
    private String loginSha256;

    private ZonedDateTime dobDate;
    private Integer dobAge;

    private ZonedDateTime registeredDate;
    private Integer registeredAge;

    private String phone;
    private String cell;

    private String idName;
    private String idValue;

    private String pictureLarge;
    private String pictureMedium;
    private String pictureThumbnail;

    private String nat;
}
