package com.techbank.account.base.command;

import com.techbank.account.base.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BaseCommand extends Message {
    public BaseCommand(String id) {
        super(id);
    }
}
