package com.codesetters.ebook.service.mapper;

import com.codesetters.ebook.domain.*;
import com.codesetters.ebook.service.dto.UserCertificatesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserCertificates and its DTO UserCertificatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserCertificatesMapper {

    UserCertificatesDTO userCertificatesToUserCertificatesDTO(UserCertificates userCertificates);

    List<UserCertificatesDTO> userCertificatesToUserCertificatesDTOs(List<UserCertificates> userCertificates);

    UserCertificates userCertificatesDTOToUserCertificates(UserCertificatesDTO userCertificatesDTO);

    List<UserCertificates> userCertificatesDTOsToUserCertificates(List<UserCertificatesDTO> userCertificatesDTOs);
}
