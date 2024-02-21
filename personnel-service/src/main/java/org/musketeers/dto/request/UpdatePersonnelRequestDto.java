package org.musketeers.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdatePersonnelRequestDto {

    private String token;

    private String name;

    private String lastName;

    private String email;

    private List<String> phones;         // get(0) personal, get(1) work phone...

}