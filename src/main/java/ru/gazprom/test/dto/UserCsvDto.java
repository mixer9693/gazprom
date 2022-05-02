package ru.gazprom.test.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserCsvDto {
    @CsvBindByName
    private String gender;
    @CsvBindByName(column = "NAME TITLE")
    private String nameTitle;
    @CsvBindByName(column = "NAME FIRST")
    private String nameFirst;
    @CsvBindByName(column = "NAME LAST")
    private String nameLast;

    @CsvBindByName(column = "LOCATION STREET NUMBER")
    private String locationStreetNumber;
    @CsvBindByName(column = "LOCATION STREET NAME")
    private String locationStreetName;

    @CsvBindByName(column = "LOCATION CITY")
    private String locationCity;
    @CsvBindByName(column = "LOCATION STATE")
    private String locationState;
    @CsvBindByName(column = "LOCATION COUNTRY")
    private String locationCountry;
    @CsvBindByName(column = "LOCATION POSTCODE")
    private String locationPostcode;

    @CsvBindByName(column = "LOCATION COORDINATES LATITUDE")
    private Float locationCoordinatesLatitude;
    @CsvBindByName(column = "LOCATION COORDINATES LONGITUDE")
    private Float locationCoordinatesLongitude;

    @CsvBindByName(column = "LOCATION TIMEZONE OFFSET")
    private String locationTimezoneOffset;
    @CsvBindByName(column = "LOCATION TIMEZONE DESCRIPTION")
    private String locationTimezoneDescription;

    @CsvBindByName
    private String email;

    @CsvBindByName(column = "LOGIN UUID")
    private String loginUuid;
    @CsvBindByName(column = "LOGIN USERNAME")
    private String loginUsername;
    @CsvBindByName(column = "LOGIN PASSWORD")
    private String loginPassword;
    @CsvBindByName(column = "LOGIN SALT")
    private String loginSalt;
    @CsvBindByName(column = "LOGIN MD5")
    private String loginMd5;
    @CsvBindByName(column = "LOGIN SHA 256")
    private String loginSha256;

    @CsvBindByName(column = "DOB DATE")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime dobDate;
    @CsvBindByName(column = "DOB AGE")
    private Integer dobAge;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "REGISTRATION DATE")
    private ZonedDateTime registeredDate;
    @CsvBindByName(column = "REGISTRATION AGE")
    private Integer registeredAge;

    @CsvBindByName
    private String phone;
    @CsvBindByName
    private String cell;

    @CsvBindByName(column = "ID NAME")
    private String idName;
    @CsvBindByName(column = "ID VALUE")
    private String idValue;

    @CsvBindByName(column = "PICTURE LARGE")
    private String pictureLarge;
    @CsvBindByName(column = "PICTURE MEDIUM")
    private String pictureMedium;
    @CsvBindByName(column = "PICTURE THUMBNAIL")
    private String pictureThumbnail;

    @CsvBindByName
    private String nat;
}
