package com.example.nhom3.project.modules.identity.dto.event;

import com.example.nhom3.project.modules.identity.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserActivatedEvent extends ApplicationEvent {
    User user;

    public UserActivatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
