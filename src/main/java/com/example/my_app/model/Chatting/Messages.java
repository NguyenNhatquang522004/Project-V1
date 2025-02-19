package com.example.my_app.model.Chatting;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Document(collection = "chat")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Messages extends TimeBase {
    @Id
    String id;
    String messages;
    String firstName;
    String lastName;

    public Messages(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
