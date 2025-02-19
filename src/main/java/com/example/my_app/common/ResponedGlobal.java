package com.example.my_app.common;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponedGlobal {

    @JsonProperty("code")
    String code;
    @JsonProperty("messages")
    String messages;
    @JsonProperty("data")
    Object data;

}
