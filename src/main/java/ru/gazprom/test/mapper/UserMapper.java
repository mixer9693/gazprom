package ru.gazprom.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gazprom.test.dto.UserCsvDto;
import ru.gazprom.test.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "name.title", target = "nameTitle")
    @Mapping(source = "name.first", target = "nameFirst")
    @Mapping(source = "name.last", target = "nameLast")

    @Mapping(source = "location.street.number", target = "locationStreetNumber")
    @Mapping(source = "location.street.name", target = "locationStreetName")

    @Mapping(source = "location.city", target = "locationCity")
    @Mapping(source = "location.state", target = "locationState")
    @Mapping(source = "location.country", target = "locationCountry")
    @Mapping(source = "location.postcode", target = "locationPostcode")

    @Mapping(source = "location.coordinates.latitude", target = "locationCoordinatesLatitude")
    @Mapping(source = "location.coordinates.longitude", target = "locationCoordinatesLongitude")

    @Mapping(source = "location.timezone.offset", target = "locationTimezoneOffset")
    @Mapping(source = "location.timezone.description", target = "locationTimezoneDescription")

    @Mapping(source = "login.uuid", target = "loginUuid")
    @Mapping(source = "login.username", target = "loginUsername")
    @Mapping(source = "login.password", target = "loginPassword")
    @Mapping(source = "login.salt", target = "loginSalt")
    @Mapping(source = "login.md5", target = "loginMd5")
    @Mapping(source = "login.sha256", target = "loginSha256")

    @Mapping(source = "dob.date", target = "dobDate")
    @Mapping(source = "dob.age", target = "dobAge")

    @Mapping(source = "registered.date", target = "registeredDate")
    @Mapping(source = "registered.age", target = "registeredAge")

    @Mapping(source = "id.name", target = "idName")
    @Mapping(source = "id.value", target = "idValue")

    @Mapping(source = "picture.large", target = "pictureLarge")
    @Mapping(source = "picture.medium", target = "pictureMedium")
    @Mapping(source = "picture.thumbnail", target = "pictureThumbnail")
    UserCsvDto userToCsvDto(User user);
}
