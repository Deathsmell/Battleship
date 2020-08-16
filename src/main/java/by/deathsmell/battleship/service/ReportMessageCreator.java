package by.deathsmell.battleship.service;

import by.deathsmell.battleship.dto.ReportType;

public interface ReportMessageCreator<T> {
    void sendTo(String sendTo, T t);

    T create(String text, String sender, ReportType type );

    void createAndSendTo(String sendTo,String text, String sender, ReportType type);

    T empty();

    // TODO: sendToUSer, createAndSendToUser, publicReport and etc.
}
